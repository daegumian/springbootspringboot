package com.simple.basic;

import javax.activation.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.simple.basic.command.BuilderVO;
import com.simple.basic.command.BuilderVO.Builder;
import com.simple.basic.command.BuilderVO2;


@SpringBootTest
public class JDBCTest { //스프링 부트 데스트클래스


	//빌더 패턴의 확인
	@Test
	public void testCode01() {

		//			Builder b = BuilderVO.builder();
		//			b = b.age(10);
		//			b = b.name("박동훈");
		//			BuilderVO vo = b.build();

		//vo는 세터가 없기 때문에 세터로 값을 바꿀 수 없다. => 고로 초기에 설정한 그 값이 불변한다.
		BuilderVO vo = BuilderVO.builder().age(20).name("개쩌네?").build();
		System.out.println(vo);
		
		
		BuilderVO2 vo2 = BuilderVO2.builder().name("박지욱").age(150).build();
		System.out.println(vo2.toString());
	
	}



}
