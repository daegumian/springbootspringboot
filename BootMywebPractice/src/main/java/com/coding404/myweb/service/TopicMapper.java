package com.coding404.myweb.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.coding404.myweb.command.TopicVO;

@Mapper
public interface TopicMapper {
	
	public int topicReg(TopicVO vo); //등록
	public ArrayList<TopicVO> getList(); //조회
	
}
