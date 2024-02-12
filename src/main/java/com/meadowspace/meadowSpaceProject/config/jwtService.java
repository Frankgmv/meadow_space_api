package com.meadowspace.meadowSpaceProject.config;

import java.awt.RenderingHints.Key;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {

	private static final String SECRECT_KEY = "a32ea0bfb00dfcbe307eb57701a1dfeb";

	public String getUserName(String token) {
		return getClaim(token, Claims::getSubject(token));
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);

		return claimsResolver.apply(claims);
	}

	private Claims getAllClaims(String token) {
		// Revisar este metodo parser() - parserBulder()		
		return Jwts.parser()
				.setSigningKey(getSingInKey())
				.build().parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSingInKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRECT_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
}
