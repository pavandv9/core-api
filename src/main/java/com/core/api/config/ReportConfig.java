package com.core.api.config;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.core.api.constants.AWSConstants;
import com.core.api.exception.HttpException;
import com.core.api.utils.ILogger;
import com.core.api.utils.JavaUtil;

public class ReportConfig implements ILogger {

	static String htmlReport = "https://core-api-automation.s3.ap-south-1.amazonaws.com/allure-report/index.html";

	public static String getAllureReportLink() {
		return htmlReport;
	}

	public static String uploadAndGetReportLink() {
		uploadAllureReportToS3Bucket();
		return htmlReport;
	}

	private static void uploadAllureReportToS3Bucket() {
		generateAllureReport();
		TransferManager transferManager = connectToS3();
		MultipleFileUpload upload = transferManager.uploadDirectory("core-api-automation", "allure-report",
				new File(System.getProperty("user.dir") + "/allure-report"), true);
		try {
			upload.waitForCompletion();
		} catch (AmazonClientException | InterruptedException e) {
			LOG.error("Interupted while uploading directory");
		}
		transferManager.shutdownNow();
	}

	private static TransferManager connectToS3() {
		BasicAWSCredentials awsCreds = null;
		try {
			awsCreds = new BasicAWSCredentials(AWSConstants.ACCESS_KEY, AWSConstants.SECRETE_KEY);
		} catch (IllegalArgumentException e) {
			throw new HttpException("Access key/Secrete key can not be null");
		}
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		return TransferManagerBuilder.standard().withS3Client(s3Client).build();
	}

	private static void generateAllureReport() {
		if (System.getProperty("os.name") != null && System.getProperty("os.name").startsWith("Mac")) {
			JavaUtil.executeShellCommand("sh", "-c", "/usr/local/Cellar/allure/2.13.9/bin/allure generate allure-results --clean -o allure-report");
		} else {
			LOG.error("Contact developer for adding win/linux execute command for allure report");
		}
	}
}
