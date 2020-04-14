package sample.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.core.api.HttpResponse;

/**
 * 
 */

/**
 * @author Pavan.DV
 *
 */
public class SampleTest {

	SampleServiceHelper serviceHelper = new SampleServiceHelper();

	@Test(priority = 1)
	public void testGetApi_usingPathParam() {
		HttpResponse httpResponse = serviceHelper.getApi_usingPathParam();
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200, "Status code fail");
	}

	@Test(priority = 2)
	public void testGetApi_usingQueryParam() {
		HttpResponse httpResponse = serviceHelper.getApi_usingQueryParam();
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200, "Status code fail");
	}

	@Test(priority = 3)
	public void testPostApi() {
		HttpResponse httpResponse = serviceHelper.postApi();
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 201, "Status code fail");
	}
}
