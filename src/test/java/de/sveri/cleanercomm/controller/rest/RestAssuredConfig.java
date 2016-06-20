package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;

import de.sveri.historify.controller.rest.UserLogin;
import de.sveri.historify.helper.JwtHelper;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;

public abstract class RestAssuredConfig {

	@BeforeClass
	public static void configureRestAssured() throws Exception {
		RestAssured.port = 9099;
	}

	protected String getValidToken() {
		UserLogin userLogin = new UserLogin("admin", "admin");

		return JwtHelper.BEARER + given().contentType("application/json").body(userLogin, ObjectMapperType.JACKSON_2)
				.when().post("/apilogin").path("token");
	}

}
