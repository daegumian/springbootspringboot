package com.simple.basic.memo.service;

import java.util.ArrayList;

import com.simple.basic.command.MemoVO;

public interface MemoService {
	
	//4번
	public void MemoRegist(MemoVO vo);
	public ArrayList<MemoVO> getList();
	
}
