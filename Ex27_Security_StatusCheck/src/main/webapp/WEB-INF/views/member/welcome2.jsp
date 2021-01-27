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
welcome : Member

<hr>

<c:if test="${not empty pageContext.request.userPrincipal}">  <!-- 로그인 상태라면 request.userPrincipal에 사용자의 로그인 정보가 들어 있음 -->
<p> is Log-In</p>
</c:if>

<c:if test="${empty pageContext.request.userPrincipal}">  <!-- 사용자 인증이 되지 않으면 이 페이지는 출력되지 않음 => 이 코드는 불리지 않는다 -->
<p> is Log-Out</p>
</c:if>

USER ID : ${pageContext.request.userPrincipal.name}<br/>  <!-- usernameParameter에 들어온 값 출력 -->
<a href="/logout">Log Out</a> <br />

</body>
</html>