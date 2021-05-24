package com.paypal.bfs.test.employeeserv.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.DateNotValidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(DateValidation.class)
public class DateValidationTest {

	private DateValidation dateValidation;

	@Before
	public void init() {

		dateValidation = new DateValidation();

	}
	
	@Test
	public void testValidDatePostive() {
		
		log.info("Running testValidDatePostive");
		
		Employee employee = getSampleEmployee();
		dateValidation.apply(employee);
		
	}
	
	@Test(expected = DateNotValidException.class)
	public void testValidDateNegative() {
		
		log.info("Running testValidDateNegative");

		Employee employee = getSampleEmployee();
		employee.setDateOfBirth("20-20-1994");
		dateValidation.apply(employee);
		
	}
	
	private Employee getSampleEmployee() {
		Address address = new Address();
        address.setLine1("Hebbal Mayure");
        address.setCity("Mysore");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setZipcode("570017");
        Employee employee = new Employee();
        employee.setFirstName("Thejas");
        employee.setLastName("S");
        employee.setDateOfBirth("20-04-1994");
        employee.setAddress(address);
        
        return employee;
	}

}
