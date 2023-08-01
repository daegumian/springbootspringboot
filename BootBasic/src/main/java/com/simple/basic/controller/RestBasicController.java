package com.simple.basic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.basic.command.SimpleVO;
 
@RestController //@ResponseBody + @Controller
public class RestBasicController {
	
	@GetMapping("/basic")
	public String basic() {
		return "<h3>hello world<h3>";
	}
	
	//데이터를 보내는 방법 => @ResponseBody = 자바의 객체를 JSON형식으로 자동 변환
	@GetMapping("/getObject")
	public SimpleVO getObject() {
		
		SimpleVO vo = new SimpleVO(1,"박","동훈", LocalDateTime.now());
		return vo;
	}
	
	//해쉬맵으로 데이터를 보내보자
	@GetMapping("getMap")
	public Map<String, Object> getMap(){
		Map<String , Object> map = new HashMap<>();
		map.put("name", "박동훈");
		
		return map;
		
	}
	
	//리스트로 데이터 보내기
	@GetMapping("/getList")
	public List<SimpleVO> getList(){
		
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(1,"박","동훈", LocalDateTime.now()));
		list.add(new SimpleVO(2,"박","지욱", LocalDateTime.now()));
		return list;
	}
	
	//데이터 받는방법////////////////////////////////////////////////////
	//get => 주소에 쿼리스트링 or 쿼리파라미터 이용해서 넘겨줌
	//post => 폼형식 or json을 이용해서 body에 담아서 넘김
	
	@GetMapping("getData")
	public SimpleVO getData(SimpleVO vo) {
		
		System.out.println(vo.toString());
		
		return new SimpleVO(2, "신", "지은", LocalDateTime.now());
	}
	
	@GetMapping("getData2")
	public SimpleVO getData2(@RequestParam("sno") int sno,
							 @RequestParam("first") String first){
		System.out.println(sno);
		System.out.println(first);
		
		return new SimpleVO(2, "박", "소려", LocalDateTime.now());
	}
	
	@GetMapping("/getData3/{sno}/{first}")
	public SimpleVO getData3(@PathVariable("sno") int sno,
							 @PathVariable("first") String first){
		
		System.out.println(sno);
		System.out.println(first);
		
		return new SimpleVO(2, "박", "소여", LocalDateTime.now());
	}
	
	//////////////////////////////////////////////////////////
	//post방식의 데이터 받기
	
	@PostMapping("/getForm")
	public SimpleVO getForm(SimpleVO vo) {
		
		System.out.println(vo.toString());
		return new SimpleVO(2, "박", "주료", LocalDateTime.now());
	}
	
	//보내는 입장에서 JSON형식의 데이터를 써줘야함 + @RequestBody를 해주면 된다.
	//{"sno": "1","first":"박","last":"히애"}
	@PostMapping("/getJSON")
	public SimpleVO getJSON(@RequestBody SimpleVO vo) {
		
		System.out.println(vo.toString());
		
		return new SimpleVO(3, "박", "가몽", LocalDateTime.now());
	}
	
	//{"sno": "1","first":"박","last":"리애"}
	@PostMapping("/getJSON2")
	public SimpleVO getJSON2(@RequestBody Map<String , Object> map) {
		
		System.out.println(map.toString());
		
		return new SimpleVO(3, "박", "이몽", LocalDateTime.now());
	}
	
	///////////////////////////////////////////////////////////////////
	//consumer = 반드시 이 타입으로 보내라! 명시
	//producer = 내가 이 타입으로 줄게! 명시(default = application/json), xml을 사용하려면 xml데이터바인딩 라이브러리 필요
	//보내고 받는 형식을 명시해줄 수 있다. 안지키면 다 거절난다
	
	//보내는 타입은 text로 줄게, 너는 json형식으로 보내라
	@RequestMapping(value="/getResult", produces="text/plain", consumes = "application/json")
	public String getResult(@RequestBody String str) {
		
		System.out.println(str);
		
		return "<h3>박동훈은 짱이다<h3>";
	}
	
	
	//응답문서 직접작성하기 = ResponseEntity<보낼데이터타입>
	@PostMapping("/createResponse")
	public ResponseEntity<SimpleVO> createResponse() {
		SimpleVO vo = new SimpleVO(1,"박","굴비", LocalDateTime.now());
		
		//1st
		//ResponseEntity<SimpleVO> result = new ResponseEntity<>(vo, HttpStatus.OK);
		
		//2nd
		//헤더에 대한 내용정의
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "JSON WEB TOKEN~");
		header.add("Content-Type", "application/json");
		header.add("Access-Control-Allow-Origin", "*");
		
		ResponseEntity<SimpleVO> result = new ResponseEntity<>(vo, header, HttpStatus.OK); //데이터, 헤더, http상태코드
		
		return result;
	}
	
	////////////////////////////////////////////////////////////////////////
	/*
	 * 클라이언트 fetch 
	 * 요청주소 : /api/v1/getData 
	 * 메서드 : get 
	 * 클라이언트에서 보낼데이터는 : num, name
	 * 서버에서 보낼 데이터는 : SimpleVO
	 * 받을 수 있는 restAPI를 생성
	 */
	
	
	
	@GetMapping("/api/v1/getData2")
	public SimpleVO getData(@RequestParam("num") int sno,
			 				@RequestParam("name") String first) {
		
		SimpleVO vo = new SimpleVO(1,"박","동훈", LocalDateTime.now());
		
		return vo;
	}
	
	
	  //쌤방식
	  
	  @GetMapping("/api/v1/getData3/{num2}/{name2}") 
	  public ResponseEntity<SimpleVO> getFetch(@PathVariable("num2") int num2,
	  
	  @PathVariable("name2") String name2 ){
	  
	  System.out.println(num2); System.out.println(name2);
	  
	  return new ResponseEntity<>(new SimpleVO(1,"박","동훈",
	  LocalDateTime.now()),HttpStatus.OK); }
	 
	
	
	
	
	/*
	 * 클라이언트 fetch: 요청을 보내는 함수
	 * 요청주소 : /api/v1/getInfo
	 * 메서드:post
	 * 클라이언트에서 보낼데이터 : num, name -> 웹에서 보내는 요청
	 * 서버에서 보낼데이터는 : 리스트<SimpleVO> -> 자바에서 처리해서 주는
	 * 받을 수 있는 restAPI를 생성
	 * 반드시 ResponseEntity로 응답 // 클라이언트 요청, 서버에서 응답
	 */
	
	@CrossOrigin("http://localhost:3000")
//	@CrossOrigin("*")
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<List<SimpleVO>> getInfo() {
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(1,"박","동훈",LocalDateTime.now()));
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "JSON WEB TOKEN~");
		header.add("Content-Type", "application/json");
		
		ResponseEntity<List<SimpleVO>> result = new ResponseEntity<List<SimpleVO>>(list, header, HttpStatus.OK);
		
		return result;
	}
	
	
	  //쌤 방식
	  
	  @PostMapping("/api/v1/getInfo2") public ResponseEntity<List<SimpleVO>>
	  getFetch2(@RequestBody Map<String, Object> map){
	  
	  System.out.println(map.toString()); List<SimpleVO> list = new ArrayList<>();
	  list.add(new SimpleVO(1,"박","동훈",LocalDateTime.now()));
	  
	  return new ResponseEntity<>(list, HttpStatus.OK);
	  
	  }
	 
	

}
