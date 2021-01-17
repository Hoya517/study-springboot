package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Validator (1)";
	}
	
	@RequestMapping("/insertForm")  // 단순히 JSP페이지를 리턴
	public String insert1() {
		return "createPage";
	}
	
	@RequestMapping("/create")	
	public String insert2(@ModelAttribute("dto") ContentDto contentDto	// 객체를 파라미터로 폼 데이터를 받아서 처리
												, BindingResult result) {
		String page = "createDonePage";
		System.out.println(contentDto);
		
		ContentValidator validator = new ContentValidator();	// 유효성 검증 객체를 만들고
		validator.validate(contentDto, result);		// 파라미터로 받은 커맨드 객체의 값을 검증한다.
													// 메서드 자체에는 리턴값 X, 참조 변수로 result를 함께 보냈기에
		if (result.hasErrors()) {	// result에 값이 들어 있다면 에러가 있는 것이므로 체크한다.
			page = "createPage";	// 에러 있을면 입력페이지 리턴
		}
		
		return page;	// 에러 없으면 결과페이지 리턴
	}
}
