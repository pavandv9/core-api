package com.core.api.listners;

import org.testng.annotations.AfterSuite;

import com.core.api.config.ReportConfig;

/**
 * 
 * @author Pavan.DV
 * @since 1.1.0
 */
public interface Report {

	@AfterSuite
	public default void getAllureReport() {
		ReportConfig.uploadAndGetReportLink();
	}
}
