<%--
  Created by IntelliJ IDEA.
  User: mingi
  Date: 2024. 6. 30.
  Time: AM 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>의료 보고서</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/report.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>보고서 수정</h1>
<table border="1">
    <thead>
    <tr>
        <th>환자 이름</th>
        <th>환자 아이디</th>
        <th>검사 날짜</th>
        <th>검사 설명</th>
        <th>판독 상태</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="study" items="${reports}">
        <tr>
            <td>${study.pname}</td>
            <td>${study.pid}</td>
            <td>${study.studydate}</td>
            <td>${study.studydesc}</td>
            <input type="hidden" id="studyKey" value="${study.studykey}"/>
            <td>
                <c:choose>
                    <c:when test="${study.reportstatus == 3}">
                        읽지 않음
                    </c:when>
                    <c:when test="${study.reportstatus == 6}">
                        판독 완료
                    </c:when>
                    <c:when test="${study.reportstatus == 5}">
                        판독 보류
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<form id="reportForm" action="/report" method="post">
    <h2>의사 소견</h2>
    <div>
        <textarea rows="4" cols="50" id="content">${report.content}</textarea>
    </div>

    <h2>결론</h2>
    <div>
        <textarea rows="4" cols="50" id="patient">${report.patient}</textarea>
    </div>

    <h2>추가 정보</h2>
    <div>
        <label>판독의:</label>
        <input type="text" id="userCode" value="${user.userCode}"/>
    </div>
    <div>
        <label>판독 일시:</label>
        <input type="text" id="date"/>
    </div>
    <div>
        <label>질병 코드:</label>
        <input type="text" id="diseaseCode"/>
    </div>
    <div>
        <label>판독 상태:</label>
        <select id="videoReplay">
            <option value="읽지않음">읽지않음</option>
            <option value="판독완료">판독완료</option>
            <option value="판독불가">판독불가</option>
        </select>
    </div>
    <div class="button-container">
        <button class="button" type="submit">리포트 수정</button>
        <button type="button" class="button">리포트 수정 취소</button>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/reportUpdate.js"></script>
</body>
</html>
