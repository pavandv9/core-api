package com.core.api.config;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * This class is responsible for generating the Extent html report.
 * 
 * @author Pavan.DV
 * @since 1.3
 */
public class ExtentReportManager {

	/** The Constant PROJECT_NAME. */
	private static final String PROJECT_NAME="PROJECT_NAME";
	
	/** The Constant REPORT_NAME. */
	private static final String REPORT_NAME ="REPORT_NAME";
    /**
     * The extent reports.
     */
    private static ExtentReports extentReports;

    /**
     * The map that contains the test objects.
     */
    private static final Map<Long, ExtentTest> map = new HashMap<>();

    /**
     * The map that contains the test objects in each test class.
     */
    private static final Map<String, ExtentTest> subMap = new HashMap<>();

    /**
     * Gets the extent reports.
     *
     * @return the extent reports
     */
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = getSparkReporter();
            extentReports.attachReporter(spark);
            extentReports.setAnalysisStrategy(AnalysisStrategy.TEST);

        }
        return extentReports;
    }

    /**
     * Gets the spark reporter.
     *
     * @return the spark reporter
     */
    private static ExtentSparkReporter getSparkReporter() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target" + File.separator + REPORT_NAME);
        spark.viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{
                        ViewName.TEST,
                        ViewName.DASHBOARD,
                        ViewName.CATEGORY,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                })
                .apply();
        spark.config().thumbnailForBase64(true);
        spark.config().setReportName(PROJECT_NAME);
        return spark;
    }

    /**
     * Start test.
     *
     * @param testClassName the test class name
     * @param testClassDescription the test class description
     * @param testName the test name
     * @param desc     the desc
     * @param testGroups the test groups
     */
    public synchronized static void startTest(String testClassName, String testClassDescription, String testName, String desc, String[] testGroups) {
        ExtentTest mainTest = subMap.get(testClassName);
        if (mainTest == null) {
            mainTest = getExtentReports().createTest(testClassName, testClassDescription);
            subMap.put(testClassName, mainTest);
        }
        ExtentTest test = mainTest.createNode(testName, desc);
        if (testGroups != null)
            test.assignCategory(testGroups);
        map.put(Thread.currentThread().getId(), test);
    }

    /**
     * Gets the current test.
     *
     * @return the current test
     */
    public synchronized static ExtentTest getCurrentTest() {
        return map.get(Thread.currentThread().getId());
    }
    
}
