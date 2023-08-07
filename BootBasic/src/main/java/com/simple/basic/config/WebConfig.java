package com.simple.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.simple.basic.controller.HomeController;
import com.simple.basic.util.intercepter.UserAuthHandler;

@Configuration //이거 설정파일이야
//수많은 설정을 할 수 있는 인터페이스로 구성 "WebMvcConfigurer"
public class WebConfig implements WebMvcConfigurer{ //자바 빈설정을 하기위해서 상속
	
	
	//약간 서비스imple늘낌이네 아니면 mapper?
	//인터셉터로 사용할 클래스를 bean
	@Bean
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler();
	}

	//스프링설정에 인터셉터를 추가, 오버라이드에서 찾아서 오버라이딩
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(userAuthHandler())
				.addPathPatterns("/user/**")//user로 시작하는 모든 요청 검사
				.excludePathPatterns("/user/login") //user/login제외
				.excludePathPatterns("/user/loginForm"); //로그인메서드 제외
				//.addPathPatterns("/memo/**")
				//.addPathPatterns("/user/mypage") //개개별로 경로 지정 가능
				//.addPathPatterns("/user/modify");
	
		//인터셉터는 여러개가 있응ㄹ 수도 있는데, 추가하면 됨.
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
//	//IOC확인
//	@Autowired
//	ApplicationContext applicationContext; //applicationContext은 스프링은 기본적으로 가지고 있음
//	
//	//value어노테이션 사용 - application.properties파일의 값을 참조하는데 사용
//	//데이터베이스의 값을 읽어올 때 "({값or변수})"으로 표현함
//	@Value("${spring.datasource.url}")
//	private String url;
//	
//	
//	
//	@Bean // 이 메서드를 빈으로 생성 - return 객체를 반환하는 모형을 만들면, 빈으로 등록
//	public void test() {
//		
//		TestBean test = applicationContext.getBean(TestBean.class);
//		System.out.println("TestBean이 빈으로 등록됨 : " + test);
//		
//		//@Controller를 통해 빈 자동 설정된 것을 확인 - 성공함
//		HomeController con = applicationContext.getBean(HomeController.class);
//		System.out.println("homeController가 빈으로 등록됨 : " + con);
//		
//		int count = applicationContext.getBeanDefinitionCount();
//		System.out.println("IOC 안에 빈의 개수 : " + count);
//		
//		System.out.println("application프로퍼티 키값 : " + url);
//		
//	}
	
	//빈 생성의 2가지 전략
	//1. @Controller, @service 등을 이용해서 빈으로 등록
	//2. 스프링 설정파일에 빈으로 등록
	//return 객체를 반환하는 모형을 만들면, 빈으로 등록
//	@Bean
//	public TestBean test2() {
//		TestBean b = new TestBean();
//		
//		return b;
//		
//	}
//	
	
}
