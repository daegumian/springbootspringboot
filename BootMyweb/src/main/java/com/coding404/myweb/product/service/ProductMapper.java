package com.coding404.myweb.product.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper
public interface ProductMapper {
	
	//마이바티스는 매개변수를 하나만 가질 수 있다.
	//2개 이상의 매개변수가 들어가면 명칭을 붙여줘야한다.
	//ex) @Param("writer") String writer ...
	public int productRegist(ProductVO vo);
//	public ArrayList<ProductVO> getList(String writer); //조회
	public ArrayList<ProductVO> getList(@Param("writer") String writer, 
										@Param("cri") Criteria cri);//조회2
	public int getTotal(@Param("writer") String writer,
						@Param("cri") Criteria cri);//젠체게시글
	
	public ProductVO getDetail(int prod_id);
	public int productUpdate(ProductVO vo);//수정
	public void productDelete(int prod_id); //삭제
}
