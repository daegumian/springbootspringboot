package com.coding404.myweb.product.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper //이거 매퍼야
public interface ProductMapper {
	
	//마이바티스는 매개변수를 하나만 가질 수 있다.
	//2개 이상의 매개변수가 들어가면 명칭을 붙여줘야한다.
	//ex) @Param("writer") String writer ...
	public int productRegist(ProductVO vo); //상품등록
	public void productFileRegist(ProductUploadVO vo); //파일등록(서비스와 매개변수나 메서드 명이 달라질 수 있다)
	
	
	//public ArrayList<ProductVO> getList(String writer); //조회
	public ArrayList<ProductVO> getList(@Param("writer") String writer, 
										@Param("cri") Criteria cri);//조회2
	public int getTotal(@Param("writer") String writer,
						@Param("cri") Criteria cri);//젠체게시글
	
	public ProductVO getDetail(int prod_id);
	public int productUpdate(ProductVO vo);//수정
	public void productDelete(int prod_id); //삭제
	
	//카테고리 처리
	public ArrayList<CategoryVO> getCategory(); //처음가져올 때
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo); //2단 3단 가져올때
	
	//이미지 불러오기
	public ArrayList<ProductUploadVO> getAjaxImg(int prod_id);
}
