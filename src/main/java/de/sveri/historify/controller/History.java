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
import de.sveri.historify.entity.Provider;
import de.sveri.historify.service.PaginationHandler;
import de.sveri.historify.service.PaginationHandler.PageItems;

@Controller
public class History {

	Provider<BrowserLink> browserLinkProvider;

	PaginationHandler paginationHandler;

	@Autowired
	public History(Provider<BrowserLink> browserLinkProvider) {
		paginationHandler = new PaginationHandler(browserLinkProvider);
		this.browserLinkProvider = browserLinkProvider;
	}

	@RequestMapping("/history")
	public ModelAndView index(Principal principal,
			@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "search-for", required = false, defaultValue = "") String searchFor) {
		ModelAndView mav = new ModelAndView("history/index");

		mav.addObject("histories", browserLinkProvider.findFromPageWithSizeByUserAndUri(principal, pageNumber - 1, pageSize, searchFor));
		mav.addObject("firstPage", PaginationHandler.isFirstPage(pageNumber));
		mav.addObject("lastPage",
				PaginationHandler.isLastPage(pageNumber, pageSize, browserLinkProvider.totalElements()));
		mav.addObject("url", "/history");
		mav.addObject("size", pageSize);
		mav.addObject("hasPreviousPage", !PaginationHandler.isFirstPage(pageNumber));
		mav.addObject("hasNextPage",
				!PaginationHandler.isLastPage(pageNumber, pageSize, browserLinkProvider.totalElements()));
		mav.addObject("totalPages", paginationHandler.getTotalPagesForPageSize(pageSize));
		mav.addObject("number", pageNumber);

		List<String> pageSizes = Arrays.asList("10", "20", "50", "100", "1000");
		mav.addObject("pageSizes", pageSizes);

		List<PageItems> pageItems = paginationHandler.getPageItems(pageSize, pageNumber);
		mav.addObject("pageItems", pageItems);

		return mav;
	}

}
