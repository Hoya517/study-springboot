package com.study.springboot.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository		// DB관련 용도로 사용하겠다는 별칭, spring 내부적으로는 빈 등록만.
public class MyUserDAO {
	@Autowired  // 자동 주입 어노테이션
	private JdbcTemplate jdbcTemplate;  // 드라이버 로드 + db접속 + 커넥션 풀 생성
	
	public List<MyUserDTO> list() {
		String query = "select * from myuser";
		List<MyUserDTO> list = jdbcTemplate.query(
				query, new BeanPropertyRowMapper<MyUserDTO>(MyUserDTO.class));  // 쿼리문 실행
//		for(MyUserDTO my : list) {
//			System.out.println(my);
//		}
		
		return list;  // List로 출력
	}
}
