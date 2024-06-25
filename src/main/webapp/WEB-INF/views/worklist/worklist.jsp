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
    <title>Title</title>
</head>
<body>
<h1>Worklist</h1>
<table border="1">
    <thead>
    <tr>
        <th>환자 아이디</th>
        <th>환자 이름</th>
        <th>검사장비</th>
        <th>검사설명</th>
        <th>검사일시</th>
        <th>판독상태</th>
        <th>시리즈</th>
        <th>이미지</th>
        <th>Verify</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="study" items="${studies}">
        <tr>
            <td>${study.pid}</td>
            <td>${study.pname}</td>
            <td>${study.modality}</td>
            <td>${study.studydesc}</td>
            <td>${study.studydate}</td>
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
            <td>${study.seriescnt}</td>
            <td>${study.imagecnt}</td>
            <td>
                <c:choose>
                    <c:when test="${study.examstatus == 1}">
                        아니오
                    </c:when>
                    <c:otherwise>
                        예
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
