package site.metacoding.red.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final UsersService usersService;
	private final HttpSession session;

	// http://localhost:8000/users/usernameSameCheck
	@GetMapping("/users/usernameSameCheck")
	public @ResponseBody CMRespDto<Boolean> usernameSameCheck(String username) {
		boolean isSame = usersService.유저네임중복확인(username);
		return new CMRespDto<Boolean>(1, "sucess", isSame);
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm"; // 파일을 리턴
	}

	@GetMapping("/loginForm")
	public String loginForm(Model model, HttpServletRequest request) { // 쿠키 배워보기
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("username")) {
				model.addAttribute(cookie.getName(), cookie.getValue());
			}
			System.out.println("===========");
			System.out.println(cookie.getName());
			System.out.println(cookie.getValue());
			System.out.println("===========");
		}
		return "users/loginForm"; // 파일을 리턴
	}

	@PostMapping("/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody JoinDto joinDto) { // join할 때는 joinDto를 받아야 한다
		usersService.회원가입(joinDto);
		return new CMRespDto<>(1, "회원가입성공", null);
	}

	@PostMapping("/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		System.out.println("=========");
		System.out.println(loginDto.isRemember());
		System.out.println("=========");

		if (loginDto.isRemember()) {
			Cookie cookie = new Cookie("username", loginDto.getUsername());
			cookie.setMaxAge(60*60*24);
			response.addCookie(cookie);
			//response.setHeader("Set-Cookie", "username=" + loginDto.getUsername());
		} else {
			Cookie cookie= new Cookie("username",null); //체크하면 생성, 체크안 하면 삭제
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		Users principal = usersService.로그인(loginDto);

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인실패", null);
		}

		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "로그인성공", null);
	}

	// 인증 필요 -> 세션이 있는지 확인하고 들여보내줘야 함
	@GetMapping("/s/users/{id}")
	public String updateForm(@PathVariable Integer id, Model model) { // 회원정보 수정 페이지 -> 모델에 데이터를 담고 가야 한다
		Users usersPS = usersService.회원정보보기(id);
		model.addAttribute("users", usersPS);
		return "users/updateForm";
	}
	
	// 인증 필요
	@PutMapping("/s/users/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		Users usersPS = usersService.회원수정(id, updateDto);
		session.setAttribute("principal", usersPS); // 세션 동기화
		return new CMRespDto<>(1, "회원수정 성공", null);
	}
	// 인증 필요
	@DeleteMapping("/s/users/{id}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		session.invalidate();
		return new CMRespDto<>(1, "회원탈퇴성공", null);
	}// @ResponseBody을 안붙이면 파일명인 줄 안다

	@GetMapping("/logout") // 로그아웃
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}
}

//뭐 뭔저 해야 할지 모를 때 클라이언트가 들어왔을 때 뭘 할지 생각하면 됨.