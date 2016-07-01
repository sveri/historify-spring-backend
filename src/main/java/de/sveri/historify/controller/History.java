package de.sveri.historify.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.service.Pageable;
import de.sveri.historify.service.PaginationHandler;
import de.sveri.historify.service.PaginationHandler.PageItems;

@Controller
public class History {

	// Provider<BrowserLink> browserLinkProvider;
	//
	// PaginationHandler paginationHandler;

	@Autowired
	BrowserLinkPaginationRepository browserLinkProvider;

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/history")
	public ModelAndView index(Principal principal,
			@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = "20") int pageSize,
			@RequestParam(name = "search-for", required = false, defaultValue = "") String searchFor) {
		ModelAndView mav = new ModelAndView("history/index");

		Pageable<BrowserLink> paginationHandler = PaginationHandler.fromSearchCriteria(browserLinkProvider, userRepo,
				principal, pageNumber, pageSize, searchFor);

		mav.addObject("histories", paginationHandler.getElements());
		mav.addObject("firstPage", paginationHandler.isFirstPage());
		mav.addObject("lastPage", paginationHandler.isLastPage());
		mav.addObject("url", "/history");
		mav.addObject("size", pageSize);
		mav.addObject("hasPreviousPage", !paginationHandler.isFirstPage());
		mav.addObject("hasNextPage", !paginationHandler.isLastPage());
		mav.addObject("totalPages", paginationHandler.getTotalPages());
		mav.addObject("number", pageNumber);

		List<String> pageSizes = Arrays.asList("10", "20", "50", "100", "1000");
		mav.addObject("pageSizes", pageSizes);

		List<PageItems> pageItems = paginationHandler.getPageItems();
		mav.addObject("pageItems", pageItems);

		mav.addObject("searchFor", searchFor);

		return mav;
	}

}
