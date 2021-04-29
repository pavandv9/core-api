package com.core.api.exception;

import lombok.NonNull;

/**
 * 
 * @author pavan-dv
 *
 * @since 1.3.0
 */
public class AwsException extends RuntimeException{

	private static final long serialVersionUID = 7850537839226561575L;
	
	/**
	 * Throws AwsException with Throwable object.
	 * @param e
	 */
	public AwsException(@NonNull Throwable e) {
		super(e);
	}

	/**
	 * Throws AwsException with message.
	 * @param message
	 */
	public AwsException(@NonNull String message) {
		super(message);
	}

	/**
	 * Throws AwsException which takes String message and Throwable object.
	 * @param message
	 * @param throwable
	 */
	public AwsException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}

}
