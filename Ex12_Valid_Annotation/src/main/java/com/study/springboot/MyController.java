package com.study.springboot;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "ValidationUtils (4)";
	}
	
	@RequestMapping("/insertForm")
	public String insert1() {
		
		return "createPage";
	}
	
	@RequestMapping("/create")	
	public String insert2(@ModelAttribute("dto") @Valid ContentDto contentDto
												, BindingResult result) {
		String page = "createDonePage";
		System.out.println(contentDto);
		
		// ContentValidator validator = new ContentValidator();
		// validator.validate(contentDto, result);
		if (result.hasErrors()) {
//			if (result.getFieldError("writer") != null) {
//				System.out.println("1:"+result.getFieldError("writer").getCode());
//			}
//			if (result.getFieldError("content") != null) {
//				System.out.println("1:"+result.getFieldError("content").getCode());
//			}
			if (result.getFieldError("writer") != null) {
				System.out.println("1:"+result.getFieldError("writer").getDefaultMessage());
			}
			if (result.getFieldError("content") != null) {
				System.out.println("1:"+result.getFieldError("content").getDefaultMessage());
			}

			page = "createPage";
		}
		
		return page;
	}
	
//	@InitBinder		// 해당 프로젝트가 시작할 때 먼저 실행 시킴
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(new ContentValidator());	// 약한 결합으로 사용가능
//	}
}