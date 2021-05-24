package com.paypal.bfs.test.employeeserv.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Interface for employee resource operations.
 */
@Api
@RequestMapping("/v1")
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get a Employee by its id" ,  response = Employee.class)
	@ApiResponses(value = { 
			  @ApiResponse(code = 200, message = "Found the Employee"),
			  @ApiResponse(code = 400, message = "Invalid id supplied"), 
			  @ApiResponse(code = 404, message = "Employee not found") })
    @RequestMapping("/bfs/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") String id);

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new Employee" ,  response = Employee.class)
	@ApiResponses(value = { 
			  @ApiResponse(code = 200, message = "Employee got created"),
			  @ApiResponse(code = 409, message = "Employee already present in the system") })
    @PostMapping("/bfs/employees")
    ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee);
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get All Employees" )
    @RequestMapping("/bfs/employees")
    ResponseEntity< List<Employee>> employeeGetAll();
	
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete a Employee by its id" ,  response = Employee.class)
	@ApiResponses(value = { 
			  @ApiResponse(code = 200, message = "Deleted the Employee"),
			  @ApiResponse(code = 400, message = "Invalid id supplied"), 
			  @ApiResponse(code = 404, message = "Employee not found") })
    @DeleteMapping("/bfs/employees/{id}")
    ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") String id);
	
	
	
	
}
