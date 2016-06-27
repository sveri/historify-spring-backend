package de.sveri.historify.entity;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class BrowserLinkProvider implements Provider<BrowserLink> {

	@Autowired
	BrowserLinkRepository browserLinkRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	BrowserLinkPagination browserLinkPagination;

	@Override
	public List<BrowserLink> allByUser(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());
		return browserLinkRepo.findAllByUserOrderByVisitedAtDesc(user);
	}

	public List<BrowserLink> lastTenByUser(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());
		return browserLinkRepo.findTop10ByUserOrderByVisitedAtDesc(user);
	}

	@Override
	public List<BrowserLink> findFromPageWithSizeByUserAndUri(Principal principal, int page, int size, String uri) {
		User user = userRepo.findOneByUserName(principal.getName());
		return browserLinkPagination.findByUserAndUriLikeIgnoreCase(user, uri, new PageRequest(page, size));
	}

	@Override
	public long totalElements() {
		return browserLinkPagination.count();
	}

}
