package de.sveri.cleanercomm.controller.rest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sveri.historify.Application;
import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkRepository;
import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;
import io.restassured.mapper.ObjectMapperType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=9099")
public class HistoryApiTest extends RestAssuredConfig {

	@Autowired
	BrowserLinkRepository browserRep;

	@Autowired
	UserRepository userRepo;

	@Test
	public void postBrowserLink() throws Exception {
		BrowserLink link = new BrowserLink();
		link.setDescription("desc");
		link.setUri(new URI("http://sveri.de"));
		link.setClientId("CHROME");
		link.setVisitedAt(new Date());
		link.setTitle("Cool Page");

		given().header("Authorization", getValidToken()).contentType("application/json")
				.body(link, ObjectMapperType.JACKSON_2).when().post("/api/browserlink").then().assertThat()
				.statusCode(200);

		User admin = userRepo.findOneByUserName("admin");

		List<BrowserLink> links = browserRep.findAllByUserOrderByVisitedAtDesc(admin);

		BrowserLink browserLink = links.get(0);

		assertEquals("Description", "desc", browserLink.getDescription());
		assertEquals("Title", "Cool Page", browserLink.getTitle());

	}

}
