package site.metacoding.red.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import site.metacoding.red.domain.users.Users;

public class LoginIntercepter implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("실행됐어어어엉🥕");
		HttpSession session = request.getSession();
		Users principal = (Users) session.getAttribute("principal");
		if(principal == null) {
			response.sendRedirect("/loginForm");
			return false;
		}
	
		return true;
	}
}