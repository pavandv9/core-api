/**
 * 
 */
package com.core.api.exception;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpException(@NonNull Throwable e) {
		super(e);
	}

	public HttpException(@NonNull String message) {
		super(message);
	}

	public HttpException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}
}
