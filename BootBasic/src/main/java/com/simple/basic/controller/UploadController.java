package com.simple.basic.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.simple.basic.command.ProductVO;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	//폴더 생성 함수 -> 날짜별로 폴더를 생성해서 그곳에 저장
	public String makeFolder() {
		
		//날짜 생성
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd") );
		
		File file = new File(uploadPath + "/" + path);
		
		if(file.exists() == false) { //존재하면 true, 존재하지 않으면 false
			file.mkdirs();
		}
		
		return path;//날짜 폴더명 반환
		
		
	}
	
	@RequestMapping("/upload")
	public String upload() {
		
		return "upload/upload";
	}
	
	//파일데이터는 MultipartFile객체로 받는다.
	@PostMapping("/upload_ok")
	public String upload_ok(@RequestParam("file") MultipartFile file) {
		
		//파일 이름을 받는다.
		String originname = file.getOriginalFilename();
		
		//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 substring()으로 잘라서 다시저장
		String filename = originname.substring(originname.lastIndexOf("\\") + 1 ); 
		
		//파일사이즈
		long size = file.getSize();
		
		//동일한 파일을 재업로드시 기존파일을 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
		String uuid = UUID.randomUUID().toString();
		
		//날짜별로 폴더생성
		String filepath = makeFolder();
		
		//세이브 파일 경로
		String savepath = uploadPath + "/"+ filepath + "/" + uuid + "_" + filename;

		//System.out.println(filename);
		//System.out.println(size);
		//데이터베이스에 추후에 저장
		System.out.println("실제파일명 : " + filename);
		System.out.println("파일명 앞에 붙는 난수번호 : " + uuid);
		System.out.println("날짜폴더경로 : " + filepath);
		System.out.println("세이프할 경로:" + savepath);
		
		try {
			File saveFile = new File(savepath);
			file.transferTo(saveFile);
			
		} catch (Exception e) {
			System.out.println("파일업로드중 에러 발생");
			e.printStackTrace();
		}
		
		
		return "upload/upload_ok";
	}

	//복수태그를 사용한 다중파일 업로드 - List<MultipartFile>
	@PostMapping("/upload_ok2")
	public String upload_ok2(@RequestParam("file") List<MultipartFile> list) {
		
		//빈 file객체는 제외하고, 새로운 리스트 생성, 람다람다식
		list = list.stream().filter( (f) -> f.isEmpty() == false ).collect(Collectors.toList());
		
		//반복문을 돌려서 다중 업로드 해결, 반복문이 짱이다..
		for(MultipartFile file : list) {
			
			//빈파일 거르는 쉬운 방법
			//System.out.println(file.isEmpty());
			//if( file.isEmpty()) continue; //방법1
			//if(file.isEmpty() == false) {} //방법2
			
			//파일 이름을 받는다.
			String originname = file.getOriginalFilename();
			
			//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 substring()으로 잘라서 다시저장
			String filename = originname.substring(originname.lastIndexOf("\\") + 1 ); 
			
			//파일사이즈
			long size = file.getSize();
			
			//동일한 파일을 재업로드시 기존파일을 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜별로 폴더생성
			String filepath = makeFolder();
			
			//세이브 파일 경로
			String savepath = uploadPath + "/"+ filepath + "/" + uuid + "_" + filename;

			//System.out.println(filename);
			//System.out.println(size);
			//데이터베이스에 추후에 저장
			System.out.println("실제파일명 : " + filename);
			System.out.println("파일명 앞에 붙는 난수번호 : " + uuid);
			System.out.println("날짜폴더경로 : " + filepath);
			System.out.println("세이프할 경로:" + savepath);
			
			try {
				File saveFile = new File(savepath);
				file.transferTo(saveFile);
				
			} catch (Exception e) {
				System.out.println("파일업로드중 에러 발생");
				e.printStackTrace();
			}
			

			
		} //end for
		
		
		return "upload/upload_ok";
	}
	
	//multiple 옵션을 사용한 다중파일 업로드
	@PostMapping("/upload_ok3")
	public String upload_ok3(MultipartHttpServletRequest files) {
		
		List<MultipartFile> list = files.getFiles("file"); //클라이언트 input name
		
				//반복문을 돌려서 다중 업로드 해결, 반복문이 짱이다..
				//빈파일이 있을 수 없다. 한번에 다 선택되서 올라가기 때문
				for(MultipartFile file : list) {
					
					//빈파일 거르는 쉬운 방법
					//System.out.println(file.isEmpty());
					//if( file.isEmpty()) continue; //방법1
					//if(file.isEmpty() == false) {} //방법2
					
					//파일 이름을 받는다.
					String originname = file.getOriginalFilename();
					
					//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 substring()으로 잘라서 다시저장
					String filename = originname.substring(originname.lastIndexOf("\\") + 1 ); 
					
					//파일사이즈
					long size = file.getSize();
					
					//동일한 파일을 재업로드시 기존파일을 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
					String uuid = UUID.randomUUID().toString();
					
					//날짜별로 폴더생성
					String filepath = makeFolder();
					
					//세이브 파일 경로
					String savepath = uploadPath + "/"+ filepath + "/" + uuid + "_" + filename;

					//System.out.println(filename);
					//System.out.println(size);
					//데이터베이스에 추후에 저장
					System.out.println("실제파일명 : " + filename);
					System.out.println("파일명 앞에 붙는 난수번호 : " + uuid);
					System.out.println("날짜폴더경로 : " + filepath);
					System.out.println("세이프할 경로:" + savepath);
					
					try {
						File saveFile = new File(savepath);
						file.transferTo(saveFile);
						
					} catch (Exception e) {
						System.out.println("파일업로드중 에러 발생");
						e.printStackTrace();
					}
					
				} //end for
		
		return "upload/upload_ok";
	}
	
	//비동기방식으로 받기
	//@@ResponseBody를 붙이는 이유는
	@PostMapping("/upload_ok4")
	public @ResponseBody ResponseEntity<String> upload_ok4(ProductVO vo){
		
		MultipartFile file = vo.getFile();
		
		//이건 위의 업로드 코드와 동일
		//파일 이름을 받는다.
		String originname = file.getOriginalFilename();
		
		//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 substring()으로 잘라서 다시저장
		String filename = originname.substring(originname.lastIndexOf("\\") + 1 ); 
		
		//파일사이즈
		long size = file.getSize();
		
		//동일한 파일을 재업로드시 기존파일을 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
		String uuid = UUID.randomUUID().toString();
		
		//날짜별로 폴더생성
		String filepath = makeFolder();
		
		//세이브 파일 경로
		String savepath = uploadPath + "/"+ filepath + "/" + uuid + "_" + filename;

		//System.out.println(filename);
		//System.out.println(size);
		//데이터베이스에 추후에 저장
		System.out.println("실제파일명 : " + filename);
		System.out.println("파일명 앞에 붙는 난수번호 : " + uuid);
		System.out.println("날짜폴더경로 : " + filepath);
		System.out.println("세이프할 경로:" + savepath);
		
		try {
			File saveFile = new File(savepath);
			file.transferTo(saveFile);
			
		} catch (Exception e) {
			System.out.println("파일업로드중 에러 발생");
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//이미지 띄워주기
	@GetMapping("/display")
	public @ResponseBody byte[] display(@RequestParam("filename") String filename,
									    @RequestParam("filepath") String filepath,
									    @RequestParam("uuid") String uuid) {
		//읽어올 경로
		String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
		System.out.println(path);
		
		byte[] data = null;
		
		try {
		  data = FileCopyUtils.copyToByteArray(new File(path));//바이트 배열 타입으로 반환
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
	
	
	
}
