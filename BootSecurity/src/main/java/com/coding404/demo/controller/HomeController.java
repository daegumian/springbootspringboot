package com.coding404.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding404.demo.command.UserVO;
import com.coding404.demo.user.MyUserDetails;
import com.coding404.demo.user.UserMapper;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//여기서 다 처리
	@Autowired
	private UserMapper userMapper;
	
	
	@GetMapping("/hello")
	public String hello(Authentication auth) {
		
		//1st - 컨트롤러 매개변수에 Authentication객체를 선언한다
//		if(auth != null) {
//			MyUserDetails details = (MyUserDetails)auth.getPrincipal();
//		
//			System.out.println(details.getUsername());
//			System.out.println(details.getPassword());
//			System.out.println(details.getRole());
//		}
		
		//2st - 시큐리티세션을 직접 사용
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
		 //System.out.println(authentication); //이렇게하면 디폴트값의 객체가 생성된다
		 //System.out.println((MyUserDetails)authentication); //받는 값이 MyUserDetails타입이 아니라서 캐스팅하는 과정에서 에러가 난다
		 
		 if ( authentication.getPrincipal() instanceof MyUserDetails ) {
			 MyUserDetails details = (MyUserDetails)authentication.getPrincipal();
			 System.out.println(details);
		 }
		
		
		return "hello";
	}

	//회원가입
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	//회원가입 요청
	@PostMapping("/joinForm")
	public String joinForm(UserVO vo) {
		
		//(패스워드를 암호화시키는 작업)
		String pw = bCryptPasswordEncoder.encode(vo.getPassword());
		vo.setPassword(pw);
		
		//서비스영역 생략 (원래는 만들어줘야함)
		//회원가입작업
		userMapper.join(vo);
		
		return "redirect:/login"; //로그인 페이지로
	}
	
	
	
	//커스터마이징 로그인페이지
	@GetMapping("/login") //err이 필수는 아니다. 이걸 무조건 선언해야한다.
	public String login(@RequestParam( value = "err", required = false) String err,
						Model model) {
		
		if(err != null) {
			model.addAttribute("msg", "아이디 비밀번호를 확인하세요");
		}
		
		return "login";
	}
	
	@GetMapping("/all")
	public String all() {
		return "all";
	}
	
	//관리자
	@GetMapping("/admin/mypage")
	public @ResponseBody String mypage() {
		return "REST API admin 마이페이지";
	}
	
	//유저
	@GetMapping("/user/mypage")
	public @ResponseBody String usermypage() {
		return "REST API user 마이페이지";
	}
	
	@GetMapping("/deny")
	public @ResponseBody String deny() {
		
		return "페이지에 접근할 권한이 없습니다";
	}
	
	
	//ADMIN 권한이 있어야 접근이 가능
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/test")
	public @ResponseBody String test() {
		
		return "여기는 preAuthorize로 처리";
	}

	
	
}
