package com.paypal.bfs.test.employeeserv.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class for employee resource.
 */
@Slf4j
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeService employeeService;
	

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {
		
		log.info("Api request for find the employee with Id " + id);

		Employee employee = employeeService.findById(id);
		
		return new ResponseEntity<>(employee, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Employee> createEmployee(@Valid Employee employee)  {
		
		log.info("Api request for creating the new Employee : " + employee);
		
		EmployeesEntity employeesEntity = employeeService.save(employee);
		
		return employeeGetById(String.valueOf(employeesEntity.getId()));
	}

	@Override
	public ResponseEntity< List<Employee>> employeeGetAll() {
		
		log.info("Api request to get all the employees Information");
		
		List<Employee> employees = employeeService.findAll(); 
		
		return new ResponseEntity<>(employees, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<Employee> deleteEmployeeById(String id) {
		
		log.info("Api request to the delete the employee with Id " + id);
		
		Employee employee =  employeeService.deleteById(id);
		
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
}
