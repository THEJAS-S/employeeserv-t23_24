package com.paypal.bfs.test.employeeserv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.validation.DateValidation;
import com.paypal.bfs.test.employeeserv.validation.EmployeeIdempotencyValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeValidationService {

	@Autowired
	private EmployeeIdempotencyValidation employeeIdempotencyValidation;
	
	@Autowired
	private DateValidation dateValidation;

	public void validate(Employee employee) {
		
		log.info("Validating the Employee.");
		
		dateValidation.andThen(
		employeeIdempotencyValidation)
		.apply(employee);

	}
}
