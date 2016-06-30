package de.sveri.historify.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;

@Controller
public class Home {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BrowserLinkPaginationRepository browserLinkProvider;

	@RequestMapping("/")
	public ModelAndView home(Principal principal) {
		User user = userRepo.findOneByUserName(principal.getName());

		ModelAndView mav = new ModelAndView("home/index");
		if (principal != null && principal.getName() != null) {
			mav.addObject("histories", browserLinkProvider.findByUserOrderByVisitedAtDesc(user, new PageRequest(0, 20)));
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
