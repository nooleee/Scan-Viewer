<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 26.
  Time: 오후 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
</head>
<body>
<h2>회원가입</h2>
<form action="/user/joinProcess" method="post">
    <div>
        <label for="userCode">유저 ID:</label>
        <input type="text" id="userCode" name="userCode" maxlength="20" required>
        <span id="userCodeMessage"></span>
    </div>
    <div>
        <label for="password">패스 워드:</label>
        <input type="password" id="password" name="password" maxlength="255" required>
    </div>
    <div>
        <label for="name">이름:</label>
        <input type="text" id="name" name="name" maxlength="40" required>
    </div>
    <div>
        <label for="phone">연락처:</label>
        <input type="text" id="phone" name="phone" maxlength="13" pattern="\d{3}-\d{4}-\d{4}" title="000-0000-0000 형식으로 입력해주세요." required>
    </div>
    <div>
        <label for="birth">생년월일:</label>
        <input type="date" id="birth" name="birth" required>
    </div>
    <div>
        <label for="group">그룹 ID:</label>
        <select id="group" name="group" required>
            <option value="Admin">Admin</option>
            <option value="Radiologist">Radiologist</option>
            <option value="Technician">Technician</option>
            <option value="Doctor">Doctor</option>
        </select>
    </div>
    <div>
        <button type="submit">회원가입</button>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/checkUserCode.js"></script>
</body>
</html>
