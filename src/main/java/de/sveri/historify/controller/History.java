package de.sveri.historify.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLinkRepository;
import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;

@Controller
public class History {

	@Autowired
	BrowserLinkRepository browserLinkRepo;

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/history")
	public ModelAndView index(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());
		ModelAndView mav = new ModelAndView("history/index");
		mav.addObject("histories", browserLinkRepo.findAllByUserOrderByVisitedAtDesc(user));
		return mav;
	}

}
