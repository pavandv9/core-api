/**
 * 
 */
package demotest;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.core.api.HttpRequest;
import com.core.api.HttpResponse;
import com.core.api.ServiceHelper;
import com.core.api.constants.HttpMethod;

/**
 * @author Pavan.DV
 *
 */
public class DemoTest implements ServiceHelper {

	@Test
	public void demo() {
		HttpRequest httpRequest = new HttpRequest();
//		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com");
		httpRequest.addEndPoint("posts");
		httpRequest.addMethod(HttpMethod.POST);
		httpRequest.getBody();
		HttpResponse response = getHttpClient().execute(httpRequest);
		assertEquals(response.getStatusLine().getStatusCode(), 201);
	}
}
