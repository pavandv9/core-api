/**
 * 
 */
package com.core.api.exception;

/**
 * @author Pavan.DV
 *
 */
public class MethodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MethodNotFoundException(Exception exception) {
		super(exception);
	}

	public MethodNotFoundException(String message) {
		super(message);
	}

}
