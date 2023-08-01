package com.simple.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "hello"; //뷰 리볼버의 합성경로
		
	}
	
	@GetMapping("/abc")
	@ResponseBody
	public String res() {
		return "<h3>abc<h3>";
	}
	
	
}
