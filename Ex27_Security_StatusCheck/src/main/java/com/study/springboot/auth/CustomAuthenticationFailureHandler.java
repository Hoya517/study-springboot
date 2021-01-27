package com.study.springboot.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	// 스프링 시큐리티 인증 처리를 할 때 이 사용자 정의 클래스를 사용할 수 있도록 지정하면 인증 실패 시 아래 메서드가 호출
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,   // 리퀘스트를 후킹하여 메서드에 필요한 체크를 하고
									    HttpServletResponse response,
									    AuthenticationException exception)
	throws IOException, ServletException 
	{
		String loginid = request.getParameter("j_username");  // 폼 데이터 변수에 저장
		String errormsg = "";
		
		// 에러 내용 비교 후 해당 한글 메시지 작성
		// 에러 내용이 너무 자세하면 해킹의 위험이 있으므로 두루 뭉실하게 작성
		if (exception instanceof BadCredentialsException) {
			loginFailureCount(loginid);  // 로그인 실패시 횟수 체크
			errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			loginFailureCount(loginid);
			errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		} else if (exception instanceof DisabledException) {
			errormsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
		} else if (exception instanceof CredentialsExpiredException) {
			errormsg = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
		}
		
		request.setAttribute("username", loginid);			// 다시 리퀘스트에 세팅하고
		request.setAttribute("error_message", errormsg);	// 다시 리퀘스트에 세팅하고
		
		request.getRequestDispatcher("/loginForm?error=true").forward(request, response);  // 리퀘스트 디스패쳐를 통해 포워딩
	}
	
	// 비밀번호를 3번 이상 틀릴 시 계정 잠금 처리
	protected void loginFailureCount(String username) {
		/* 슈도 코딩 => 이런 일을 할것이라는 표시, 작동X
		// 틀린 횟수 업데이트
		userDao.countFailure(username);
		// 틀린 횟수 조회
		int cnt = userDao.checkFailureCount(username);
		if(cnt==3) {
			// 계정 잠금 처리
			userDao.disabledUsername(username);
		*/
	}
	
}
