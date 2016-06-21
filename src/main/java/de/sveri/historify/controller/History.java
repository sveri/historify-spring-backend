package de.sveri.historify.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLinkProvider;

@Controller
public class History {

	@Autowired
	BrowserLinkProvider browserLinkProvider;

	@RequestMapping("/history")
	public ModelAndView index(Principal principal) {
		ModelAndView mav = new ModelAndView("history/index");
		mav.addObject("histories", browserLinkProvider.allByUser(principal));
		return mav;
	}

}
