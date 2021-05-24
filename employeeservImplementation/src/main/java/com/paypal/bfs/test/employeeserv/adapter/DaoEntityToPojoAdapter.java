package com.paypal.bfs.test.employeeserv.adapter;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.constant.Constants;
import com.paypal.bfs.test.employeeserv.jpa.model.AddressEntity;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

public class DaoEntityToPojoAdapter {
		
	public static Employee adaptDaoEntityToEmployeePojo(EmployeesEntity employeesEntity) {
		Employee employee = new Employee();
        employee.setId(employeesEntity.getId());
        employee.setFirstName(employeesEntity.getFirstName());
        employee.setLastName(employeesEntity.getLastName());
        employee.setDateOfBirth(Constants.DATE_FORMAT.format(employeesEntity.getDateOfBirth()));
        employee.setAddress(adaptDaoEntityToAddressPojo(employeesEntity.getAddressEntity()));
        return employee;
	}
	
	public static Address adaptDaoEntityToAddressPojo(AddressEntity addressEntity) {
		
		Address address = new Address();
        address.setLine1(addressEntity.getLine1());
        address.setLine2(addressEntity.getLine2());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        address.setCountry(addressEntity.getCountry());
        address.setZipcode(addressEntity.getZipcode());
        return address;
		
	}

}
