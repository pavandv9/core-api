package com.core.api.config;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.core.api.config.ExtentReportManager.getCurrentTest;

// TODO: Auto-generated Javadoc
/**
 * The Class is responsible for Reporting.
 *
 * @author Pavan.DV
 * @since 1.3
 */
public class ReportUtil {

	/**
	 * Log info.
	 *
	 * @param details the details
	 */
	public static void logInfo(String details) {
		if(null!=getCurrentTest())
			getCurrentTest().log(Status.INFO, details);
	}

	/**
	 * Log message.
	 *
	 * @param status the status
	 * @param details the details
	 */
	public static void logMessage(Status status, String details) {
		if(null!=getCurrentTest())
			getCurrentTest().log(status, details);
	}

	/**
	 * Log exception.
	 *
	 * @param t the t
	 */
	public static void logException(Throwable t){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		if(null!=getCurrentTest())
			getCurrentTest().fail(MarkupHelper.createCodeBlock(stringWriter.toString()));
	}
	
	/**
	 * Log req res.
	 *
	 * @param status the status
	 * @param json the json
	 */
	public static void logReqRes(Status status, String json) {
		if(null!=getCurrentTest())
			getCurrentTest().info(MarkupHelper.createCodeBlock(json));
	}
}
