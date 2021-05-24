package com.paypal.bfs.test.employeeserv.adapter;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.constant.Constants;
import com.paypal.bfs.test.employeeserv.exceptions.DateNotValidException;
import com.paypal.bfs.test.employeeserv.jpa.model.AddressEntity;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PojoToDaoEntityAdapter {


	public static EmployeesEntity adaptPojoToEmployeesEntity(Employee employee) throws DateNotValidException {

		EmployeesEntity employeesEntity = new EmployeesEntity();
		employeesEntity.setFirstName(employee.getFirstName());
		employeesEntity.setLastName(employee.getLastName());
		try {
			employeesEntity.setDateOfBirth(Constants.DATE_FORMAT.parse(employee.getDateOfBirth()));
		} catch (Exception e) {
			log.error(" Error while converting Pojo object to EmployeeEntity for the date of Birth Field" , e );
			throw new DateNotValidException(" Date is not in valid format " + employee.getDateOfBirth());
		}
		employeesEntity.setAddressEntity(adaptPojoToAddressEntity(employee.getAddress()));
		return employeesEntity;
	}

	public static AddressEntity adaptPojoToAddressEntity(Address address) {

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setCity(address.getCity());
		addressEntity.setCountry(address.getCountry());
		addressEntity.setState(address.getState());
		addressEntity.setZipcode(address.getZipcode());
		addressEntity.setLine1(address.getLine1());
		addressEntity.setLine2(address.getLine2());
		return addressEntity;
	}

}
