<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 27.
  Time: 오전 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${sessionScope.user.userCode}님의 mypage</h1>
<div>${sessionScope.user.name}</div>
<div>${sessionScope.user.group}</div>
<div>${sessionScope.user.birth}</div>

<c:if test="${sessionScope.user.group eq 'Admin'}">
    <button onclick="window.location='/user/manage'">회원관리</button>
</c:if>
<form action="/user/delete" method="post">
    <input type="hidden" name="userCode" value="${sessionScope.user.userCode}">
    <input type="submit" value="회원탈퇴">
</form>
</body>
</html>
