package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final UsersService usersService;
	private final HttpSession session;		
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";	// 파일을 리턴
	}

	@GetMapping("/loginForm")
	public String loginForm() {	// 쿠키 배워보기
		return "users/joinForm";	// 파일을 리턴
	}
	
	@PostMapping("join")
	public String join(JoinDto joinDto) {	// join할 때는 joinDto를 받아야 한다
		usersService.회원가입(joinDto);
		return "redirect:/loginForm";
	}
	
	@PostMapping("login")
	public @ResponseBody String login(LoginDto loginDto) {	// join할 때는 joinDto를 받아야 한다
		Users principal = usersService.로그인(loginDto);
		if (principal != null) {
			return Script.back("아이디 혹은 비밀번호가 틀렸습니다");
		}
		session.setAttribute("principal", principal);
		return Script.href("/");
	}
	
	@GetMapping("/users/{id}")
	public String updateForm(@PathVariable Integer Id, Model model) { //회원정보 수정 페이지	-> 모델에 데이터를 담고 가야 한다
		Users usersPS = usersService.회원정보보기(Id);
		model.addAttribute("users", usersPS);
		return "users/updateForm";
	}
	
	@PutMapping("/users/{id}")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		usersService.회원수정(id, updateDto);
		return "redirect:/users/"+id;
	}
	
	@DeleteMapping("/users/{id}")
	public @ResponseBody String delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		return Script.href("/loginForm", "회원탈퇴가 완료되었습니다");	//@ResponseBody을 안붙이면 파일명인 줄 안다
	}
	
	@GetMapping("/logout")	// 로그아웃
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}
}

//뭐 뭔저 해야 할지 모를 때 클라이언트가 들어왔을 때 뭘 할지 생각하면 됨.