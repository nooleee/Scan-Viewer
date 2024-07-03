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
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div>
    <h2>Logged In Users</h2>
    <ul id="userList"></ul>
</div>

<div>
    <h2>Chat</h2>
    <div id="chatWindow"></div>
    <div id="messageInputContainer">
        <input type="text" id="messageInput" placeholder="Enter your message"/>
        <button id="sendMessageBtn">Send</button>
    </div>
</div>

<script>
    $(document).ready(function() {
        const socket = new SockJS('/ws');
        const stompClient = Stomp.over(socket);

        let currentRecipient = null;
        let currentUser = null;

        function fetchCurrentUser() {
            return fetch('/user/userInfo')
                .then(response => response.json())
                .then(user => {
                    currentUser = user.userCode;
                    $('#currentUser').text(currentUser);
                });
        }

        function fetchLogOnUsers() {
            return fetch('/user/logOnUsers')
                .then(response => response.json())
                .then(users => {
                    const userList = $('#userList');
                    userList.empty();
                    users.forEach(user => {
                        if (user !== currentUser) {
                            const userItem = $('<li>').text(user);
                            userItem.click(() => {
                                currentRecipient = user;
                                $('#chatWindow').empty();  // 기존 메시지 지우기
                                fetchChatHistory(currentUser, currentRecipient);
                                $('#messageInputContainer').show();  // 입력창 보이기
                            });
                            userList.append(userItem);
                        }
                    });
                });
        }

        function fetchChatHistory(sender, recipient) {
            fetch(`/messages/${sender}/${recipient}`)
                .then(response => response.json())
                .then(messages => {
                    const chatWindow = $('#chatWindow');
                    chatWindow.empty();  // 기존 메시지 지우기
                    messages.forEach(message => {
                        displayMessage(message.sender, message.content);
                    });
                });
        }

        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/user/queue/reply', (message) => {
                const chatMessage = JSON.parse(message.body);
                displayMessage(chatMessage.sender, chatMessage.content);
            });

            fetchCurrentUser().then(fetchLogOnUsers);
        });

        $('#sendMessageBtn').click(() => {
            const messageContent = $('#messageInput').val();
            if (messageContent && currentRecipient) {
                const message = {
                    sender: currentUser,
                    recipient: currentRecipient,
                    content: messageContent,
                    type: 'CHAT'
                };
                stompClient.send('/app/sendMessage', {}, JSON.stringify(message));
                displayMessage(currentUser, messageContent);  // 본인이 보낸 메시지도 표시
                $('#messageInput').val('');
            } else {
                console.error("Message content or recipient is missing");
            }
        });

        function displayMessage(sender, content) {
            const chatWindow = $('#chatWindow');
            chatWindow.append('<div><strong>' + sender + ':</strong> ' + content + '</div>');
        }

        // 초기 로드시 입력창을 숨기기
        $('#messageInputContainer').hide();
    });

</script>
</body>
</html>
