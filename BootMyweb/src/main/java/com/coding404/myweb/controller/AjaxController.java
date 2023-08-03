package com.coding404.myweb.controller;

import java.util.ArrayList;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.product.service.ProductService;

//카테고리 컨트롤러

@RestController
public class AjaxController {
	
	//VO만들고, 컨트롤러 만들고, 
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getCategory")
	public ResponseEntity<ArrayList<CategoryVO>> getCategory(){
		
		ArrayList<CategoryVO> list = productService.getCategory();
		//위의 코드를 아래처럼 흔히 한번에 쓴다.
		//return new ResponseEntity<>(productService.getCategory(), HttpStatus.OK);
		
		return new ResponseEntity<>(list, HttpStatus.OK);//하지만 가독성을 위해 이렇게 고고
	}
	
	@GetMapping("/getCategoryChild/{a}/{b}/{c}")
	public ResponseEntity<ArrayList<CategoryVO>> getCategoryChild(@PathVariable("a") String group_id,
																  @PathVariable("b") int category_lv,
																  @PathVariable("c") int category_detail_lv){
		//get방식으로 넘긴 값 넘어오는지 확인
		//System.out.println(group_id);
		//System.out.println(category_lv);
		//System.out.println(category_detail_lv);
		
		//builder를 써야하는 이유? 게터세터로 만들어서 DB로 보낸다?
		CategoryVO vo = CategoryVO.builder()
								  .group_id(group_id)
								  .category_lv(category_lv)
								  .category_detail_lv(category_detail_lv)
								  .build();
		
		ArrayList<CategoryVO> list = productService.getCategoryChild(vo);
		
		return new ResponseEntity<ArrayList<CategoryVO>>(list, HttpStatus.OK);
	}
	
	
}
