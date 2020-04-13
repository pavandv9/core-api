/**
 * 
 */
package com.core.api;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.core.api.exception.HttpException;
import com.core.api.model.StatusLine;

/**
 * @author Pavan.DV
 *
 */
public final class AbstractResponse extends HttpResponse {

	CloseableHttpResponse response;

	public AbstractResponse(CloseableHttpResponse closeableHttpResponse) {
		super();
		this.response = closeableHttpResponse;
		setHeaders();
		setStatusLine();
		setBody();
	}

	/**
	 * Set status line from response
	 */
	@Override
	protected void setStatusLine() {
		this.statusLine = new StatusLine(this.response.getStatusLine().getStatusCode(),
				this.response.getStatusLine().getReasonPhrase());
	}

	/**
	 * Set body from response
	 */
	@Override
	protected void setBody() {
		try {
			this.body = EntityUtils.toString(response.getEntity());
		} catch (ParseException | IOException e) {
			throw new HttpException(e.getMessage());
		}
	}

	/**
	 * Set headers from response
	 */
	@Override
	protected void setHeaders() {
		for (Header header : response.getAllHeaders())
			this.headers.put(header.getName(), header.getValue());
	}
}
