package de.sveri.historify.controller.rest;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

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
	
	private final Timer timer = new Timer();

	@RequestMapping(path = "/browserlink", method = RequestMethod.POST)
	public @ResponseBody Response saveBrowserLink(@RequestHeader(value = "Authorization") String authorizationToken,
			@RequestBody BrowserLink link) throws Exception {
		link.setUser(userRepo.findOneByUserName(jwtHelper.getSubject(authorizationToken)));
		link.setUriKeywords(UriExtractor.extractKeywords(link.getUri()));
		repo.save(link);
		return new Response("Added browser history");
	}

	@RequestMapping(path = "/browserlink")
	public @ResponseBody DeferredResult<Iterable<BrowserLink>> getBrowserLink() throws Exception {

//		 return repo.findAll(new PageRequest(0, 50));
		
		final DeferredResult<Iterable<BrowserLink>> deferredResult = new DeferredResult<Iterable<BrowserLink>>();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(deferredResult.isSetOrExpired()){
					throw new RuntimeException();
				} else {
					deferredResult.setResult(repo.findAll(new PageRequest(0, 50)));
				}
				
			}
		}, 0);
		
		return deferredResult;
	}


}
