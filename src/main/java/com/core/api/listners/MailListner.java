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

/**
 * @author Pavan.DV
 *
 */
public class MailListner implements ITestListener {

	private static String NEW_LINE = System.lineSeparator();
	private static String FORMAT = "%1$-25s%2$-10s%3$-10s";
	private static String FORMAT_TEXT = FORMAT + NEW_LINE;

	List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();

	@Override
	public void onFinish(ITestContext context) {
		SpringMailUtil.sendMail(getReport());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		passedtests.add(result.getMethod());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		failedtests.add(result.getMethod());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		skippedtests.add(result.getMethod());
	}

	@Override
	public void onStart(ITestContext context) {
	}

	private int getTotalTestCase() {
		return passedtests.size() + failedtests.size() + skippedtests.size();
	}

	private StringBuilder getReport() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Total test case", ":", getTotalTestCase()));
		builder.append(String.format(FORMAT_TEXT, "Passed test case", ":", passedtests.size()));
		builder.append(String.format(FORMAT_TEXT, "Failed test case", ":", failedtests.size()));
		builder.append(String.format(FORMAT_TEXT, "Skipped test case", ":", skippedtests.size()));
		builder.append(NEW_LINE);
		if (!failedtests.isEmpty())
			builder.append(String.format(FORMAT_TEXT, "Failed test methods", ":", failedtests.get(0).getMethodName()));
		if (!skippedtests.isEmpty())
			builder.append(
					String.format(FORMAT_TEXT, "Skipped test methods", ":", skippedtests.get(0).getMethodName()));
		return builder;
	}

}
