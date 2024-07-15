<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 7. 3.
  Time: 오후 2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chat Application</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/chat.css"/>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="../commons/header.jsp"></jsp:include>
<div class="chat-container">
    <div class="user-list">
        <h2>All Users</h2>
        <ul id="userList"></ul>
    </div>
    <div class="chat-window">
        <h2>Chat</h2>
        <div id="chatWindow" class="messages"></div>
        <div id="messageInputContainer" class="message-input">
            <input type="text" id="messageInput" placeholder="Enter your message"/>
            <button id="sendMessageBtn">Send</button>
        </div>
    </div>
</div>
<jsp:include page="../commons/footer.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/chat.js"></script>

</body>
</html>
