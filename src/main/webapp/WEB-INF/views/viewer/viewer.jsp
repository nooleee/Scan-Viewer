<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>CornerStone Sample Test</h1>
<div id="content">
    <c:forEach var="image" items="${images}">
        <img src="Z:/${image.path}${image.fname}" alt="DICOM Image" width="100" height="100"/>
    </c:forEach>
</div>
<input id ="file" type="file" accept="application/dicom">
<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>

