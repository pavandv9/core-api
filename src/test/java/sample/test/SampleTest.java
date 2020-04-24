package sample.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.core.api.HttpResponse;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

/**
 * 
 */

/**
 * @author Pavan.DV
 *
 */
@Epic("Regression Tests")
@Feature("Sample Test api's")
public class SampleTest {

	SampleServiceHelper serviceHelper = new SampleServiceHelper();

	@Test(priority = 1, description = "Testing get api path param")
	@Description("Description of the test case if needed")
	@Severity(SeverityLevel.BLOCKER)
	public void testGetApi_usingPathParam() {
		HttpResponse httpResponse = serviceHelper.getApi_usingPathParam(1);
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200, "Status code fail");
	}

	@Test(priority = 2, description = "Testing get api query param")
	public void testGetApi_usingQueryParam() {
		HttpResponse httpResponse = serviceHelper.getApi_usingQueryParam(1);
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200, "Status code fail");
	}

	@Test(priority = 3, description = "Testing post api path param")
	public void testPostApi() {
		HttpResponse httpResponse = serviceHelper.postApi(100, "api title");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 201, "Status code fail");
	}
}
