package com.simple.basic.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter,setter,toString
@AllArgsConstructor //모든 생성자
@NoArgsConstructor //기본 생성자
@Builder //빌더패턴 자동생성
public class BuilderVO2 {

	private String name;
	private int age;
	
}
