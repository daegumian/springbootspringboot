package com.simple.basic.memo.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.simple.basic.command.MemoVO;

@Mapper
public interface MemoMapper {

	//5번
	@Autowired
	public void MemoRegist(MemoVO vo);
	public ArrayList<MemoVO> getList();
	
}
