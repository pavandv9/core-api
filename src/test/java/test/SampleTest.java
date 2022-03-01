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

// TODO: Auto-generated Javadoc
/**
 * The Class SampleTest.
 */
@Listeners({MailListner.class})
public class SampleTest implements ILogger {
	
	/**
	 * Req res api.
	 */
	@Test(description = "Core API Test")
	public void reqResApi() {
		Request request = new Request();
		Response res =	request.addBaseUrl("https://reqres.in/api/users/2").addMethod(HttpMethod.GET).send();
		assertEquals(res.getStatusCode(), 200);
	}

	/**
	 * Using logger and endpoint.
	 */
	@Test(description = "Sample test case")
	@Severity(SeverityLevel.MINOR)
	public void usingLoggerAndEndpoint() {
		LOG.info("Using logger: test case 2");
		Request request = new Request();
		Response response = request.addBaseUrl("https://reqres.in").addEndPoint("/api/users/2").addMethod(HttpMethod.GET).send();
		assertEquals(response.getStatusCode(), 200, "Status not matching");
	}

}
