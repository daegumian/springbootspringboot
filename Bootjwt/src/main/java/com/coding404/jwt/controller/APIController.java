package com.coding404.jwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.jwt.command.UserVO;
import com.coding404.jwt.security.config.JWTService;

@RestController //rest, jwt는 서버를 넘나든다
public class APIController {
	
	
	
//	//로그인기능 가정
	//2.할때 열어서 확인
	//4.할때 닫아서 확인
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody UserVO vo) {
//		
//		//로그인시도 --> 성공이라고 가정
//		System.out.println(vo.toString());
//		//토큰생성
//		String token = JWTService.createToken(vo.getMilitary_sn());
//		
//		
//		return new ResponseEntity<>(token, HttpStatus.OK);
//	}
//	
//	//사용자 정보 확인
//	@PostMapping("/api/v1/getInfo")
//	public ResponseEntity<String> getInfo(HttpServletRequest request){
//		
//		//헤더에 담긴 토큰
//		String token = request.getHeader("Authorization");
//		
//		//토큰의 무결성 검사
//		try {
//			boolean result = JWTService.validateToken(token);
//			System.out.println("토큰무결성 : " + result);
//			//사용자 정보~~~
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("토큰위조", HttpStatus.UNAUTHORIZED);
//		}
//		return new ResponseEntity<>("succuss getInfo~~", HttpStatus.OK);
//	}
	
/////////**JWT**////////////////////////////////////////////////////////////////////////////////
	
	//시큐리티 요청테스트
	//rest라서 responseBody가 그냥 붙어있는 상태다
	@GetMapping("/api/v1/hello")
	public String hello() {
		
		return"<h3>헬로<h3>";
	}
	
	//토큰기반으로한 사용자정보 반환가능
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<Object> getInfo(){
		
		System.out.println("토큰이 있으면 호출될 (데이터베이스 연결이 처리~) ");
		
		return new ResponseEntity<>("데이터", HttpStatus.OK);
	}
	
	//회원가입 -> 토큰이 필요하나?
	@PostMapping("/join")
	public ResponseEntity<Object> join(){
		
		return new ResponseEntity<>("가입성공",HttpStatus.OK);
	}
	
	
}
