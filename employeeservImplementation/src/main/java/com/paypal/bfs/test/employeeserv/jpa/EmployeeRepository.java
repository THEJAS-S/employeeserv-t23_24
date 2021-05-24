package com.paypal.bfs.test.employeeserv.jpa;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeesEntity,Integer>{
	
	Optional<EmployeesEntity> findById(Integer id);
	
    Optional<EmployeesEntity> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName , Date dateOfBirth );
    
}
