package com.study.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper	 // 다음 인터페이스의 구현을 XML로 한다
public interface IMyUserDao {
	List<MyUserDTO> list();
}
