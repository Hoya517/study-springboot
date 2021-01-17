package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.MyUserDAO;


@Controller
public class MyController {
	
	@Autowired  // 자동 주입 지정 (현 프로젝트에서는 @Repository로 등록한 빈이 한개 밖에 없으므로 자동 주입 가능)
	private MyUserDAO userDao;  // 객체 변수
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "JdbcTemplate 사용하기";
	}
	
	//@GetMapping("/user")	// 아래와 동일
	@RequestMapping(value = "/user", method = RequestMethod.GET)  // 리퀘스트맵핑(url, 호출방식)
	public String userlistPage(Model model) {
		model.addAttribute("users", userDao.list());  // 리스트형 데이터 리턴받아
		return "userlist";  // jsp 호출
	}
}
