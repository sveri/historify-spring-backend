package de.sveri.historify.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class JwtHelper {

	@Value("${app.jwt.secretKey}")
	@Getter
	private String secretKey;
	
	public JwtHelper(){}

	public String getSubject(String token) {
		return io.jsonwebtoken.Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().getSubject();
	}

	public String getRole(String token) {
		return io.jsonwebtoken.Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().get("role",
				String.class);
	}
}
