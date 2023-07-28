package com.coding404.myweb.topic.service;

import java.util.ArrayList;

import com.coding404.myweb.command.TopicVO;

public interface TopicService {

	public ArrayList<TopicVO> getList(); //조회
	public int topicReg(TopicVO vo); //등록
	
}
