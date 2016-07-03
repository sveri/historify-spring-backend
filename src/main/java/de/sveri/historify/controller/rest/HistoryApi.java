package de.sveri.historify.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.helper.JwtHelper;
import de.sveri.historify.service.UriExtractor;

@RestController
@RequestMapping("/api")
public class HistoryApi {

	@Autowired
	BrowserLinkPaginationRepository repo;

	@Autowired
	JwtHelper jwtHelper;

	@Autowired
	UserRepository userRepo;

	@RequestMapping(path = "/browserlink", method = RequestMethod.POST)
	public @ResponseBody Response saveBrowserLink(@RequestHeader(value = "Authorization") String authorizationToken,
			@RequestBody BrowserLink link) throws Exception {
		link.setUser(userRepo.findOneByUserName(jwtHelper.getSubject(authorizationToken)));
		link.setUriKeywords(UriExtractor.extractKeywords(link.getUri()));
		repo.save(link);
		return new Response("Added browser history");
	}

}
