package com.coding404.jwt.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.coding404.jwt.command.UserVO;

//이 객체는 화면에 전달이 되는데, 화면에서 사용할 값들은 반드시 getter로 생성을 해야한다.
public class MyUserDetails implements UserDetails{
	
	//멤버변수로 UserVO객체를 받는다.
	private UserVO userVO;
	
	public MyUserDetails(UserVO vo) {
		this.userVO = vo;
	}
	
	//화면에서 권한도 사용할 수 있게 해주고 싶다면 getter를 각각 만들어라~
	//+@ 원하는 거 다 만들어서 해~
	public String getRole() {
		return userVO.getRole();
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//UserVO가 가지고 있는 권한을 리스트에 담아서 반환시키면, 스프링 시큐리티가 참조해서 사용함.
		//Collection은 List의 최고 부모님
		List<GrantedAuthority> list = new ArrayList<>();
		
		list.add(new GrantedAuthority() {
			//유저의 Role이 GrantedAuthority에 들어가고 그게 list에 들어가서 반환을 한다.
			@Override
			public String getAuthority() {
				return userVO.getRole();
			}
		});
		
		return list;
	}

	@Override
	public String getPassword() {
		return userVO.getPassword();
	}

	@Override
	public String getUsername() {
		return userVO.getUsername();
	}

	//아래에 것들 전부 DB에 상태를 저장해주는 컬럼이 있으면 가져다가 쓸 수 있다.
	/*
	 * private UserVO userVO;
	
	public MyUserDetails(UserVO vo) {
		this.userVO = vo;
	}
	으로 인해 userVO에 그 정보들도 가져올 수 있기에..!
	 * 
	 * */
	@Override
	public boolean isAccountNonExpired() {
		return true; //계정이 만료되지 않았습니까? true - 네
	}
		
	@Override
	public boolean isAccountNonLocked() {
		return true; //계정이 락이 걸리지 않았습니까? true - 네
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; //비밀번호가 만료되지 않았습니까? true - 네
	}

	@Override
	public boolean isEnabled() {
		return true; //사용할 수 있는 계정입니까? true - 네
	}

}
