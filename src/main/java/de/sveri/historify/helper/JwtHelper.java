package de.sveri.historify.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class JwtHelper {

	@Value("${app.jwt.secretKey}")
	@Getter
	private String secretKey;
	
	public static final String BEARER = "Bearer "; 
	
	public static final String AUTHORIZATION = "Authorization";
	
	public JwtHelper(){}

	public String getSubject(final String token) {
		return io.jsonwebtoken.Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(getCleanedToken(token)).getBody().getSubject();
	}

	public String getRole(String token) {
		return io.jsonwebtoken.Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(getCleanedToken(token)).getBody().get("role",
				String.class);
	}
	
	private static String getCleanedToken(final String token){
		if(token.startsWith(BEARER)) return token.substring(7);
		return token;		
	}
}
