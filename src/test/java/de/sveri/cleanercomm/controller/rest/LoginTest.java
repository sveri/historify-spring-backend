package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sveri.historify.Application;
import de.sveri.historify.controller.rest.UserLogin;
import io.restassured.mapper.ObjectMapperType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=0")
public class LoginTest {
	
	@Test
	public void login() throws Exception {
		UserLogin userLogin = new UserLogin("sveri", "zzzzzz");

		given().contentType("application/json").body(userLogin, ObjectMapperType.JACKSON_2)
		.when().post("/apilogin")
		.then().body("token", notNullValue());
	}


}
