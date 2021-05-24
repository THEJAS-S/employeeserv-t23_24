package com.paypal.bfs.test.employeeserv.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.adapter.DaoEntityToPojoAdapter;
import com.paypal.bfs.test.employeeserv.adapter.PojoToDaoEntityAdapter;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.DuplicateEmployeeException;
import com.paypal.bfs.test.employeeserv.exceptions.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.IllegalEmployeeIdException;
import com.paypal.bfs.test.employeeserv.jpa.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	@Autowired
	private EmployeeValidationService employeeValidationService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Cacheable("employee")
	public Employee findById(String id) throws EmployeeNotFoundException {
		
		log.info("Finding the Employee with Id " + id);
		try {
			Optional<EmployeesEntity> employeesEntityOptional = employeeRepository.findById(Integer.valueOf(id));
			if (employeesEntityOptional.isPresent()) {
				Employee employee = DaoEntityToPojoAdapter.adaptDaoEntityToEmployeePojo(employeesEntityOptional.get());
				log.info(" Found the Employee : " + employee);
				return employee;
			} else {
				log.error("Employee with Id " + id + " is not found in the Db");
				throw new EmployeeNotFoundException("Employee with the Id " + id + " is not present in the system.");
			}
		} catch (NumberFormatException nfe) {
			log.error("Error occured due to incorrect id format {}", nfe);
			throw new IllegalEmployeeIdException("Employee id should be a valid Integer value");
		}

	}
	@CacheEvict(value = "employees", allEntries = true)
	public EmployeesEntity save(Employee employee) throws DuplicateEmployeeException {

		employeeValidationService.validate(employee);
		
		EmployeesEntity employeesEntity = PojoToDaoEntityAdapter.adaptPojoToEmployeesEntity(employee);
		employeesEntity = employeeRepository.save(employeesEntity);
		
		log.info("Employee got saved.  " + employeesEntity);

		return employeesEntity;
	}

	@Cacheable("employees")
	public List<Employee> findAll() {
		
		List<EmployeesEntity> employeeEnityList = employeeRepository.findAll();
		
		List<Employee> employeeList = employeeEnityList.stream().collect(Collectors.mapping(DaoEntityToPojoAdapter::adaptDaoEntityToEmployeePojo, Collectors.toList()));
		
		return employeeList;
	}

	@Caching(evict = {
			@CacheEvict( value = "employees", allEntries = true),
			@CacheEvict( value = "employee",  key = "#id")
	})
	public Employee deleteById(String id) {
		
		log.info("Delete the Employee with Id " + id);
		try {
			Optional<EmployeesEntity> employeesEntityOptional = employeeRepository.findById(Integer.valueOf(id));
			
			if (employeesEntityOptional.isPresent()) {
				Employee employee = DaoEntityToPojoAdapter.adaptDaoEntityToEmployeePojo(employeesEntityOptional.get());
				log.info(" Deleting the Employee : " + employee);
				employeeRepository.deleteById(Integer.valueOf(id));
				return employee;
			} else {
				log.error("Employee with Id " + id + " is not found in the Db");
				throw new EmployeeNotFoundException("Employee with the Id " + id + " is not present in the system.");
			}
		} catch (NumberFormatException nfe) {
			log.error("Error occured due to incorrect id format {}", nfe);
			throw new IllegalEmployeeIdException("Employee id should be a valid Integer value");
		}
		
	}

}
