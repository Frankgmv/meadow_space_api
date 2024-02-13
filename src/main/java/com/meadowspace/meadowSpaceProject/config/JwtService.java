package com.meadowspace.meadowSpaceProject.config;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRECT_KEY = "62c66a7a5dd70c3146618063c344e531e6d4b59e379808443ce962b3abd63c5a";

	public String getUserName(String token) {
		return getClaim(token, Claims::getSubject);
    }

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		 long expirationMillis = System.currentTimeMillis() + (1000 * 60 * 60 * 24);
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(expirationMillis))
				 .signWith(Keys.hmacShaKeyFor(SECRECT_KEY.getBytes()), SignatureAlgorithm.HS256).compact();
				
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRECT_KEY.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private SecretKey getSigningKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRECT_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
	private List<String> invalidTokens = new ArrayList<>();

	public void invalidateToken(String token) {
	    invalidTokens.add(token);
	}

	public boolean isTokenInvalid(String token) {
	    return invalidTokens.contains(token);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
	    final String username = getUserName(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenInvalid(token));
	}
}

