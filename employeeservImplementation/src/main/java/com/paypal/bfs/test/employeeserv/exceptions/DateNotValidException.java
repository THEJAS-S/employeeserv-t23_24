package com.paypal.bfs.test.employeeserv.exceptions;

import org.springframework.http.HttpStatus;

public class DateNotValidException extends CustomEmployeeException {

	public DateNotValidException( String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}




}
