package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import de.sveri.historify.controller.rest.UserLogin;
import io.restassured.mapper.ObjectMapperType;

public class LoginTest extends RestAssuredConfig {

	@Test
	public void login() throws Exception {
		UserLogin userLogin = new UserLogin("admin", "admin");

		given().contentType("application/json").body(userLogin, ObjectMapperType.JACKSON_2).when().post("/apilogin")
				.then().body("token", notNullValue());
	}

	@Test
	public void loginFailed() throws Exception {
		UserLogin userLogin = new UserLogin("admin", "wrong_password");

		given().contentType("application/json").body(userLogin, ObjectMapperType.JACKSON_2).when().post("/apilogin")
				.then().body("message", equalTo("Invalid login"));
	}

}
