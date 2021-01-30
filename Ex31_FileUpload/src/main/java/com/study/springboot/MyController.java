package com.study.springboot;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "FileUpload";
	}
	
	@RequestMapping("/uploadForm")
	public String uploadForm() {
		
		return "fileForm";
	}
	
	@RequestMapping("/uploadOk")
	public @ResponseBody String uploadOk(HttpServletRequest request) {
		int size = 1024 * 1024 * 10;  // 업로드될 파일의 최대 크기 지정 - 10M
		String file = "";
		String oriFile = "";
		
		JSONObject obj = new JSONObject();  // json 객체 변수 생성
		
		try {
			String path = ResourceUtils.getFile("classpath:static/upload/").toPath().toString();  // 폴더 위치 지정
			System.out.println(path);
			
			MultipartRequest multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());  // 폼에서 올라온 멀티파트 리퀘스트로부터 정보를 추출할 변수를 만든다.
			System.out.println("111111");
			Enumeration<?> files = multi.getFileNames();
			String str = (String)files.nextElement();  // 업로드된 파일들에 대한 정보를 가져온다.
			
			file = multi.getFilesystemName(str);  // 실제로 저장된 파일 이름을 가져온다.
			oriFile = multi.getOriginalFileName(str);  // 사용자가 실제로 업로드한 파일 이름을 가져온다.
			
			obj.put("succes", new Integer(1));  // 업로드 성공에 대한 정보를 json 객체에 넣는다.
			obj.put("desc", "업로드 성공");  // 업로드 성공에 대한 정보를 json 객체에 넣는다.
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("succes", new Integer(0));  // 업로드 실패에 대한 정보를 json 객체에 넣는다.
			obj.put("desc", "업로드 실패");  // 업로드 실패에 대한 정보를 json 객체에 넣는다.
		}
		
		return obj.toJSONString();
	}
}
