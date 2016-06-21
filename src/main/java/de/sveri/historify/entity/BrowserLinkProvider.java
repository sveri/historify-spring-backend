package de.sveri.historify.entity;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrowserLinkProvider {

	@Autowired
	BrowserLinkRepository browserLinkRepo;

	@Autowired
	UserRepository userRepo;

	public List<BrowserLink> allByUser(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());
		return browserLinkRepo.findAllByUserOrderByVisitedAtDesc(user);
	}

	public List<BrowserLink> lastTenByUser(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());
		return browserLinkRepo.findTop10ByUserOrderByVisitedAtDesc(user);
	}

}
