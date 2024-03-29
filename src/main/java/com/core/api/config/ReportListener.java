package com.core.api.config;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving report events. The class that is
 * interested in processing a report event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's addReportListener method. When the report event
 * occurs, that object's appropriate method is invoked.
 * 
 * @author Pavan.DV
 * @since 1.3
 */
public class ReportListener implements ITestListener {

	/**
	 * Gets the test name.
	 *
	 * @param result the result
	 * @return the test name
	 */
	public String getTestName(ITestResult result) {
		Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
		if (testAnnotation != null && !"".equals(testAnnotation.testName().trim())) {
			return testAnnotation.testName().trim();
		}
		return result.getTestName() != null ? result.getTestName()
				: result.getMethod().getConstructorOrMethod().getName();
	}

	/**
	 * Gets the main test name.
	 *
	 * @param result the result ,if not available return the Test class name
	 * @return the main test name
	 */
	public String getMainTestName(ITestResult result) {
		result.getMethod().isDataDriven();
		Test test = result.getTestClass().getRealClass().getDeclaredAnnotation(Test.class);
		if (test != null && !"".equalsIgnoreCase(test.testName().trim())) {
			return test.testName().trim();
		}
		return result.getTestClass().getRealClass().getSimpleName();
	}

	/**
	 * Gets the main test name.
	 *
	 * @param result the result ,if not available return the Test class name
	 * @return the main test name
	 */
	public String getMainTestDescription(ITestResult result) {
		Test test = result.getTestClass().getRealClass().getDeclaredAnnotation(Test.class);
		if (test != null && !"".equalsIgnoreCase(test.description().trim())) {
			return test.description().trim();
		}
		return "";
	}

	/**
	 * Gets the main test name.
	 *
	 * @param result the result ,if not available return the Test class name
	 * @return the main test name
	 */
	public String[] getTestGroups(ITestResult result) {
		return result.getMethod().getGroups();
	}

	/**
	 * Gets the test description.
	 *
	 * @param result the result
	 * @return the test description
	 */
	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult result) {
		ExtentReportManager.startTest(getMainTestName(result), getMainTestDescription(result), getTestName(result),
				getTestDescription(result), getTestGroups(result));
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		Throwable t = result.getThrowable();
		@SuppressWarnings("unused")
		String cause = "";
		if (t != null)
			cause = t.getMessage();
		try {
			ReportUtil.logException(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.getExtentReports().flush();
	}

}
