<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:24
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
<h1>보고서</h1>
<table border="1">
    <thead>
    <tr>
        <th>환자 이름</th>
        <th>환자 아이디</th>
        <th>검사 날짜</th>
        <th>검사 설명</th>
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
        </tr>
    </c:forEach>
    </tbody>
</table>

<form id="reportForm" method="post">
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
        <input type="text" id="userCode" readonly/>
    </div>
    <div>
        <label>판독 일시:</label>
        <input type="text" id="date" readonly/>
    </div>
    <div>
        <label>질병 코드:</label>
        <input type="text" id="diseaseCode"/>
        <button type="button" id="searchICDButton">ICD 코드 검색</button>
        <div id="icdResults"></div> <!-- 검색 결과 표시 -->
    </div>
    <div>
        <label>판독 상태:</label>
        <select id="videoReplay">
            <option value="읽지않음">읽지않음</option>
            <option value="판독완료">판독완료</option>
            <option value="판독취소">판독취소</option>
        </select>
    </div>
    <div class="button-container">
        <button class="button" type="submit">리포트 생성</button>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/report.js"></script>
</body>
</html>
