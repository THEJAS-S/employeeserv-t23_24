package com.paypal.bfs.test.employeeserv.exceptions;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends CustomEmployeeException {

	public EmployeeNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}



}
