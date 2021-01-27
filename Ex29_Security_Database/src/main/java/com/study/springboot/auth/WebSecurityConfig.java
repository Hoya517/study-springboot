package com.study.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()
				.antMatchers("/guest/**").permitAll()
				.antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated();
		
		http.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/j_spring_security_check")
				.failureHandler(authenticationFailureHandler)
				//.defaultSuccessUrl("/")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.permitAll();
		http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();
		
		http.csrf().disable();
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
//			.and()
//			.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
//	}
	
	// DB관련 변수 설정 및 자동주입
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {  // 오버로딩
//		System.out.println(passwordEncoder().encode("123"));  // 암호화된 비밀번호를 얻기 위해 사용한 테스트용 코드 
															  // 한 번 실행 후 암호화된 비밀번호를 얻은 뒤엔 주석 처리하거나 지워야 한다.
		auth.jdbcAuthentication()
			.dataSource(dataSource)  // (데이터베이스의 테이블에서 사용자를 조회해 사용자 인증을 하기위해) 데이터베이스 접속 정보를 먼저 이용하고
			.usersByUsernameQuery("select name as userName, password, enabled from user_list where name =?")  // 해당 사용자가 있는지 조회
			.authoritiesByUsernameQuery("select name as userName, authority from user_list where name = ?")  // 있다면 사용자의 역할 구해옴
			.passwordEncoder(new BCryptPasswordEncoder());  // 입력한 비밀번호를 암호화해서 데이터베이스의 암호와 비교를 해서 올바른 값인지 검증
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
