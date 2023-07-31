package com.coding404.myweb.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

@Data //getter, setter, 
public class PageVO {
	
	private int start; //시작페이지네이션
	private int end; //끝페이지네이션
	private boolean prev; //이전버튼 활성화여부
	private boolean next; //다음버튼 활성화여부
	private int realEnd; //실제보여지는 끝번호
	
	private int total; //전체 게시글 수
	private int page; //cri에 있는 현재조회하는 페이지
	private int amount; //cri에 있는 데이터 개수
	private Criteria cri; //페이지 기준

	private int pnCount = 10; //페이지네이션 개수
	
	private List<Integer> pageList; //페이지네이션을 리스트로 저장
	
	//페이지네이션 클래스는 cri와 total을 매개변수로 받음
	public PageVO(Criteria cri, int total) {
		this.cri = cri;
		this.page = cri.getPage();
		this.amount = cri.getAmount();
		this.total = total;
		

		//end페이지 계산
		//현재 조회하는 page와 연관
		//page가 1을 가르킬 때, end = 10
		//page가 11을 가르킬 때, end = 20
		//Math.ceil => 인자로 주어진 숫자를 올림하여 가장 작은 정수 중 가장 큰 값을 반환합니다.
		//예를 들어, Math.ceil(4.5)를 호출하면 4.5가 올림되어 5.0이 반환됩니다. 또 다른 예시로, Math.ceil(7.2)는 7.2가 올림되어 8.0이 반환
		//this.end = (int)(Math.ceil(현재조회하는페이지 / 페이지네이션개수) * 페이지네이션개수;
		this.end = (int)(Math.ceil(this.page / (double)this.pnCount)) * this.pnCount;
		
		//start페이지 계싼
		//this.start = this.end - 페이지네이션개수 +1;
		this.start = this.end - this.pnCount + 1;
	
		//실제끝번호의 계산 -> 데이터 개수 10개 기준
		//총 게시글 수가 53 -> 마지막 번호는 6
		//총 게시글 수가 232개 -> 마지막 번호는 24
		//this.realend = (int)(Math.ceil(총 게시글 수 / 데이터개수));
		this.realEnd = (int)(Math.ceil(this.total / (double)this.amount));
	
		//end페이지 재결정
		//데이터가 25개 -> end = 10, realEnd = 3 (실제끝번호를 따라감)
		//데이터가 153개(현재조회하는 11번페이지 조회 -> end = 20, realEnd = 16(실제끝번호를 따라감)
		//데이터가 153개(3번페이지 조회 시) -> end = 10, realEnd = 16(페이지네이션을 따라감)
		
		if(this.end > this. realEnd) {
			this.end = this.realEnd;
		}
		
		//prev활성화 여부
		//start페이지 결정은 1, 11, 21, 31, 41........
		this.prev = this.start > 1;
		
		//next활성화 여부
		//end = 10, this.realEnd > this.end;
		this.next = this.realEnd > this.end;
		
		//타임리프 - 리스트에 페이지네이션을 담음
		
		this.pageList = IntStream.rangeClosed(this.start, this.end).boxed().collect(Collectors.toList());
		
		
		
		}
		
		
	
	
}
