# employeeserv

## Solution

### Details
 - Used H2 DB. Table details are present in the file [data.sql](employeeservImplementation/src/main/resources/data.sql)
 - Used caching for the get request.
 - Used swagger validation to validate the incoming request with respect length, format, type and required fields. Swagger UI link `http://localhost:8080/swagger-ui.html`
 - Validation is done by EmployeeValidationService and have used chain of responsibilty pattern.
 - For Idempotency of employee I'm considering if first name , last name and date of birth fields.
 - For Tracking the logs from a request have used Correlation Id, added using Interceptor.
 - Event Integrating testing is present in employeeservImplmenetation module cause of dependency issues.
 - Added delet and getAll and delete by id api operations as well.


## API Details

#### Get Request

---------------
	Get Employee By Id
	Url: http://localhost:8080/v1/bfs/employees/1
	Method: GET
	Response: {
		"id": 1,
		"first_name": "Thejas",
			"last_name": "S",
			"date_of_birth": "01-12-1994",
			"address": {
				"line1": "line1",
				"line2": "line2",
				"city": "Mysore",
				"state": "Karnataka",
				"country": "India",
				"zipcode": "570017"
			}
		}

#### Post Request	
---------------
	Create Employee
	Url: http://localhost:8080/v1/bfs/employees
	Method: POST
	Request: {
		"first_name": "Thejas",
			"last_name": "Simha",
			"date_of_birth": "01-12-1994",
			"address": {
				"line1": "line1",
				"line2": "line2",
				"city": "Mysore",
				"state": "Karnataka",
				"country": "India",
				"zipcode": "570017"
			}
		}
	Response: {
		"id": 2,
		"first_name": "Thejas",
			"last_name": "Simha",
			"date_of_birth": "01-12-1994",
			"address": {
				"line1": "line1",
				"line2": "line2",
				"city": "Mysore",
				"state": "Karnataka",
				"country": "India",
				"zipcode": "570017"
			}
		}
		
#### Delete Request	
---------------
	Delete Employee by its Id
	Url: http://localhost:8080/v1/bfs/employees/1
	Method: DELETE	
	
#### GetAll  Request	
---------------
	Get All employees
	Url: http://localhost:8080/v1/bfs/employees
	Method: GET				


## Things which can be added

 - Load Test using Jmeter.
 - Spring Secuirty
 - LoadBalancer, Gateway 
 


## Application Overview
employeeserv is a spring boot rest application which would provide the CRUD operations for `Employee` resource.

There are three modules in this application
- employeeservApi - This module contains the interface.
	- `v1/schema/employee.json` defines the employee resource.
	- `jsonschema2pojo-maven-plugin` is being used to create `Employee POJO` from json file.
	- `EmployeeResource.java` is the interface for CRUD operations on `Employee` resource.
		- GET `/v1/bfs/employees/{id}` endpoint is defined to fetch the resource.
- employeeservImplementation - This module contains the implementation for the rest endpoints.
	- `EmployeeResourceImpl.java` implements the `EmployeeResource` interface.
- employeeservFunctionalTests - This module would have the functional tests.

## How to run the application
- Please have Maven version `3.3.3` & Java 8 on your system.
- Use command `mvn clean install` to build the project.
- Use command `mvn spring-boot:run` from `employeeservImplementation` folder to run the project.
- Use postman or curl to access `http://localhost:8080/v1/bfs/employees/1` GET endpoint. It will return an Employee resource.

## Assignment
We would like you to enhance the existing project and see you complete the following requirements:

- `employee.json` has only `name`, and `id` elements. Please add `date of birth` and `address` elements to the `Employee` resource. Address will have `line1`, `line2`, `city`, `state`, `country` and `zip_code` elements. `line2` is an optional element.
- Add one more operation in `EmployeeResource` to create an employee. `EmployeeResource` will have two operations, one to create, and another to retrieve the employee resource.
- Implement create and retrieve operations in `EmployeeResourceImpl.java`.
- Resource created using create endpoint should be retrieved using retrieve/get endpoint.
- Please use h2 in-memory database or any other in-memory database to persist the `Employee` resource. Dependency for h2 in-memory database is already added to the parent pom.
- Please make sure the validations are done for the requests.
- Response codes are as per rest guidelines.
- Error handling in case of failures.

## Assignment submission
Thank you very much for your time to take this test. Please upload this complete solution in Github and send us the link to `bfs-sor-interview@paypal.com`.
