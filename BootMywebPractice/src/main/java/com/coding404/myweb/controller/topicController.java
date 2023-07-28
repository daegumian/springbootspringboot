package com.coding404.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coding404.myweb.topic.service.TopicService;

@Controller
@RequestMapping("/topic")
public class topicController {
	
	@Autowired
	@Qualifier("topicService")
	TopicService topicService;
	
	
	//리스트 모든 화면
	@RequestMapping("/topicListAll")
	public String topicList() {
		
		return "topic/topicListAll";
	}
	
	//리스트 나의 화면
	@RequestMapping("/topicListMe")
	public String topicListMe() {
		
		return "topic/topicListMe";
	}
	
	//게시글 등록
	@RequestMapping("/topicReg")
	public String topicReg() {
		
		return "topic/topicReg";
	}

	//게시글 수정
	@RequestMapping("/topicModify")
	public String topicModify() {
		
		return "topic/topicModify";
	}
	
	//게시글 디테일
	@RequestMapping("/topicDetail")
	public String topicDetail() {
		
		return "topic/topicDetail";
	}
	
	
	
	

}
