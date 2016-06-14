package de.sveri.cleanercomm.controller.rest;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import de.sveri.historify.Application;
import de.sveri.historify.controller.rest.UserLogin;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=9090")
public class HistoryApiTest {
	
//	RestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void postBrowserLink() throws Exception {
		given().params("username", "admin", "password", "admin")
		.when().post("/apilogin")
		.then().body("token", notNullValue());
//		UserLogin userLogin = new UserLogin("sveri", "zzzzzz");
//		ResponseEntity<String> entity = restTemplate.postForEntity("/apilogin", userLogin, String.class);
//		
//		assertEquals("foo", entity.getBody());
	}

}
