package com.simple.basic.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoVO {
	
	private String mno;
	
	@NotNull
	@Pattern(message="메모는 5글자 이상입니다.", regexp = "^.{5,}$")
	private String memo;
	
	@NotNull
	@Pattern(message="전화번호는 -없이 11자리 입니다.", regexp = "^\\d{11}$")
	private String phone;
	
	@NotNull
	@Pattern(message="비밀번호는 4자리수 입니다.", regexp="^\\d{4}$")
	private String pw;
	
	@NotNull
	private String secret;
	

}
