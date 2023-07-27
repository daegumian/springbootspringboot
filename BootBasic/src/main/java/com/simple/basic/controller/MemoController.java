package com.simple.basic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.basic.command.MemoVO;
import com.simple.basic.memo.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {

	//2번
	@Autowired
	@Qualifier("memoService")
	private MemoService memoService;

	//메모 목록
	@GetMapping("/memoList")
	public String memoList(Model model) {
		ArrayList<MemoVO> list = memoService.getList();

		model.addAttribute("list", list);
		System.out.println(list);

		return "memo/memoList";
	}

	//글쓰기 화면
	@GetMapping("/memoWrite")
	public String memoWrite() {

		return "memo/memoWrite";
	}

	//메모기능

	@PostMapping("/memoForm")
	public String memoForm(@Valid @ModelAttribute("vo") MemoVO vo, Errors errors, Model model) {
		
		
		if(errors.hasErrors() == true) { //에러가 있을 때
			System.out.println("왜 안 넘어가노1");
			System.out.println(vo.getMemo());
			List<FieldError> list = errors.getFieldErrors();
			
			for(FieldError err : list) {
				if(err.isBindingFailure()) {
					model.addAttribute("memo_"+err.getField(), "잘못된 값입니다.");
				}else {
					model.addAttribute("memo_"+err.getField(), err.getDefaultMessage());
				}
			}
			return "memo/memoWrite";

		}else { //에러가 없을 때
			System.out.println("왜 안 넘어가노2");
			
			memoService.MemoRegist(vo);
			ArrayList<MemoVO> list = memoService.getList();
			model.addAttribute("list", list);
			
			return "redirect:/memo/memoList";

		}





	}





}
