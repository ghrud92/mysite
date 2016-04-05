package com.estsoft.mysite.web.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = new Cookie("testCookie", "Hello World");
		cookie.setMaxAge(60 * 60 * 24);
		cookie.setPath("/mysite/");
		
		response.addCookie(cookie);
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

}
