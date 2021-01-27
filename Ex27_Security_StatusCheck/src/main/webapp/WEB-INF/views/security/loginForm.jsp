<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>loginForm.jsp</h1>

<c:url value="j_spring_security_check" var="loginUrl" />  <!-- loginUrl 변수 선언하고 값 저장 -->
<form action="${loginUrl}" method="post">	<!-- 변수를 사용해 액션에 값 지정 (jstl 사용, 별의미X) -->
	<c:if test="${param.error != null}">	<!-- 파라미터의 값을 체크하거나 비교X => error라는 파라미터 자체가 있다면 에러 메시지 출력하는 코드 -->
	<p>
		Login Error! <br />
		${error_message}  <!-- 아직 에러 내용 추가X => 내용 출력해도 안보임 -->
	</p>
	</c:if>
	ID : <input type="text" name="j_username" value="${username}"> <br />
	PW : <input type="text" name="j_password"> <br />
	<input type="submit" value="LOGIN"> <br />
</form>

</body>
</html>