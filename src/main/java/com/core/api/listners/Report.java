package com.core.api.listners;

import org.testng.annotations.AfterSuite;

import com.core.api.config.ReportConfig;

public interface Report {

	@AfterSuite
	public default void getAllureReport() {
		ReportConfig.uploadAndGetReportLink();
	}
}
