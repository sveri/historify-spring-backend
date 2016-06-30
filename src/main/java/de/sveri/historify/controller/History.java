package de.sveri.historify.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.service.PaginationHandler;
import de.sveri.historify.service.PaginationHandler.PageItems;

@Controller
public class History {

	// Provider<BrowserLink> browserLinkProvider;
	//
	// PaginationHandler paginationHandler;

	@Autowired
	BrowserLinkPaginationRepository browserLinkProvider;
	// public History(Provider<BrowserLink> browserLinkProvider) {
	// paginationHandler = new PaginationHandler(browserLinkProvider);
	// this.browserLinkProvider = browserLinkProvider;
	// }

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/history")
	public ModelAndView index(Principal principal,
			@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = "20") int pageSize,
			@RequestParam(name = "search-for", required = false, defaultValue = "") String searchFor) {
		ModelAndView mav = new ModelAndView("history/index");

		User user = userRepo.findOneByUserName(principal.getName());

		PaginationHandler paginationHandler;

		String searchCleaned = cleanSearchForParam(searchFor);
		if (StringUtils.isEmpty(searchCleaned)) {
			paginationHandler = new PaginationHandler(
					browserLinkProvider.findByUserOrderByVisitedAtDesc(user, new PageRequest(pageNumber - 1, pageSize)),
					browserLinkProvider.count());
		} else {
			PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
			List<BrowserLink> elements = browserLinkProvider.findByUserAndSearchable(user.getId(), searchCleaned,
					pageRequest.getPageSize(), pageRequest.getOffset());
			paginationHandler = new PaginationHandler(elements, elements.size());
		}

		mav.addObject("histories", paginationHandler.getElements());
		mav.addObject("firstPage", PaginationHandler.isFirstPage(pageNumber));
		mav.addObject("lastPage", paginationHandler.isLastPage(pageNumber, pageSize));
		mav.addObject("url", "/history");
		mav.addObject("size", pageSize);
		mav.addObject("hasPreviousPage", !PaginationHandler.isFirstPage(pageNumber));
		mav.addObject("hasNextPage", !paginationHandler.isLastPage(pageNumber, pageSize));
		mav.addObject("totalPages", paginationHandler.getTotalPagesForPageSize(pageSize));
		mav.addObject("number", pageNumber);

		List<String> pageSizes = Arrays.asList("10", "20", "50", "100", "1000");
		mav.addObject("pageSizes", pageSizes);

		List<PageItems> pageItems = paginationHandler.getPageItems(pageSize, pageNumber);
		mav.addObject("pageItems", pageItems);

		mav.addObject("searchFor", searchFor);

		return mav;
	}

	private String cleanSearchForParam(String searchFor) {
		return searchFor.replace("|", "").trim().replace(" ", "&");
	}

}
