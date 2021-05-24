package com.paypal.bfs.test.employeeserv.exceptions;

import org.springframework.http.HttpStatus;

public class CustomEmployeeException extends RuntimeException{
	
	private HttpStatus status;

	public CustomEmployeeException(String message, Throwable cause,HttpStatus status) {
		super(message, cause);
		this.setStatus(status);
	}

	public CustomEmployeeException(String message , HttpStatus status) {
		super(message);
		this.setStatus(status);
	}


	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	

}
