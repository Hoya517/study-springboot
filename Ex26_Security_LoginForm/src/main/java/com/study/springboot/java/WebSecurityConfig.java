package com.study.springboot.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // CSRF 프로텍션이 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
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
				.loginPage("/loginForm")  // default : /login  |  로그인 폼의 url을 지정
				.loginProcessingUrl("/j_spring_security_check")  // 스프링의 시큐리티 인증 url
				.failureUrl("/loginError")  // default : /login?error  |  로그인 실패시 호출될 url
				//.defaultSuccessUrl("/")
				.usernameParameter("j_username")  // default : j_username => jsp에서 지정한 변수명으로 파라미터명을 지정
				.passwordParameter("j_password")  // default : j_password => jsp에서 지정한 변수명으로 파라미터명을 지정
				.permitAll();
		http.logout()
				.logoutUrl("/logout")  // default  |  로그아웃의 경우 url 지정
				.logoutSuccessUrl("/")  // 로그아웃시 리다이렉션 될 url
				.permitAll();
		
		// ssl을 사용하지 않으면 true로 사용
		http.csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
			.and()
			.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
