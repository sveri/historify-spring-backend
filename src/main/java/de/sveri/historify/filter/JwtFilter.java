package de.sveri.historify.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import de.sveri.historify.helper.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class JwtFilter extends GenericFilterBean {

	@Autowired
	private JwtHelper jwtHelper;

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

//		final String authHeader = request.getHeader(JwtHelper.AUTHORIZATION);
//		if (authHeader == null || !authHeader.startsWith(JwtHelper.BEARER)) {
//			log.error("Missing or invalid Authorization header.");
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
//			return;
//		}
//
//		final String token = authHeader.substring(7);
//
//		try {
//			final Claims claims = Jwts.parser().setSigningKey(jwtHelper.getSecretKey()).parseClaimsJws(token).getBody();
//			request.setAttribute("claims", claims);
//		} catch (final Exception e) {
//			e.printStackTrace();
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Jwt token.");
//			return;
//		}

		chain.doFilter(req, res);
	}

}
