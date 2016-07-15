package de.sveri.historify.controller.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.sveri.historify.controller.exception.UnprocessableEntityException;
import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.helper.JwtHelper;
import de.sveri.historify.service.UriExtractor;

@RestController
public class HistoryApi {

	public static final String UNPROCESSABLE_TAG_ID = "2000";

	public static final String API_BROWSERLINK = "/api/browserlink";

	public static final String API_ADD_TAGS = "/api/addtags";

	@Autowired
	BrowserLinkPaginationRepository repo;

	@Autowired
	JwtHelper jwtHelper;

	@Autowired
	UserRepository userRepo;

	@RequestMapping(path = API_BROWSERLINK, method = RequestMethod.POST)
	public @ResponseBody Response saveBrowserLink(@RequestHeader(value = "Authorization") String authorizationToken,
			@RequestBody BrowserLink link) throws Exception {
		link.setUser(userRepo.findOneByUserName(jwtHelper.getSubject(authorizationToken)));
		link.setUriKeywords(UriExtractor.extractKeywords(link.getUri()));
		repo.save(link);
		return new Response("Added browser history");
	}

	// @RequestMapping(path = "/browserlink")
	// public @ResponseBody DeferredResult<Iterable<BrowserLink>>
	// getBrowserLink() throws Exception {
	// final DeferredResult<Iterable<BrowserLink>> deferredResult = new
	// DeferredResult<Iterable<BrowserLink>>();
	// timer.schedule(new TimerTask() {
	//
	// @Override
	// public void run() {
	// if (deferredResult.isSetOrExpired()) {
	// throw new RuntimeException();
	// } else {
	// deferredResult.setResult(repo.findAll(new PageRequest(0, 5)));
	// }
	//
	// }
	// }, 0);
	//
	// return deferredResult;
	// }

	@RequestMapping(path = API_ADD_TAGS, method = RequestMethod.POST)
	public @ResponseBody Response addTags(@RequestBody BrowserLink link) throws UnprocessableEntityException {
		if (StringUtils.isEmpty(link.getUri()) || StringUtils.isEmpty(link.getTags())) {
			throw new UnprocessableEntityException();
		}

		return new Response("Added tags");
	}

}
