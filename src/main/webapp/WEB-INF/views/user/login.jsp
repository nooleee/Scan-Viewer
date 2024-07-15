<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 7. 1.
  Time: 오후 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

</head>
<body>
<jsp:include page="../commons/header.jsp"></jsp:include>
<div class="login-container">
    <h2>Login</h2>
    <form action="/user/loginProcess" method="post" id="login-form">
        <input type="text" id="userCode" name="userCode" placeholder="Username" required>
        <input type="password" id="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>
    <button class="signup" onclick="window.location = '/user/signup'">Sign Up</button>
</div>
<jsp:include page="../commons/footer.jsp"></jsp:include>


<script type="text/javascript" src="${pageContext.request.contextPath}/script/login.js"></script>
</body>
</html>
