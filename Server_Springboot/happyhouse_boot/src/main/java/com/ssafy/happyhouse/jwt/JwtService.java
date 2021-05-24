package com.ssafy.happyhouse.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.happyhouse.model.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service("jwtService")
public class JwtService {

	private static final String SALT = "luvookSecret";

	@Autowired
    private ObjectMapper objectMapper;
	
	public <T> String create(String key, T data, String subject) {
		Date expireTime = new Date();
		expireTime.setTime(expireTime.getTime() + (1000 * 60 * 60 * 24 * 7)); // 일주일

		String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
				.setSubject(subject).setExpiration(expireTime).claim(key, data)
				.signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		return jwt;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
//			if(log.isInfoEnabled()){
//				e.printStackTrace();
//			}else{
//				log.error("Making JWT Key Error ::: {}", e.getMessage());
//			}
		}

		return key;
	}

	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			return true;

		} catch (Exception e) {

//			if(log.isInfoEnabled()){
//				e.printStackTrace();
//			}else{
//				log.error(e.getMessage());
//			}
			throw new UnauthorizedException();

			/*
			 * 개발환경!!! return false;
			 */

		}
	}

	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String jwt = request.getParameter("access_token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
//			if(log.isInfoEnabled()){
//				e.printStackTrace();
//			}else{
//				log.error(e.getMessage());
//			}
			throw new UnauthorizedException();

			/*
			 * 개발환경 Map<String,Object> testMap = new HashMap<>(); testMap.put("memberId",
			 * 2); return testMap;
			 */
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>) claims.getBody().get(key);
		return value;
	}

	public UserDto getUser(String jwt) {
        Jws<Claims> claims = null;
        try {
			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
        
        return objectMapper.convertValue(claims.getBody().get("user"), UserDto.class);
	}
}
