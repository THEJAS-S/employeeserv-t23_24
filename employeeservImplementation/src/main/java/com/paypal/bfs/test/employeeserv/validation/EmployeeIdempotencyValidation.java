package com.paypal.bfs.test.employeeserv.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.constant.Constants;
import com.paypal.bfs.test.employeeserv.exceptions.DateNotValidException;
import com.paypal.bfs.test.employeeserv.exceptions.DuplicateEmployeeException;
import com.paypal.bfs.test.employeeserv.jpa.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Component
public class EmployeeIdempotencyValidation extends Validation {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee apply(Employee employee) {

		log.info(" Verifying if the given employee is alread present in the system or not");
		
		Date dateOfBirth;
		try {
			dateOfBirth = Constants.DATE_FORMAT.parse(employee.getDateOfBirth());
		} catch (ParseException e) {
			throw new DateNotValidException(" Date is not in valid format " + employee.getDateOfBirth());
		}
		
		log.info("dob" + dateOfBirth);
		

		Optional<EmployeesEntity> employeesEntity = employeeRepository
				.findByFirstNameAndLastNameAndDateOfBirth(employee.getFirstName(), employee.getLastName(), dateOfBirth);


		if (employeesEntity.isPresent()) {
			throw new DuplicateEmployeeException(
					"Employee already exist in the system. With firstname: " + employee.getFirstName() + " lastname: "
							+ employee.getLastName() + " DateOfBirth: " + dateOfBirth);
		}

		return employee;
	}


}
