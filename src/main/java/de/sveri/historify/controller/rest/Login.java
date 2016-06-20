package de.sveri.historify.controller.rest;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;
import de.sveri.historify.helper.JwtHelper;
import de.sveri.historify.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

@RestController
public class Login {

	@Autowired
	@Getter
	private JwtHelper jwtHelper;

	@Autowired
	private UserRepository userRep;

	@RequestMapping(value = "/apilogin", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody final UserLogin login) throws ServletException {

		User user = userRep.findOneByUserNameOrEmail(login.getName(), login.getName());

		if (user == null || !UserService.matchesPassword(login.getPassword(), user.getPassword())) {
			throw new ServletException("Invalid login");
		}

		return new LoginResponse(Jwts.builder().setSubject(login.getName()).claim("roles", user.getRole())
				.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, jwtHelper.getSecretKey())
				.setExpiration(new Date(System.currentTimeMillis() + 12960000)).compact());
	}

	@SuppressWarnings("unused")
	private static class LoginResponse {
		public String token;

		public LoginResponse(final String token) {
			this.token = token;
		}
	}

}
