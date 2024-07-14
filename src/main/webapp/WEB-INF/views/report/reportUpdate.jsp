<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medical Report</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/report.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <div class="content">
        <div class="header">
            <h2>Medical Report</h2>
        </div>
        <div class="report-info">
            <c:forEach var="study" items="${study}">
                <p>환자명: ${study.pname} / 환자 아이디: ${study.pid} / 스터디번호: ${study.studykey}</p>
                <p>검사 날짜: ${study.studydate}</p>
                <p>검사명: ${study.studydesc}</p>
                <input type="hidden" id="studyKey" value="${study.studykey}"/>
            </c:forEach>
        </div>
        <form id="reportForm" method="post">
            <div class="form-group">
                <label for="content">의사 소견</label>
                <textarea id="content" rows="4">${report.content}</textarea>
            </div>
            <div class="form-group">
                <label for="patient">결론</label>
                <textarea id="patient" rows="4">${report.patient}</textarea>
            </div>
            <div class="form-group">
                <label for="userCode">판독의</label>
                <input type="text" id="userCode" readonly value="${report.userCode}"/>
            </div>
            <div class="form-group">
                <label for="date">판독 일시</label>
                <input type="text" id="date" readonly value="${report.date}"/>
            </div>
            <div class="form-group">
                <label for="diseaseCode">질병 코드</label>
                <input type="text" id="diseaseCode" value="${report.diseaseCode}"/>
                <button type="button" id="searchICDButton">ICD 코드 검색</button>
                <div id="icdResults"></div>
            </div>
            <div class="form-group">
                <label for="videoReplay">판독 상태</label>
                <select id="videoReplay" name="videoReplay">
                    <option value="읽지않음" <c:if test="${report.videoReplay == '읽지않음'}">selected</c:if>>읽지않음</option>
                    <option value="판독완료" <c:if test="${report.videoReplay == '판독완료'}">selected</c:if>>판독완료</option>
                    <option value="판독취소" <c:if test="${report.videoReplay == '판독취소'}">selected</c:if>>판독취소</option>
                </select>
            </div>
            <div class="button-container">
                <button class="button" type="submit">리포트 수정</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/reportUpdate.js"></script>
</body>
</html>
