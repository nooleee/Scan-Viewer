<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Image List</title>
</head>
<body>
<h1>Image List</h1>
<table>
    <thead>
    <tr>
        <th>Study Key</th>
        <th>Series Key</th>
        <th>Image Key</th>
        <th>Path</th>
        <th>File Name</th>
        <th>Image</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="image" items="${images}">
        <tr>
            <td>${image.studykey}</td>
            <td>${image.serieskey}</td>
            <td>${image.imagekey}</td>
            <td>${image.path}</td>
            <td>${image.fname}</td>
            <td><img src="${pageContext.request.contextPath}/${image.path}/${image.fname}" alt="Image" width="100" height="100"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>
    <c:if test="${!imagesPage.empty}">
        <c:if test="${!imagesPage.first}">
            <a href="${pageContext.request.contextPath}/images/list/page?page=${imagesPage.number - 1}&size=${imagesPage.size}">Previous</a>
        </c:if>
        <c:if test="${!imagesPage.last}">
            <a href="${pageContext.request.contextPath}/images/list/page?page=${imagesPage.number + 1}&size=${imagesPage.size}">Next</a>
        </c:if>
    </c:if>
</div>
</body>
</html>

