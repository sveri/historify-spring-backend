package de.sveri.historify.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLinkProvider;

@Controller
public class Home {

	@Autowired
	BrowserLinkProvider browserLinkProvider;

	@RequestMapping("/")
	public ModelAndView home(Principal principal) {
		ModelAndView mav = new ModelAndView("home/index");
		if (principal != null && principal.getName() != null) {
			mav.addObject("histories", browserLinkProvider.lastTenByUser(principal));
		}
		return mav;
	}

	@RequestMapping("/about")
	public String about() {
		return "home/about";
	}

	@RequestMapping("/dataprivacypolicy")
	public String dpp() {
		return "home/dataprivacypolicy";
	}

}
