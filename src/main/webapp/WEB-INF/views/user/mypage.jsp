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
<h1>${user.userCode}님의 mypage</h1>
<div>${user.name}</div>
<div>${user.group}</div>
<div>${user.birth}</div>

<c:if test="${user.group eq 'Admin'}">
    <button onclick="window.location='/user/manage'">회원관리</button>
</c:if>
<form action="/user/delete" method="post">
    <input type="hidden" name="userCode" value="${user.userCode}">
    <input type="submit" value="회원탈퇴">
</form>
<button onclick="window.location='/chat'">채팅 테스트</button>

<button onclick="window.location='/worklist'">워크리스트로 이동 </button>
</body>
</html>
