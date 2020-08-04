package com.busticketapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
	
	public void preHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		System.out.println("Pre handler executed before controller");
	}
	
	public void postHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Post handler executed before controller");
	}
}
