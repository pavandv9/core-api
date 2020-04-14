/**
 * 
 */
package sample.test;

import com.core.api.HttpRequest;
import com.core.api.HttpResponse;
import com.core.api.ServiceHelper;
import com.core.api.constants.HttpMethod;

import lombok.Data;

/**
 * @author Pavan.DV
 *
 */
public class SampleServiceHelper implements ServiceHelper {

	public HttpResponse getApi_usingQueryParam() {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com/").addEndPoint("comments")
				.addMethod(HttpMethod.GET).addQueryParam("postId", 1);
		return getHttpClient().execute(httpRequest);
	}

	public HttpResponse getApi_usingPathParam() {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com/").addEndPoint("posts/{postId}")
				.addMethod(HttpMethod.GET).addPathParamValue("postId", 1);
		return getHttpClient().execute(httpRequest);
	}

	public HttpResponse postApi() {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com/");
		httpRequest.addEndPoint("posts");
		httpRequest.addMethod(HttpMethod.POST);

		MyRequestModel requestModel = new MyRequestModel();
		requestModel.setBody("test body");
		requestModel.setId(101);
		requestModel.setName("test name");
		requestModel.setUserId(1);
		httpRequest.addBody(requestModel);
		return getHttpClient().execute(httpRequest);
	}

	@Data
	public class MyRequestModel {

		private int id;
		private String name;
		private String body;
		private int userId;
	}
}
