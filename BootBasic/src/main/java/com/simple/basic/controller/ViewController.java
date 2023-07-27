package com.simple.basic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simple.basic.command.SimpleVO;

@Controller
@RequestMapping("/view")
public class ViewController {

	
//	@RequestMapping(value = "/ex01", method = RequestMethod.GET) 
	@GetMapping("/ex01")//위의 코드와 동일, post방식을 거부함
	public String ex01() {
		
		return "view/ex01"; //템플릿폴더 밑이 기준
	}
	
	@GetMapping("/ex02")//위의 코드와 동일, post방식을 거부함
	public String ex02(Model model) {
	
		SimpleVO vo = SimpleVO.builder()
	            .sno(1)
	            .first("하정우")
	            .regdate( LocalDateTime.now())
	            .build();
		
		
		ArrayList<SimpleVO> list = new ArrayList<>();
		for(int i = 1; i <=10; i++) {
			SimpleVO simpleVO = SimpleVO.builder()
					  .sno(i)
					  .first("하정우")
					  .regdate(LocalDateTime.now())
					  .build();
			
			list.add(simpleVO);
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("list", list);
		return "view/ex02"; //템플릿폴더 밑이 기준
	}
	
	@GetMapping("/ex03")
	   public String ex03(Model model) {
	      SimpleVO vo = SimpleVO.builder()
	            .sno(1)
	            .first("하정우")
	            .regdate( LocalDateTime.now())
	            .build();
	      
	      ArrayList<SimpleVO> list = new ArrayList<>();
	      for(int i =1; i<=10; i++) {
	         SimpleVO Simplevo = SimpleVO.builder()
	               .sno(i)
	               .first("하정우" + i)
	               .regdate( LocalDateTime.now())
	               .build();
	         
	         list.add(Simplevo);
	      }
	      model.addAttribute("vo",vo);
	      model.addAttribute("list",list);
	      return "view/ex03";
	   }

	
	//키를 받는 첫번째 방법 쿼리스트링
	@GetMapping("/result")
	public String ex03_result(@RequestParam("sno") int sno,
							  @RequestParam("first") String first) {
		System.out.println("넘어온 값 : " + sno +", " + first);
		
		return "view/ex03_result";
	}
	
	//키를 받는 두번째 방법-URL파람
	   
	   @GetMapping("/result02/{a}/{b}")
	   public String ex03_result02(@PathVariable("a") String name,
	                         	   @PathVariable("b") int age) {
	      
	      System.out.println("넘어온값:"+name+","+age);
	      
	      return "view/ex03_result";
	   }
	   
	   
	   //ex04 - 타임리프 인클루드
	   @GetMapping("/ex04")
	   public String ex04() {
		   
		   return "view/ex04";
	   }
	   
	   
	   //ex05 - 타임리프 템플릿 형식으로 결합하기
	   @GetMapping("/ex05")
	   public String ex05(Model model) {
		   SimpleVO vo = SimpleVO.builder()
					   .sno(1)
					   .first("하")
					   .last("정우")
					   .regdate(LocalDateTime.now())
					   .build();
	model.addAttribute("vo", vo);
		   
		   
		   return "view/ex05";
	   }
	   
	   
	   //quiz
	   @GetMapping("/quiz")
	   public String quiz(Model model) {
		   SimpleVO vo = SimpleVO.builder()
		            .sno(1)
		            .first("하")
	   				.last("정우")
		            .regdate( LocalDateTime.now())
		            .build();
		      
		      ArrayList<SimpleVO> list = new ArrayList<>();
		      for(int i =1; i<=10; i++) {
		         SimpleVO Simplevo = SimpleVO.builder()
		               .sno(i)
		               .first("하")
		               .last("정우")
		               .regdate( LocalDateTime.now())
		               .build();
		         
		         list.add(Simplevo);
		      }
		      model.addAttribute("vo",vo);
		      model.addAttribute("list",list);
		   
		   return "view/quiz";
	   }
	   
	   //a링크 요청
	   //quiz_result 
	   @GetMapping("/quiz_result01")
	   public String quiz_result(@ModelAttribute("sno") int sno,
			   					 @ModelAttribute("name") String name,
			   					 @ModelAttribute("regdate" ) String regdate ) { //폼에서 날아오는 모든 값은 String타입이다.
		   	//@ModelAttribute는 값을 받는과 동시에 화면으로 정보 넘어감
		    System.out.println(sno);
		   	System.out.println(name);
		   	System.out.println(regdate);
		   
		   return "view/quiz_result01";
	   }
	   
	 
	   
	   
	   
	   
	   
	
	
}
