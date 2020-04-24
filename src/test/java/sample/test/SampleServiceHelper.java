/**
 * 
 */
package sample.test;

import com.core.api.HttpRequest;
import com.core.api.HttpResponse;
import com.core.api.ServiceHelper;
import com.core.api.constants.HttpMethod;

import io.qameta.allure.Step;
import lombok.Data;

/**
 * @author Pavan.DV
 *
 */
public class SampleServiceHelper implements ServiceHelper {

	@Step("Get api using query param")
	public HttpResponse getApi_usingQueryParam(int postId) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com/").addEndPoint("comments")
				.addMethod(HttpMethod.GET).addQueryParam("postId", postId);
		return getHttpClient().execute(httpRequest);
	}

	@Step("Get api using path param")
	public HttpResponse getApi_usingPathParam(int postId) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://jsonplaceholder.typicode.com/").addEndPoint("posts/{postId}")
				.addMethod(HttpMethod.GET).addPathParamValue("postId", postId);
		return getHttpClient().execute(httpRequest);
	}

	@Step("Post api")
	public HttpResponse postApi(int userId, String title) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addBaseUrl("https://reqres.in/api/users/");
		httpRequest.addMethod(HttpMethod.POST);

		MyRequestModel requestModel = new MyRequestModel();
		requestModel.setUserId(userId);
		requestModel.setTitle(title);
		requestModel.setId(1);
		requestModel.setBody("body");

		httpRequest.addBody(requestModel);
		return getHttpClient().execute(httpRequest);
	}

}

@Data
class MyRequestModel {

	private int userId;
	private int id;
	private String title;
	private String body;

}
