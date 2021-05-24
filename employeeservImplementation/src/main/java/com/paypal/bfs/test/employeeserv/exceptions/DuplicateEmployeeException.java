package com.paypal.bfs.test.employeeserv.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmployeeException extends CustomEmployeeException {

	public DuplicateEmployeeException(String message) {
		super(message ,HttpStatus.CONFLICT );
	}


}
