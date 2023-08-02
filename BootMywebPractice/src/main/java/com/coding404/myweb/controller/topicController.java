package com.coding404.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coding404.myweb.command.TopicVO;
import com.coding404.myweb.service.TopicService;

@Controller
@RequestMapping("/topic")
public class TopicController {
	
	@Autowired
	@Qualifier("topicService")
	private TopicService topicService;
	
	//전체 목록 화면
	@RequestMapping("/topicListAll")
	public String topicListAll() {
		
		
		return "topic/topicListAll";
	}
	
	//등록 요청
	@PostMapping("/topicRegForm")
	public String topicRegForm(TopicVO vo) {
		
		
		int result = topicService.topicReg(vo);
		System.out.println(vo.getClass());
		System.out.println(result);
		System.out.println(vo.toString());
		
		
		return "topic/topicListAll";
	}
	
	//등록 화면
	@RequestMapping("/topicReg")
	public String topicReg() {
		
		return "topic/topicReg";
	}

}
