package com.coding404.jwt.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {

	private String username; //military_sn 로 바꿔보자
	private String password;
	private String Role; //grade 로 바꿔보자
	
}
