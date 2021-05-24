package com.paypal.bfs.test.employeeserv.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalEmployeeIdException extends CustomEmployeeException {

	public IllegalEmployeeIdException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

}
