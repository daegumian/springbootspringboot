package com.simple.basic.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.basic.command.MemberVO;
import com.simple.basic.command.ValidVO;

@Controller
@RequestMapping("/valid")
public class ValidController {
	
	//화면 출력
	@GetMapping("/valid")
	public String view() {
		
		return "valid/valid";
	}
	
	//폼요청
	@PostMapping("/viewForm") //적용하는 방법
	public String viewForm(@Valid @ModelAttribute("vo") ValidVO vo, Errors errors, Model model) {
		
		if(errors.hasErrors() == true) { //에러가 있다면 true, 없으면 false
			//에러 있을 시
			//1.유효성 검사에 실패한 에러 확인
			List<FieldError> list = errors.getFieldErrors();
			
			//2. 반복처리
			for(FieldError err : list) {
				//System.out.println(err);
				//System.out.println(err.getField());//무엇이 에러인지
				//System.out.println(err.getDefaultMessage());//메세지 출력
				//System.out.println(err.isBindingFailure());
				//유효성검사에 의해서 err라면 false 아니라면 true 반환
				
				if(err.isBindingFailure()) {
					model.addAttribute("valid_" + err.getField(), "잘못된 값 입력입니다.");
					
				}else {
					
					model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
				}
				
				
			}
			
			return "valid/valid";
		} //err end
		
		
		
		return "valid/result";
	}
	
	//////////////////////////////////////////////////
	//quiz01 화면 출력
	
	/*1번*/
	@GetMapping("/quiz01")
	public String quiz01() {
		
		return "valid/quiz01";
	}
	
	@PostMapping("/quizForm")
	public String quizForm(@Valid @ModelAttribute("vo") MemberVO vo, Errors errors, Model model) {
		
		if(errors.hasErrors() == true) {
			List<FieldError> list = errors.getFieldErrors();
			for(FieldError err : list) {
				if(err.isBindingFailure()) {
					model.addAttribute("Quiz_"+err.getField(), "잘못된 입력방식입니다");
				}else {
					model.addAttribute("Quiz_"+err.getField(), err.getDefaultMessage());
				}
			}
			return "valid/quiz01"; //원본화면으로 다시 돌아감
		}
		return "valid/quiz01_result";
	}
	
	

	
}
