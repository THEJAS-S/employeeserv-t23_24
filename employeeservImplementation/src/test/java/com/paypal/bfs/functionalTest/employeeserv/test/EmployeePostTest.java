package com.paypal.bfs.functionalTest.employeeserv.test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.bfs.test.employeeserv.EmployeeservApplication;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = EmployeeservApplication.class ,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeePostTest {

	 @LocalServerPort
	 private int port;
	 
	 private Response response;
	 
	 @Before
		public void setup() {
			RestAssured.baseURI = "http://localhost";
			RestAssured.port = port;
	 }
	 
	 @Test
	 public void TestPostRequestEmployee() {
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"Thejas\",\r\n" + 
					"	\"last_name\": \"Simha\",\r\n" + 
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
			
			JsonPath jp = new JsonPath(response.getBody().asInputStream());

			assertEquals(200, response.getStatusCode());
			assertEquals("Thejas", jp.get("first_name"));
			assertEquals("Simha", jp.get("last_name"));
			assertEquals("line1", jp.get("address.line1"));
			assertNotNull(jp.get("id"));
		 
	 }
	 
	 @Test
	 public void TestPostRequestOnlyWithRequiredFields() {
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"Thejas1\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-04-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			
			JsonPath jp = new JsonPath(response.getBody().asInputStream());

			assertEquals(200, response.getStatusCode());
			assertEquals("Thejas1", jp.get("first_name"));
			assertEquals("S", jp.get("last_name"));
			assertEquals("line1", jp.get("address.line1"));
			assertNotNull(jp.get("id"));
		 
	 }
	 
	 @Test
	 public void TestIdempotencyEmployee() {
		 
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"Idempotency\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-04-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");

			JsonPath jp = new JsonPath(response.getBody().asInputStream());
			List<String> error = jp.get("errors");
			assertEquals(409, response.getStatusCode());
			assertEquals("Employee already exist in the system. With firstname: Idempotency lastname: S DateOfBirth: Wed Apr 20 00:00:00 IST 1994", error.get(0));
					
	 }
	 
	 @Test 
	 public void TestInvalidDatePostRequest() {
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"InvalidDate\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-14-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			

			JsonPath jp = new JsonPath(response.getBody().asInputStream());
			List<String> error = jp.get("errors");
			assertEquals(400, response.getStatusCode());
			assertEquals("Incorrect date format. Expected format: dd-MM-yyyy 20-14-1994", error.get(0));
			 
	 }
	 
	 @Test
	 public void TestVeryLongFirstName() {
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"VerryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyLongggggggggggggggggggggggg\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-04-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			

			JsonPath jp = new JsonPath(response.getBody().asInputStream());
			List<String> error = jp.get("errors");
			assertEquals(400, response.getStatusCode());
			assertEquals("firstName: size must be between 1 and 100", error.get(0));
		 
	 }
	 
	 @Test
	 public void TestWithEmptyAddress() {
		 
		 String apiBody = "{\r\n" + 
					"	\"first_name\":\"EmptyAddress\",\r\n" + 
					"	\"last_name\": \"S\",\r\n" + 
					"	\"date_of_birth\":\"20-04-1994\",\r\n" + 
					"	\"address\":{\r\n" + 
					
					"		}\r\n" + 
					"	}";
			
			
			response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			

			JsonPath jp = new JsonPath(response.getBody().asInputStream());
			List<String> error = jp.get("errors");
			List<String> expectedErros = new ArrayList<String>();
			expectedErros.add("address.city: must not be null");
			expectedErros.add("address.zipcode: must not be null");
			expectedErros.add("address.country: must not be null");
			expectedErros.add("address.state: must not be null");
			expectedErros.add("address.line1: must not be null");
			assertEquals(400, response.getStatusCode());
			assertThat(expectedErros, Matchers.containsInAnyOrder(error.toArray()));
		 
	 }
	 
	 @Test
	 public void TestWithEmptyEmplyee() {
		 
		 String apiBody = "{\r\n" + 
					
					"	\"address\":{\r\n" + 
					"		\"line1\":\"line1\",\r\n" + 
					"		\"city\":\"Mysore\",\r\n" + 
					"		\"state\":\"Karnataka\",\r\n" + 
					"		\"country\":\"india\",\r\n" + 
					"		\"zipcode\":\"5670017\"\r\n" + 
					"		}\r\n" + 
					"	}";
		 
		 response =  given().headers("Content-Type","application/json").body(apiBody).post("v1/bfs/employees");
			

			JsonPath jp = new JsonPath(response.getBody().asInputStream());
			List<String> error = jp.get("errors");
			List<String> expectedErros = new ArrayList<String>();
			expectedErros.add("firstName: must not be null");
			expectedErros.add("dateOfBirth: must not be null");
			expectedErros.add("lastName: must not be null");
		 
			assertEquals(400, response.getStatusCode());
			assertThat(expectedErros, Matchers.containsInAnyOrder(error.toArray()));
	 }

}
