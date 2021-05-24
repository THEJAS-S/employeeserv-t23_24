package com.paypal.bfs.test.employeeserv.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.DateNotValidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DateValidation extends Validation {

	@Override
	public Employee apply(Employee employee) {

		log.info("Validating the date of birth");

		try {
			LocalDate.parse(employee.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (Exception e) {
			log.error("Incorrect date format. Expected format: dd-MM-yyyy " + employee.getDateOfBirth() , e);
			throw new DateNotValidException(
					"Incorrect date format. Expected format: dd-MM-yyyy " + employee.getDateOfBirth());

		}

		log.info("Validating the date of birth " + employee.getDateOfBirth());

		return employee;
	}

}
