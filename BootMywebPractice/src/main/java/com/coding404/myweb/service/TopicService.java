package com.coding404.myweb.service;

import java.util.ArrayList;

import com.coding404.myweb.command.TopicVO;

public interface TopicService {
	
	public int topicReg(TopicVO vo); //등록
	public ArrayList<TopicVO> getList(); //조회

}
