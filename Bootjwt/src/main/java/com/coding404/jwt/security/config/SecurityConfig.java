package com.coding404.jwt.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.coding404.jwt.security.filter.CustomLoginFilter;
import com.coding404.jwt.security.filter.FilterOne;
import com.coding404.jwt.security.filter.FilterTwo;
import com.coding404.jwt.security.filter.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//비밀번호 암호화객체
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

		//기본 로그인 방식, 세션, 베이직인증, csrf토큰 전부 사용하지 X
		http.csrf().disable();//서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.

		http.formLogin().disable(); //form 기반 로그인을 사용하지 x
		http.httpBasic().disable(); //Authorization : 아이디 형식으로 basic 인증을 사용하지 x
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여서 인증 예정

		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); //모든 요청은 전부 허용

		//1.크로스오리진 필터 생성
		http.cors(Customizer.withDefaults());
		
		//2. 필터체이닝 연습 //4.할려고 연습으로 한거임
		//http.addFilter(new FilterOne()); // 시큐리티타입의 필터를 등록할 때
		//http.addFilterBefore(new FilterOne(), UsernamePasswordAuthenticationFilter.class );
		//http.addFilterBefore(new FilterTwo(), FilterOne.class);
		//http.addFilterAfter(new FilterTwo(), FilterOne.class);
		
		//3.로그인 시도에 AuthenticationManager가 필요하다
		//++UserDetailService객체 and PasswordEncoder가 반드시 필요
		
		AuthenticationManager authenticationManager = 
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
		System.out.println(authenticationManager);
		
		//4.로그인 필터를 등록 //부메랑으로 post로 username,password,role 값 제이슨 식으로 넘겨서 send해보기
		
		//http.addFilterBefore(new CustomLoginFilter(), UsernamePasswordAuthenticationFilter.class);//많은 예제에서 이렇게 하지만 우린 아래에 방식으로
//		http.addFilter(new CustomLoginFilter( authenticationManager));
		
		//5. jwt검증필터를 등록
//		http.addFilterBefore( new JwtAuthrizationFilter(authenticationManager), BasicAuthenticationFilter.class); //토큰검사 필터 - 이렇게 해도됩니다.
//		http.addFilter( new JwtAuthorizationFilter(authenticationManager));
		
		//4~5번은 모든 요청이니 주석처리
		//6. 요청별로 필터 실행시키기
		// /login요청에만 CustomLoginFilter가 실행됨
		http.requestMatchers()
			.antMatchers("/login")
			.and()
			.addFilter( new CustomLoginFilter(authenticationManager));
		
		http.requestMatchers()
		 	.antMatchers("/api/v1/**")
		 	.antMatchers("/api/v2/**")
		 	.and()
		 	.addFilter( new JwtAuthorizationFilter(authenticationManager));
		 	
		return http.build();
	}
	
	
	
	//로그인 시도에 필요한 AuthenticationManager객체
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	//크로스오리진 필터

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		//configuration.setAllowedOrigins(Arrays.asList("http://example.com")); //부메랑에서 요청이 deny됨
		configuration.setAllowedOrigins(Arrays.asList("*")); //모든요청 주소를 허용 == CrossOrigin
		configuration.setAllowedMethods(Arrays.asList("*")); //모든요청 메서드를 허용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);//모든 요청에 대해서

		return source;

	}



}
