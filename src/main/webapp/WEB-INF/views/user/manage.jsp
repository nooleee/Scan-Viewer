<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 27.
  Time: 오전 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users</title>
</head>
<body>
<h2>Manage Users</h2>
<table border="1">
    <thead>
    <tr>
        <th>User Code</th>
        <th>Name</th>
        <th>Group</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userCode}</td>
            <td>${user.name}</td>
            <td>${user.group}</td>
            <c:choose>
                <c:when test="${user.group != 'Admin'}">
                    <td>
                        <a href="/user/edit/${user.userCode}">Edit</a>
                    </td>
                </c:when>
                <c:otherwise>
                    <td></td>
                </c:otherwise>
            </c:choose>

        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>