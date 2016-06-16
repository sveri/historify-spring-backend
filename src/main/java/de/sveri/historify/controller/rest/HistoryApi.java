package de.sveri.historify.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkRepository;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.helper.JwtHelper;

@RestController
@RequestMapping("/api")
public class HistoryApi {
	
	@Autowired
	BrowserLinkRepository repo;
	
	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(path = "/browserlink", method = RequestMethod.POST)
	public @ResponseBody OKResponse getTest(@RequestHeader(value="Authorization") String authorizationToken, @RequestBody BrowserLink link){
		link.setUser(userRepo.findOneByUserName(jwtHelper.getSubject(authorizationToken)));
		repo.save(link);
		return new OKResponse("Added browser history");
	}
	
	

}
