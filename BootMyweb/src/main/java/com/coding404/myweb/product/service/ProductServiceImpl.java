package com.coding404.myweb.product.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;


@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	/////////////첫번째 추가 업로드 기능///////////
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
	////////////////////////////////
	
	@Override
	////하나의 메서드에서 여러 CRUD작업이 일어나는 경우에 한부분이 에러가 발생하면 그 에러를 처리하고, 롤백처리를 대신한다.
	@Transactional(rollbackFor = Exception.class)
	public int productRegist(ProductVO vo, List<MultipartFile> list) {
		
		//product테이블 처리
		int result = productMapper.productRegist(vo);
		
		//업로드 처리
		for(MultipartFile file : list) {
		
		//파일 이름을 받는다.
		String originname = file.getOriginalFilename();
		
		//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 substring()으로 잘라서 다시저장
		String filename = originname.substring(originname.lastIndexOf("\\") + 1 ); 
		
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
		System.out.println("==================================");
		
		try {
			File saveFile = new File(savepath);
			file.transferTo(saveFile);
			
		} catch (Exception e) {
			System.out.println("파일업로드중 에러 발생");
			e.printStackTrace();
			return 0; //파일 업로드 실패시 0반환
		}
			
		//productUpload테이블에 파일의 경로 인서트
		//ex) 한번 올라가면 한번 인서트 -> 또 한번 올라가면 또 한번 인서트 ....
		//prod_id는 insert전에 insertKey구문으로 인해 자동으로 얻는다. 그러므로 매개변수에 적을 필요가 없다.
		productMapper.productFileRegist(ProductUploadVO.builder()
													   .filename(filename)
													   .filepath(filepath)
													   .uuid(uuid)
													   .prod_writer(vo.getProd_writer())
													   //.prod_id(null)
													   .build()
													   );
		
		
		
		
		} //end for
			
		
		
		return result;
	}

	@Override
	public int getTotal(String writer, Criteria cri) {
		return productMapper.getTotal(writer, cri);
	}

	@Override
	public ArrayList<ProductVO> getList(String writer, Criteria cri) {
		return productMapper.getList(writer, cri);
	}

	@Override
	public ProductVO getDetail(int prod_id) {
		
		return productMapper.getDetail(prod_id);
	}

	@Override
	public int productUpdate(ProductVO vo) {
		return productMapper.productUpdate(vo);
	}

	@Override
	public void productDelete(int prod_id) {
		productMapper.productDelete(prod_id);
		
	}

	@Override
	public ArrayList<CategoryVO> getCategory() {
		
		return productMapper.getCategory();
	}

	@Override
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo) {
		return productMapper.getCategoryChild(vo);
	}

	@Override
	public ArrayList<ProductUploadVO> getAjaxImg(int prod_id) {
		
		return productMapper.getAjaxImg(prod_id);
	}

	
	
	

	

	

	
	
	
}
