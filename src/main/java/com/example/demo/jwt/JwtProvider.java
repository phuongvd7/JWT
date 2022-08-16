package com.example.demo.jwt;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.security.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;

import org.slf4j.Logger;

@Component // tao component de no quet // k phai tao bean nua
public class JwtProvider {
	// ghi log
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	private String jwtSecret = "123456";
	private int jwtExpiration = 86400;
	
	// login se goi ham nay de tao token
//	public String createToken(Authentication authentication) {
//		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal(); // get user hien tai ra he thong
//
//		return Jwts.builder().setSubject(userPrinciple.getUsername())
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(new Date().getTime() + jwtExpiration*1000))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//	}
	public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
	
	// check xem token co hop le hay khong
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message:{}" , e);
		} catch (MalformedJwtException e) {
			logger.error("Invalid format Token -> Message:{}", e);
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT Token -> Message:{}", e);
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT Token -> Message:{}", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims String is empty -> Message:{}", e);
		}
		return false;
	}
	public String getUsernameFromToken(String token) {
		String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return userName;
	}
}
