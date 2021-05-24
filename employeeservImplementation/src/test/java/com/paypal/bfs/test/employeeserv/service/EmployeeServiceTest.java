package com.paypal.bfs.test.employeeserv.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import com.paypal.bfs.test.employeeserv.adapter.PojoToDaoEntityAdapter;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.IllegalEmployeeIdException;
import com.paypal.bfs.test.employeeserv.jpa.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(EmployeeService.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private EmployeeValidationService employeeValidationService;



	@Test
	public void testFindById() {

		log.info("Running testFindById");

		Mockito.doReturn(Optional.of(getSampleEmployeeEntity())).when(employeeRepository).findById(1);

		Employee resultEmployee = employeeService.findById("1");

		assertEquals(resultEmployee.getFirstName(), getSampleEmployee().getFirstName());
		assertEquals(resultEmployee.getLastName(), getSampleEmployee().getLastName());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testSaveNegativeEmployeeNotFound() {

		log.info("Running testSaveNegativeEmployeeNotFound");

		Mockito.doReturn(Optional.ofNullable(null)).when(employeeRepository).findById(1);

		Employee resultEmployee = employeeService.findById("1");

	}

	@Test(expected = IllegalEmployeeIdException.class)
	public void testSaveNegativeIllegalEmployeeId() {
		
		log.info("Running testSaveNegativeIllegalEmployeeId");

		Employee resultEmployee = employeeService.findById("q");
	}
	
	@Test
	public void testSavePositive() {
		
		log.info("Running testSavePositive ");
		
		Employee inputEmployee = getSampleEmployee();
		
		Mockito.doNothing().when(employeeValidationService).validate(inputEmployee);
		
		Mockito.doReturn(getSampleEmployeeEntity()).when(employeeRepository).save(getSampleEmployeeEntity());
		
		EmployeesEntity employeeEntity = employeeService.save(getSampleEmployee());
		
		assertEquals(getSampleEmployeeEntity(), employeeEntity);
		
		
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
		employee.setId(1);

		return employee;
	}

	private EmployeesEntity getSampleEmployeeEntity() {

		EmployeesEntity employeesEntity = PojoToDaoEntityAdapter.adaptPojoToEmployeesEntity(getSampleEmployee());
		return employeesEntity;

	}

}
