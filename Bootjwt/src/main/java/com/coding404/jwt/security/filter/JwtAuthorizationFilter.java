package com.coding404.jwt.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.coding404.jwt.security.config.JWTService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	
	//생성자 //오쏘티케이션매니저를 기본으로 받음
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		System.out.println("=========JWT 필터 실행========>");
		
		//헤더의 담긴 토큰이 유효성을 확인하고, 인증된 토큰이면 우리서비스로 연결, 만료or위조 인 경우에는 error메시지 던지기
		String headers = request.getHeader("Authorization");
		//헤더가 없거나 bearer로 시작하지 않으면 토큰 안 보낸 것.
		if(headers == null || headers.startsWith("Bearer ") == false) {
			
			System.out.println("headers값 : " + headers);
			
			response.setContentType("text/html; charset=UTF-8;");
			response.sendError(403, "토큰없음");
		
			return; //함수 종료를 꼭 해줘야함!
		}
		
		//토큰의 유효성검사
		
		try {
			String token = headers.substring(7); //Bearer공백 이후에 진자 토큰
			
			boolean result = JWTService.validateToken(token); //토큰검증
			
			if(result) { //result == true면 정상토큰
				
				chain.doFilter(request, response);//컨트롤러로 연결됨 // 연결시킴
				
			}else { //토큰이 만료됨
				
				response.setContentType("text/html; charset=UTF-8;");
				response.sendError(403, "토큰이 만료됨");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//토큰이 위조
			response.setContentType("text/html; charset=UTF-8;");
			response.sendError(403, "토큰위조");
			
		}
		
	}
	
	
	
	
	
	
	//	super.doFilterInternal(request, response, chain); //삭제 해도 무관
}
