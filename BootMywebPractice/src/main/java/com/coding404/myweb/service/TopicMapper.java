package com.coding404.myweb.service;

import org.apache.ibatis.annotations.Mapper;

import com.coding404.myweb.command.TopicVO;

@Mapper
public interface TopicMapper {
	
	public int topicReg(TopicVO vo); //등록

}
