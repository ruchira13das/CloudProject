/**
 * 
 */
package com.sjsu.cmpe282.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ruchira
 * If the HTTP request gets exception as NOT FOUND
 * 	
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Record not found in database!")
public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2890201840676042254L;

}
