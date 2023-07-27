package com.simple.basic.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {
	
	@NotBlank
	@Pattern(message="8글자 이상입니다.", regexp="[a-zA-Z0-9]{8,}")
	private String id;
	
	@NotBlank
	@Pattern(message="비밀번호는 다음 조건을 만족해야 합니다:\r\n"
			+ "\r\n"
			+ "최소 8자 이상\r\n"
			+ "최소 하나 이상의 대문자 포함\r\n"
			+ "최소 하나 이상의 소문자 포함\r\n"
			+ "최소 하나 이상의 숫자 포함\r\n"
			+ "최소 하나 이상의 특수문자(!@#$%^&*()) 포함\r\n"
			+ "공백은 사용할 수 없습니다.", 
			regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])(?!.*\\s).{8,}$")
	private String pw;

}
