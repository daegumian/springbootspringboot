package com.daegumian.herewego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HerewegoController {
	
	@GetMapping("/login")
	public String herewegoLogin() {
		
		
		return "login";
	}

}
