package com.core.api.config;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.core.api.constants.AWSConstants;
import com.core.api.exception.AwsException;
import com.core.api.exception.HttpException;
import com.core.api.utils.ILogger;

/**
 * 
 * @author Pavan.DV
 * @since 1.3
 */
public class AwsConfig implements AWSConstants, ILogger {

	private TransferManager transferManager;
	private AmazonS3 s3Client;

	/**
	 * Get AwsConfig instance.
	 * 
	 * @return AwsConfig instance.
	 */
	public static AwsConfig getInstance() {
		return new AwsConfig();
	}

	/**
	 * Connect to aws s3.
	 * 
	 * @return TransferManager object
	 */
	public AwsConfig connectToS3() {
		BasicAWSCredentials awsCreds = null;
		try {
			awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRETE_KEY);
		} catch (IllegalArgumentException e) {
			throw new HttpException("Access key/Secrete key can not be null");
		}
		s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
		return this;
	}

	/**
	 * Upload files/directory to the given bucket.
	 * 
	 * @param bucketName
	 * @param virtualDirectoryKeyPrefix
	 * @param directory
	 * @param includeSubdirectories
	 */
	public void uploadToS3Bucket(String bucketName, String virtualDirectoryKeyPrefix, File directory,
			boolean includeSubdirectories) {
		if (null != transferManager) {
			MultipleFileUpload upload = transferManager.uploadDirectory(bucketName, virtualDirectoryKeyPrefix,
					directory, includeSubdirectories);
			try {
				upload.waitForCompletion();
			} catch (AmazonClientException | InterruptedException e) {
				LOG.error("Interupted while uploading directory");
			}
			transferManager.shutdownNow();
		} else
			throw new AwsException("Connect to S3 bucket before uploading.");
	}

	/**
	 * Clear uploads that were initiated before the specified date.
	 * 
	 * @param bucketName The name of the bucket containing the uploads to clear.
	 * @param object
	 */
	public void clearFilesFromS3BucketSince(String bucketName, String object) {
		if (null != s3Client) {
			try {
				for (S3ObjectSummary file : s3Client.listObjects(bucketName, "allure-report").getObjectSummaries()) {
					s3Client.deleteObject(bucketName, file.getKey());
				}
				LOG.info("Allure Report folder cleared from s3 bucket.");
			} catch (Exception e) {
				throw new AwsException("Failed to clear files.");
			}

		} else
			throw new AwsException("Connect to S3 bucket before clearing files.");
	}
}
