package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.net.URI;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserRepository;
import de.sveri.historify.entity.ClientId;
import io.restassured.mapper.ObjectMapperType;


public class HistoryApiTest extends RestAssuredConfig {
	
	@Autowired
	BrowserRepository browserRep;
	
	@Test
	public void postBrowserLink() throws Exception {
		BrowserLink link = new BrowserLink();
		link.setDescription("desc");
		link.setUri(new URI("http://sveri.de"));
		link.setClientId(ClientId.CHROME);
		link.setVisitedAt(new Date());
		link.setTitle("Cool Page");
		
		given().header("Authorization", getValidToken())
		.contentType("application/json").body(link, ObjectMapperType.JACKSON_2)
		.when().post("/api/browserlink").then().assertThat().statusCode(200);
		
		System.out.println(browserRep.findAll());
		
	}

}
