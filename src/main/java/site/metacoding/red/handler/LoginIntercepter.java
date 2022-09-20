package site.metacoding.red.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.response.CMRespDto;

public class LoginIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕");
		System.out.println(request.getRequestURI());
		System.out.println("🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕");

		String uri = request.getRequestURI();

		HttpSession session = request.getSession();
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			if (uri.contains("api")) {
				System.out.println("🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕");
				System.out.println("API 가 주소에 있음");

				// response.setHeader("Content-Type", "application/json; charset=utf-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				CMRespDto<?> cmRespDto = new CMRespDto<>(-1, "인증이 필요합니다", null);
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(cmRespDto);
				out.println(json);
			} else {
				System.out.println("🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕");
				System.out.println("API 가 주소에 없음");
				response.sendRedirect("/loginForm");
			}
			return false;
		}
		return true;
	}
}