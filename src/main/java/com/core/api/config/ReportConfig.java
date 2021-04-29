package com.core.api.config;

import java.io.File;

import com.core.api.constants.AWSConstants;
import com.core.api.utils.ILogger;
import com.core.api.utils.JavaUtil;

/**
 * 
 * @author Pavan.DV
 * @since 1.1.0
 */
public class ReportConfig implements ILogger, AWSConstants {

	/**
	 * Allure Report path.
	 */
	static String HTML_REPORT_PATH = "https://core-api-automation.s3.ap-south-1.amazonaws.com/allure-report/index.html";

	/**
	 * Get sharable allure report link.
	 * 
	 * @return String report link
	 */
	public static String getAllureReportLink() {
		return HTML_REPORT_PATH;
	}

	/**
	 * Upload allure report to given s3 bucket and get sharable allure report link.
	 * 
	 * @return String report link
	 */
	public static String uploadAndGetReportLink() {
		uploadAllureReportToS3Bucket();
		return HTML_REPORT_PATH;
	}

	/**
	 * Upload allure reports to given s3 bucket.
	 */
	private static void uploadAllureReportToS3Bucket() {
		generateAllureReport();
		AwsConfig.getInstance().connectToS3().uploadToS3Bucket(BUCKET_NAME, "allure-report",
				new File(System.getProperty("user.dir") + "/allure-report"), true);
	}

	/**
	 * Generate allure report directory from allure results.
	 */
	private static void generateAllureReport() {
		if (System.getProperty("os.name") != null && System.getProperty("os.name").startsWith("Mac")) {
			JavaUtil.executeShellCommand("sh", "-c",
					"/usr/local/Cellar/allure/2.13.9/bin/allure generate allure-results --clean -o allure-report");
		} else {
			LOG.error("Contact developer for adding win/linux execute command for allure report");
		}
	}
}
