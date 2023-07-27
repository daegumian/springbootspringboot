package com.simple.basic.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ValidVO {
	
	/*
	 * 아래로갈수록 규정이 빡세짐
	 * @Notnull = null 허용x , (String, Integer, Double등등)
	 * @NotEmpty = null, 공백 허용x (String 적용)	 
	 * @NotBlank = null, 공백, 화이트스페이스 허용x (String 적용)
	 * (각각의 멤버변수 위에 선언한다!)
	 * 
	 * @Pattern = 정규표현식에 일치하지 않으면 error
	 */
	
	//기본타입 => 래퍼타입 작성
	//ex)왜 int보다 Integer가 좋냐? Integer은 기본값으로 null을 주기 때문
	
	@NotEmpty(message = "빈칸 노노!")
	private String name;
	@NotNull(message = "열정페이노노! 숫자많이오오!")
	private Integer salary; 
	
	@NotBlank(message = "공백이 있네?")
	@Email(message = "골뱅이 붙여라~")//이메일은 공백이 통과
	private String email;
	
	@Pattern(message = "000-000-000형식 이어야 합니다", regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
	private String phone;
	
	

}
