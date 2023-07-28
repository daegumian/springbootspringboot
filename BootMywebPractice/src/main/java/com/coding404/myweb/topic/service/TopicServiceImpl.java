package com.coding404.myweb.topic.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding404.myweb.command.TopicVO;

@Service("/topicService")
public class TopicServiceImpl implements TopicService{

	@Autowired
	private TopicMapper topicMapper;

	@Override
	public ArrayList<TopicVO> getList() {
		
		return topicMapper.getList();
	}

	@Override
	public int topicReg(TopicVO vo) {
		return topicMapper.topicReg(vo);
	}
	
	
}
