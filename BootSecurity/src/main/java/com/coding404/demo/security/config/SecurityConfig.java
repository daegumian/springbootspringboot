package com.coding404.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.coding404.demo.user.MyUserDetailService;
import com.coding404.demo.user.MyUserDetails;

@EnableGlobalMethodSecurity(prePostEnabled = true)//어노테이션으로 권한을 지정할 수 있게 함
@Configuration //설정파일(이라는 뜻)
@EnableWebSecurity //이 설정파일을 시큐리티 필터에 추가(무조건 붙여주는 거라고 생각)
public class SecurityConfig {
	
	//나를 기억해에서 사용할 UserDetailService
	@Autowired
	public MyUserDetailService myUserDetailService;
	
	
	//비밀번호 암호화객체 //주입이 왜 들어갈 수 있는가? 설정파일에 Bean등록해주기때문?
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		//csrf토큰 x (사용중지)
		http.csrf().disable();//기본적으로 빌더패턴으로 되어있다. .찍고 계속 설정을 추가할 수 있다.
		
		//권한 설정
		//http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); //모두다 들어 갈 수 있다.
		
		//모든 페이지에 대해서 거부
		//http.authorizeHttpRequests(authorize -> authorize.anyRequest().denyAll()); //모두다 들어 갈 수 없다.

		//인증이된 사람은 접근 가능
//		http.authorizeHttpRequests(authorize -> authorize.
//												antMatchers("/user/**").
//												authenticated()); //user로시작하는 모든 페이지에 대해서 인증이 필요함
		
		//user페이지에 대해서 권한이 필요
		//http.authorizeHttpRequests(authorize -> authorize.antMatchers("/user/**").hasRole("USER")); 
		
		//user페이지는 user권한이 필요, admin페이지는 admin 권한이 필요
//		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/user/**").hasRole("USER")
//														 .antMatchers("/admin/**").hasRole("ADMIN") ); 
		
		// all페이지는 인증만 되면됨, user페이지는 user권한, admin페이지는 admin권한, 나머지 모든 페이지는 접근이 가능하다
//		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/all").authenticated() //all페이지는 허가만 하면됨 ,
//														 .antMatchers("/user/**").hasRole("USER") //user페이지는 user의 권한이
//														 .antMatchers("/admin/**").hasRole("ADMIN") //admin은 admin의 권한이
//														 .anyRequest().permitAll());  // 나머지 모든 페이지는 모든 접근이 허용한다.
		
		//hasAnyRole은 (매개변수 중 하나만 충족해도 됨)
		//all페이지는 인증만 되면 됨, user페이지는 3개 중 1개의 권한을 가지면 접근 가능
		//권한 앞에서는 ROLE_ 자동으로 생략이된다
		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/all").authenticated() //all페이지는 허가만 하면됨
														 .antMatchers("/user/**").hasAnyRole("USER", "ADMIN","TESTER")//권한 앞에서는 ROLE_ 자동으로 생략이된다
														 .antMatchers("/admin/**").hasRole("ADMIN") //admin에만 롤을 줌
														 .anyRequest().permitAll());
												
		
		
		//시큐리티 설정파일을 만들면, 시큐리티가 제공하는 기본 로그인페이지가 보이지 않게된다.
		//시큐리티가 사용하는 기본로그인페이지를 사용함
		//권한 or 인증이 되지 않으면 기본으로 선언된 로그인페이지를 보여주게 된다.
		//http.formLogin(Customizer.withDefaults());//기본로그인페이지 사용
		
		//사용자가 제공하는 폼기반 로그인 기능을 사용할 수 있다.
		http.formLogin()
			.loginPage("/login")//로그인페이지
			.loginProcessingUrl("/loginForm") //로그인시도 요청경로 -> 로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결합니다.
			.defaultSuccessUrl("/all")//현재 all은 인증이 필요한 페이지
			.failureUrl("/login?err=true")//로그인실패시 이동할 url 키=값으로 날림
			.and() //and는 다시 처음부터 http상태를 쓸 수 있음 -> 계속 build패턴으로 추가해 나갈 수 있음
			.exceptionHandling().accessDeniedPage("/deny") //권한이 없을 때 이동할 리다이렉트경로
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/hello");// default 로그아웃 경로 / logout, / logout 주소를 직접 작성할 수 있고, 로그아웃 성공시 리다이렉트할 경로
		
		
		//나를기억해
		http.rememberMe()
			.key("daegumian") //토큰(쿠키)를 만들 비밀키 //필수
			.rememberMeParameter("remember-me")//화면에서 전달받는 checked name명 //필수
			.tokenValiditySeconds(60)//쿠키(토큰)의 유효시간 //필수
			.userDetailsService(myUserDetailService)//토큰이 있을 때 실행시킬 UserDetailService객체 //필수
			.authenticationSuccessHandler(customRememberMe());//나를기억해가 동작할 떄, 실행할 핸들러 객체를 넣는다.
		
		
		return http.build();//빌더패턴
		
	}
	
	
	//customeRememberMe
	@Bean
	public CustomRememberMe customRememberMe() {
		
		CustomRememberMe me = new CustomRememberMe("/all");//리멤버미 성공시 실행시킬 리다이렉트 주소
		return me;
	}
	
	

}
