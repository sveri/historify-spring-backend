package de.sveri.historify.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLinkProvider;

@Controller
public class History {

	@Autowired
	BrowserLinkProvider browserLinkProvider;

	@RequestMapping("/history")
	public ModelAndView index(Principal principal,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		ModelAndView mav = new ModelAndView("history/index");
		mav.addObject("histories", browserLinkProvider.findFromPageWithSizeByUser(principal, page, size));
		mav.addObject("firstPage", isFirstPage(page, size));
		mav.addObject("lastPage", isLastPage(page, size, browserLinkProvider.totalElements()));
		mav.addObject("url", "/history");
		mav.addObject("size", size);
		mav.addObject("hasPreviousPage", !isFirstPage(page, size));
		mav.addObject("hasNextPage", !isLastPage(page, size, browserLinkProvider.totalElements()));
		mav.addObject("totalPages", browserLinkProvider.totalElements() / size);
		mav.addObject("number", page);

		return mav;
	}

	private static boolean isFirstPage(long page, long size) {
		// return page <= size;
		return page == 0;
	}

	private static boolean isLastPage(long page, long size, long totalEntries) {
		// return page > totalPages - size;
		return page == totalEntries / size;
	}

}
