/**
 * 
 */
package com.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Pavan.DV
 *
 */
@Getter
@ToString
@AllArgsConstructor
public class StatusLine {

	private int statusCode;
	private String statusMessage;
}
