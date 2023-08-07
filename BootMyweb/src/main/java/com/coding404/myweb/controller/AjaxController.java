package com.coding404.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
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
	
	/////////////////////////////////
	//이미지 데이터 화면에 응답하기
	
	/*
	 * 1.화면에서 넘어오는 getAjaxImg요청을 받는 REST API를 생성해보기
	 * 1-1. 서비스, 매퍼 함수를 생성해보기
	 * 2.getAjaxImg()로 업로드 테이블에서 조회한 결과를 화면으로 전
	 * 
	 * */
	
	
	@PostMapping("/getAjaxImg")
	public ResponseEntity<ArrayList<ProductUploadVO>> getAjaxImg(@RequestBody ProductUploadVO vo){
		System.out.println(vo.toString());
		
		ArrayList<ProductUploadVO> list = productService.getAjaxImg(vo.getProd_id());
		System.out.println(list.toString());
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	//이미지 src값 응답하기
		@GetMapping("/display")
		public ResponseEntity<byte[]> display(@RequestParam("filename") String filename,
											  @RequestParam("filepath") String filepath,
											  @RequestParam("uuid") String uuid){
			
			String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
			System.out.println(path);
			
			byte[] data = null;
			
			try {
			  data = FileCopyUtils.copyToByteArray(new File(path));//바이트 배열 타입으로 반환
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	
	
	
	
}
