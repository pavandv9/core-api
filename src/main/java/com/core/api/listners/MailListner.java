/**
 * 
 */
package com.core.api.listners;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.core.api.utils.SpringMailUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MailListner.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class MailListner implements ITestListener {

	/** The new line. */
	private static String NEW_LINE = System.lineSeparator();
	
	/** The format. */
	private static String FORMAT = "%1$-25s%2$-10s%3$-10s";
	
	/** The format text. */
	private static String FORMAT_TEXT = FORMAT + NEW_LINE;

	/** The passedtests. */
	List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	
	/** The failedtests. */
	List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	
	/** The skippedtests. */
	List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish(ITestContext context) {
		SpringMailUtil.sendMail(getReport());
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		passedtests.add(result.getMethod());
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		failedtests.add(result.getMethod());
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		skippedtests.add(result.getMethod());
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult result) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedWithTimeout(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	/**
	 * Gets the total test case.
	 *
	 * @return the total test case
	 */
	private int getTotalTestCase() {
		return passedtests.size() + failedtests.size() + skippedtests.size();
	}

	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	private StringBuilder getReport() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Total Test Case", ":", getTotalTestCase()));
		builder.append(String.format(FORMAT_TEXT, "Passed Test Case", ":", passedtests.size()));
		builder.append(String.format(FORMAT_TEXT, "Failed Test Case", ":", failedtests.size()));
		builder.append(String.format(FORMAT_TEXT, "Skipped Test Case", ":", skippedtests.size()));
		builder.append(NEW_LINE);
		if (!failedtests.isEmpty())
			builder.append(String.format(FORMAT_TEXT, "Failed Test Methods", ":", failedtests.get(0).getMethodName()));
		if (!skippedtests.isEmpty())
			builder.append(
					String.format(FORMAT_TEXT, "Skipped Test Methods", ":", skippedtests.get(0).getMethodName()));
		builder.append(NEW_LINE);
		return builder;
	}

}
