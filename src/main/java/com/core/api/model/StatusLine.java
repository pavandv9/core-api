/**
 * 
 */
package com.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public class StatusLine {

	private int statusCode;
	private String statusMessage;
}
