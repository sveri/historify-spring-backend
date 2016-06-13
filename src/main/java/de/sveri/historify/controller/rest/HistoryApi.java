package de.sveri.historify.controller.rest;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HistoryApi {
	
	@RequestMapping(path = "/browserlink", method = RequestMethod.POST)
	public @ResponseBody OKResponse getTest(@RequestHeader(value="Authorization") String authorizationToken){		
		return new OKResponse("Added browser history");
	}
	
	

}
