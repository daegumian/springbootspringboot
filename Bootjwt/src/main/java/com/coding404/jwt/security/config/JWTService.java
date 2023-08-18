package com.coding404.jwt.security.config;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


public class JWTService {
	
	private static String secretKey = "daegumian91";
	//원래는 절대 쉬운 이름으로 지으면 안된다. 절대 알아볼 수 없는 어려운 형식의 키로 만들어 줘야한다.
	
	
	//토큰 생성
	public static String createToken(String username) {
		
		//알고리즘 생성
		Algorithm alg = Algorithm.HMAC256(secretKey);
		
		//만료시간 설정 현재시간 + (원하는 시간) ex) 3600 = 1분
		long expire = System.currentTimeMillis() + 3600000; //1시간 뒤
		
		//토큰생성
		Builder builder = JWT.create().withSubject(username) //주제
					.withIssuedAt(new Date()) //발행일
					.withExpiresAt(new Date(expire)) //만료시간
					.withIssuer("daegumian91") //발행자
					.withClaim("admin", "공개 클레임 박동훈~"); // +@ 공개 클레임을 추가
		
		return builder.sign(alg);//객체생성
	}
	
	//토큰의 유효성확인
	public static boolean validateToken(String token) throws JWTVerificationException {
		
		Algorithm alg = Algorithm.HMAC256(secretKey);
		
		JWTVerifier verifier =  JWT.require(alg).build(); //token을 검증할 객체
		
		verifier.verify(token);//토큰검사 - 만료기간 or 토큰위조가 발생하면 throws 처리됨.
		
		return true;//토큰검사 후, 검증성공시 true, 검증실패시 false
		
		
	}
	
	
	

}
