package com.paypal.bfs.functionalTest.employeeserv.test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = EmployeeservApplication.class ,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeGetTest {

	
	 @LocalServerPort
	 private int port;
	 
	 private Response response;
	 
	 Employee employee;
	 	    
	    @Before
		public void setup() {
			RestAssured.baseURI = "http://localhost";
			RestAssured.port = port;
			
			String apiBody = "{\r\n" + 
					"	\"first_name\":\"Thejas\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-04-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"line2\":\"line2\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
		}
	
	@Test
	public void TestGetRequestEmployee() throws IOException {

		response = given().headers("Content-Type", "application/json").get("v1/bfs/employees/1");

		JsonPath jp = new JsonPath(response.getBody().asInputStream());

		assertEquals(200, response.getStatusCode());
		assertEquals("Thejas", jp.get("first_name"));
		assertEquals("S", jp.get("last_name"));
		assertEquals("line1", jp.get("address.line1"));
		assertNotNull(jp.get("id"));

	}

	@Test
	public void TestInvalidEmployeeIdTypeGetRequest() {

		response = given().headers("Content-Type", "application/json").get("/v1/bfs/employees/R");

		JsonPath jp = new JsonPath(response.getBody().asInputStream());

		List<String> error = jp.get("errors");

		assertEquals(400, response.getStatusCode());
		assertEquals("Employee id should be a valid Integer value", error.get(0));

	}

	@Test
	public void TestEmployeeNotFoundGetRequest() {

		response = given().headers("Content-Type", "application/json").get("v1/bfs/employees/2");
		JsonPath jp = new JsonPath(response.getBody().asInputStream());

		List<String> error = jp.get("errors");
		assertEquals(404, response.getStatusCode());
		assertEquals("Employee with the Id 2 is not present in the system.", error.get(0));
	}
}
