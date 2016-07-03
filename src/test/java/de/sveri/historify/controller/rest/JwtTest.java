package de.sveri.historify.controller.rest;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sveri.historify.Application;
import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.helper.JwtHelper;
import io.restassured.mapper.ObjectMapperType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=9099")
public class JwtTest {


	private static final String API_BROWSERLINK = "/api/browserlink";

	@Test
	public void invalidJwtToken() throws Exception {
		BrowserLink link = new BrowserLink();
		link.setDescription("desc");
		given().header("Authorization", "foobar").contentType(MediaType.APPLICATION_JSON).body(link, ObjectMapperType.JACKSON_2).when().post(API_BROWSERLINK)
		.then().assertThat().statusCode(401);

		given().header("Authorization", JwtHelper.BEARER + " some_token").contentType(MediaType.APPLICATION_JSON).body(link, ObjectMapperType.JACKSON_2).when().post(API_BROWSERLINK)
		.then().assertThat().statusCode(401);
	}	

}
