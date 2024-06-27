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
</head>
<body>
<h1>보고서</h1>

<form action="/report/{pid}" method="get">
    <label for="pid">환자 아이디:</label>
    <input type="text" id="pid" name="pid">
    <button type="submit">조회</button>
</form>

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

<%--<c:if test="${not empty study}">--%>
    <h2>의사 소견</h2>
    <div>
        <textarea rows="4" cols="50">${study.content}</textarea>
    </div>

    <h2>결론</h2>
    <div>
        <textarea rows="4" cols="50">${study.patient}</textarea>
    </div>

    <h2>추가 정보</h2>
    <form id="reportForm" action="/report" method="post">
        <div>
            <label>판독의:</label>
            <input type="text" name="userCode" value="${study.userCode}" readonly/>
        </div>
        <div>
            <label>판독 일시:</label>
            <input type="text" name="date" value="${study.date}" readonly/>
        </div>
        <div>
            <label>질병 코드:</label>
            <input type="text" name="diseaseCode" value="${study.diseaseCode}" readonly/>
        </div>

        <div>
            <button type="submit">판독</button>
        </div>
    </form>
<%--</c:if>--%>

<div>
    <button onclick="cancelReport()">판독 취소</button>
</div>

<script>
    function cancelReport() {
        // 판독 취소 로직
        alert('판독이 취소되었습니다.');
    }
</script>
</body>
</html>
