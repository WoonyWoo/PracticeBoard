package com.ptc.rain.notice.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	
	public static final String SESSION_COOKIE_NAME = "mySessionId";
	private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
	
	public void createSession(Object value, HttpServletResponse res) {
		// 세션 생성
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, value);
		
		// 쿠키 생성 후 저장
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		res.addCookie(cookie);
	}
	
	public Object getSession(HttpServletRequest req) {
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie == null) {
			return null;
		}
				
		return sessionStore.get(cookie.getValue());
	}
	
	public void expire(HttpServletRequest req) {
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie != null) {
			sessionStore.remove(cookie.getValue());
		}
	}
	
	public Cookie findCookie(HttpServletRequest req, String cookieNm) {
		
		if(req.getCookies() == null) {
			return null;
		}
		
		return Arrays.stream(req.getCookies())
				.filter(c -> c.getName().equals(cookieNm))
				.findAny()
				.orElse(null);
		
	}

}
