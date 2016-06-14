package de.sveri.historify.controller.rest;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.sveri.historify.entity.BrowserLink;

@RestController
@RequestMapping("/api")
public class HistoryApi {
	
	@RequestMapping(path = "/browserlink", method = RequestMethod.POST)
	public @ResponseBody OKResponse getTest(@RequestHeader(value="Authorization") String authorizationToken, BrowserLink link){		
		return new OKResponse("Added browser history");
	}
	
	

}
