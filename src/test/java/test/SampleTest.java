package test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.core.api.Request;
import com.core.api.Response;
import com.core.api.constants.HttpMethod;
import com.core.api.listners.MailListner;
import com.core.api.utils.ILogger;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Listeners({MailListner.class})
public class SampleTest implements ILogger {
	
	@Test(description = "Core API Test", invocationCount = 1)
	public void reqResApi() {
		Request httpRequest = new Request();
		Response res =	httpRequest.addBaseUrl("https://reqres.in/api/users/2").addMethod(HttpMethod.GET).send();
		assertEquals(res.getStatusCode(), 200);
	}

	@Test(description = "Sample test case")
	@Severity(SeverityLevel.MINOR)
	public void test1() {
		LOG.info("Using logger: test case 2");
		Request httpRequest = new Request();
		Response response = httpRequest.addBaseUrl("https://test-dummy-api.herokuapp.com").addMethod(HttpMethod.GET).send();
		assertEquals(response.getStatusCode(), 200, "Status not matching");
	}
}
