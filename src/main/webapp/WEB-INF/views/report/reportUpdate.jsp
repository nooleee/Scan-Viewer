<%--
  Created by IntelliJ IDEA.
  User: mingi
  Date: 2024. 6. 27.
  Time: AM 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>외료 보고서 수정</title>
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
        </tr>
    </c:forEach>
    </tbody>
</table>
<h2>의사 소견</h2>
<div>
    <textarea rows="4" cols="50" placeholder="의사 코멘트">${report.content}</textarea>
</div>

<h2>결론</h2>
<div>
    <textarea rows="4" cols="50" placeholder="결론">${report.patient}</textarea>
</div>

<h2>추가 정보</h2>
<div>
    <label>판독의:</label>
    <input type="text" value="${report.userCode}" readonly/>
</div>
<div>
    <label>판독 일시:</label>
    <input type="text" value="${report.date}" readonly/>
</div>
<div>
    <label>질병 코드:</label>
    <input type="text" value="${report.diseaseCode}" readonly/>
</div>
<div>
    <button>리포트 수정</button>
    <button onclick="cancelReport()">리포트 수정 취소</button>
    <script>
        function cancelReport() {
            alert('리포트 수정 취소되었습니다.');
            window.location.href = '/report';
        }
    </script>
</div>
</body>
</html>
