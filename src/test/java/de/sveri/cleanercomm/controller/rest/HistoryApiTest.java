package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import de.sveri.historify.entity.BrowserLink;
import io.restassured.mapper.ObjectMapperType;


public class HistoryApiTest extends RestAssuredConfig {
	
	@Test
	public void postBrowserLink() throws Exception {
		BrowserLink link = new BrowserLink();
		link.setDescription("desc");
		
		given().header("Authorization", getValidToken())
		.contentType("application/json").body(link, ObjectMapperType.JACKSON_2)
		.when().post("/api/browserlink").then().log().body();
//		.then().body("token", notNullValue());
//		UserLogin userLogin = new UserLogin("sveri", "zzzzzz");
//		ResponseEntity<String> entity = restTemplate.postForEntity("/apilogin", userLogin, String.class);
//		
//		assertEquals("foo", entity.getBody());
	}

}
