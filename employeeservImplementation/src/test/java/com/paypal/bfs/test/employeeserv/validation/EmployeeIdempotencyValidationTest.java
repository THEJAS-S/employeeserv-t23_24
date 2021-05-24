package com.paypal.bfs.test.employeeserv.validation;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.DuplicateEmployeeException;
import com.paypal.bfs.test.employeeserv.jpa.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(EmployeeIdempotencyValidation.class)
public class EmployeeIdempotencyValidationTest {

	
	private EmployeeIdempotencyValidation employeeIdempotencyValidation;
	
	private EmployeeRepository employeeRepository;
	
	@Before
	public void init() {
		
		employeeIdempotencyValidation = new EmployeeIdempotencyValidation();
		employeeRepository = PowerMockito.mock(EmployeeRepository.class);
		employeeIdempotencyValidation.setEmployeeRepository(employeeRepository);
		
	}

	@Test
	public void testValidationWithEmployeeNotInSystem() {
		
		log.info("Running testValidationWithEmployeeNotInSystem");
		
		Employee employee = getSampleEmployee();
        
		Mockito.doReturn( Optional.ofNullable(null)).when(employeeRepository).
		findByFirstNameAndLastNameAndDateOfBirth(any(), any(), any());
        
        Employee testEmployee = employeeIdempotencyValidation.apply(employee);
          
        assertEquals(testEmployee, employee);
		
	}
	
	@Test(expected = DuplicateEmployeeException.class)
	public void testValidationWithEmployeePresentInSystem() {
		
		log.info("Running testValidationWithEmployeePresentInSystem");
		
		Employee employee = getSampleEmployee();
		
		Mockito.doReturn( Optional.ofNullable(employee)).when(employeeRepository).
		findByFirstNameAndLastNameAndDateOfBirth(any(), any(), any());
		
		 Employee testEmployee = employeeIdempotencyValidation.apply(employee);
		
		
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
