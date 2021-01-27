package com.study.springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration  // 이 클래스를 빈으로 등록하는데 스프링 설정으로 사용한다.
@EnableWebSecurity  // 스프링 시큐리티의기능을 활성화하겠다.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {  // 시큐리티 설정 내용 구성
		// url 요청에 대한 허용여부 설정
		// 설정 내용이 겹치면 뒤의 설정이 앞의 설정 내용을 덮어씀
		// 그래서 가장 앞 부분에서 가장 넓은 범위로 허용 범위를 정하고 뒤에서 범위를 좁혀 부분적으로 다시 지정
		http.authorizeRequests()
				.antMatchers("/").permitAll()  // 루트(/) url 요청에 대해서 모두에게 허용하는 세팅
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()  // /css, /js, /img 아래 모든 url 요청에 대해서는 모두에게 허용하는 세팅
				.antMatchers("/guest/**").permitAll()  // /guest 아래 모든 url 요청에 대해서 모두에게 허용하는 세팅
				.antMatchers("/member/**").hasAnyRole("USER", "ADMIN")  // /member 아래 url 요청은 'USER'나 'ADMIN' 역할을 가지고 있어야 한다고 세팅
				.antMatchers("/admin/**").hasRole("ADMIN")  // /admin 아래 url 요청은 'ADMIN' 역할을 가지고 있어야 한다고 세팅
				.anyRequest().authenticated();
		
		http.formLogin()
				.permitAll();  // 로그인 폼 url 은 모두에게 허용하는 세팅
		http.logout()
				.permitAll();  // 로그아웃 url 요청은 모두에게 허용하는 세팅
	}
	
	// 빠른 테스트를 위해 등록이 간단한 인메모리(inMemory) 방식의 인증 사용자를 등록
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password(passwordEncoder().encode("1234")).roles("USER")  // 사용자이름:user, 비밀번호:1234, 역할이름:USER 로 사용자를 등록
			.and()
			.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");  // 사용자이름:admin, 비밀번호:1234, 역할이름:ADMIN 로 사용자를 등록
			// ROLE_ADMIN 에서 ROLE_는 자동으로 붙는다.
	}

	// passwordEncoder() 추가
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {  // 비밀번호의 인코딩 방식 지정 (디폴트 사용 => 임의로 코드 변경할 수 없음)
		return new BCryptPasswordEncoder();
	}
}
