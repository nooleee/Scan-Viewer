<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/consent-form.css">
    <title>Consent Form</title>
</head>
<body>
<%--<h2>Consent Form</h2>--%>
<form id="consentForm" action="/consent/submit" method="post">
    <div class="form-group">
        <input type="hidden" id="studyKey" name="studyKey" value="${studyKey}">
    </div>
    <div class="form-group">
        <label for="userCode">유저코드:</label>
        <input type="text" id="userCode" name="userCode" readonly>
    </div>
    <div class="form-group">
        <label for="name">이름:</label>
        <input type="text" id="name" name="name" readonly>
    </div>
    <div class="form-group">
        <label for="birth">생년월일:</label>
        <input type="date" id="birth" name="birth" readonly>
    </div>
    <div class="form-group">
        <label for="phone">연락처:</label>
        <input type="text" id="phone" name="phone" readonly>
    </div>
    <div class="form-group">
        <label for="position">직책:</label>
        <input type="text" id="position" name="position" required>
    </div>
    <div class="form-group">
        <label for="address">주소:</label>
        <input type="text" id="address" name="address" required>
    </div>
    <div class="form-group">
        <label for="department">소속부서:</label>
        <input type="text" id="department" name="department" required>
    </div>
    <div class="form-group">
        <label for="user">검사자:</label>
        <input type="text" id="user" name="user" required>
    </div>
    <div class="form-group">
        <label for="detail">환자와의 관계:</label>
        <textarea id="detail" name="detail" required></textarea>
    </div>
    <div class="form-group">
        <label for="purpose">목적:</label>
        <textarea id="purpose" name="purpose" required></textarea>
    </div>
    <div class="form-group">
        <label for="period">Period:</label>
        <input type="text" id="period" name="period" required>
    </div>
    <div class="form-group">
        <button type="submit">Submit</button>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/consent.js"></script>
</body>
</html>
