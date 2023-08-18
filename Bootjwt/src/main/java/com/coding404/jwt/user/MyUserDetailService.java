package com.coding404.jwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coding404.jwt.command.UserVO;


@Service //서비스 빈으로 선언해야 빈으로 등록됨
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserMapper userMapper;
	
	
	//loginProcessURL이 호출될 때 loadUserByUsername함수를 자동으로 연결.
	//화면에서는 기본적으로 username이라는 파라미터로 꼭 지정해야한다. 약속된 이름임. (하지만, 바꿀수도 있긴함 :.usernameParameter(name값) )
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("===========loadUserByUsername==============");
		System.out.println(username);
		//로그인을 직접 날려줘야한다.
		//로그인 처리 - password는 시큐리티가 알아서 처리해줌
		UserVO vo = userMapper.login(username);
		
		System.out.println(vo);
		
		//vo가 null이 아니라는 것은 회원정보가 있다는 뜻!
		if(vo != null) {
			//스프링 시큐리티가 이 값을 받아가서 password를 확인한 후에, 정상유저라고 판별이되면
			//시큐리티 세션 안에 (new Authentication(new MyUserDetails))) 형태로 저장을 시킨다
			return new MyUserDetails(vo);
			//시큐리티 설정파일에 defaultSuccessUrl을 추가
			
		}
		
		return null;//vo에 정보가 없다면, 즉 회원정보가 없다면 null을 반환하고 그대로 끝
	}

	
}
