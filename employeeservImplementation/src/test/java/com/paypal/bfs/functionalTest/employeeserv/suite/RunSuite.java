package com.paypal.bfs.functionalTest.employeeserv.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.paypal.bfs.functionalTest.employeeserv.test.EmployeeGetTest;
import com.paypal.bfs.functionalTest.employeeserv.test.EmployeePostTest;


 
/**
 * Use this class to run test suite
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	EmployeeGetTest.class,
	EmployeePostTest.class
})
public class RunSuite {
}
