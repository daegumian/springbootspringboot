package com.coding404.jwt.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.coding404.jwt.security.config.JWTService;
import com.coding404.jwt.user.MyUserDetails;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	//attemptAuthentication를 오버라이등 하면
	//클라이언트 에서 post방식으로 /login로 들어오면 실행됨.
	
	private AuthenticationManager authenticationManager;
	
	//생성될 때 AuthenticationManager를 생성자 매개변수로 받는다.
	public CustomLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("===============attemptAuthentication 실행됨 ===============");
		
		//로그인처리 - 로그인 시도하는 사람은 반드시 form타입으로 전송해라! (JSON형식도 받을 수 있다 + JSON맵핑처리)
		//1.request로 username과 userpassword를 받음 //그런데 제이슨 방식은request get파라미터로 받지 못한다.
		//
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username);
		System.out.println(password);
		
		//스프링 시큐리티가 로그인에 사용하는 토큰객체
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username, password);
		
		//어쏜티케이션매니저의 authenticate가 실행되면 MyUserDetailsService의 loadUserByUsername가 실행됩니다.
		//여기 리턴이 있다는 것은 로그인 성공이라는 의미
		Authentication authentication = authenticationManager.authenticate(token);
		
		System.out.println("===========로그인성공=========>");
		System.out.println("내가 실행되었다는, 로그인 성공 : " + authentication);
		
		
		return authentication; //여기서 반환되는 return은 시큐리티 세션이 가져가서 new 시큐리티세션(new 인증객체 (new 유저객체))) 형태로 저장시킴
							   	  //리턴 (여기서 리턴된 값은 시큐리티세션( new Authentication(new MyUserDetails()) )
	}

	//로그인 성공 후에 실행되는 메서드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
//		super.successfulAuthentication(request, response, chain, authResult); //자동으로 부모님 기능이 되는데 지워야된다.
		
		System.out.println("==============로그인 성공 헨들러==============>");
		//토큰 발행해서 헤더에 담고 클라이언트로 전달
		
		System.out.println("로그인 이후 인증객체 : " + authResult); //principal 안에 들어있다.
		
		MyUserDetails principal = (MyUserDetails)authResult.getPrincipal();
		
		String token = JWTService.createToken(principal.getUsername());
		
		response.setContentType("text/html; charset=UTF-8;");
		response.setHeader("Authorization", "Bearer" + token);//Bearer라는 의미란? 찾아보기 가장 기본이 되는 토큰 방식
		response.getWriter().println("로그인성공(아이디)" + principal.getUsername());
		response.getWriter().println("로그인성공(아이디)" + principal.getRole());//게터 메서드가 있으면 다 뽑을 수 있다.
		
		
	}

	//로그인 실패한 후에 실행되는 메서드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
//		super.unsuccessfulAuthentication(request, response, failed); //자동으로 부모님 기능이 되는데 지워야된다.
		
		System.out.println("==============로그인 실패 헨들러==============>");
		
		response.setContentType("text/html; charset=UTF-8;");
//		response.getWriter().println("응답할내용");
		response.sendError(1000, "아이디 비밀번호를 확인하세요");
		
	}
	
	
	
	
}
