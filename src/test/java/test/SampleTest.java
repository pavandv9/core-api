package test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.core.api.HttpRequest;
import com.core.api.HttpResponse;
import com.core.api.Request;
import com.core.api.constants.HttpMethod;
import com.core.api.listners.MailListner;
import com.core.api.listners.Report;
import com.core.api.utils.ILogger;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Listeners(MailListner.class)
public class SampleTest implements Report, ILogger {
	
	@Test(description = "Core API Test")
	public void reqResApi() {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://reqres.in/api/users/2").addMethod(HttpMethod.GET);
		HttpResponse res = Request.execute(httpRequest);
		assertEquals(res.getStatusCode(), 200);
	}

	@Test(description = "Sample test case")
	@Severity(SeverityLevel.MINOR)
	public void test1() {
		LOG.info("Using logger: test case 2");
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://test-dummy-api.herokuapp.com").addMethod(HttpMethod.GET);
		HttpResponse response =  Request.execute(httpRequest);	
		assertEquals(response.getStatusCode(), 200, "Status not matching");
	}
}
