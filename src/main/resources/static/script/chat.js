$(document).ready(function() {
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    let currentRecipient = null;
    let currentUser = null;
    let loggedInUsers = new Set();

    function fetchCurrentUser() {
        return fetch('/user/userInfo')
            .then(response => response.json())
            .then(user => {
                currentUser = user.userCode;
                $('#currentUser').text(currentUser);
            });
    }

    function fetchAllUsers() {
        return fetch('/user/allUsers')
            .then(response => response.json())
            .then(users => {
                const userList = $('#userList');
                userList.empty();
                users.forEach(user => {
                    if (user !== currentUser){
                        const userItem = $('<li>').text(user);
                        const statusIndicator = $('<span>').addClass('status-indicator');

                        if (loggedInUsers.has(user)) {
                            statusIndicator.addClass('online');
                        } else {
                            statusIndicator.addClass('offline');
                        }

                        userItem.append(statusIndicator);
                        userItem.click(() => {
                            currentRecipient = user;
                            $('#chatWindow').empty();  // 기존 메시지 지우기
                            $('#messageInputContainer').show();  // 입력창 보이기
                            fetchChatHistory(currentUser, currentRecipient);
                        });
                        userList.append(userItem);
                    }

                });
            });
    }

    function fetchLogOnUsers() {
        return fetch('/user/logOnUsers')
            .then(response => response.json())
            .then(users => {
                loggedInUsers = new Set(users);
                fetchAllUsers();
            });
    }

    async function fetchChatHistory(sender, recipient) {
        console.log("sender: ", sender);
        console.log("recipient: ", recipient);

        try {
            const response = await fetch(`/messages/${sender}/${recipient}`);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }

            const messages = await response.json();
            const chatWindow = $('#chatWindow');
            chatWindow.empty();  // 기존 메시지 지우기

            if (messages && Array.isArray(messages)) {
                messages.forEach(message => {
                    displayMessage(message.sender, message.content);
                });
            }
        } catch (error) {
            console.error('Error fetching chat history:', error);
        }
    }

    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/queue/reply', (message) => {
            const chatMessage = JSON.parse(message.body);
            if (currentRecipient && (chatMessage.sender === currentRecipient || chatMessage.recipient === currentRecipient)) {
                displayMessage(chatMessage.sender, chatMessage.content);
            }
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

    $('#messageInput').keypress(function (e) {
        if (e.which === 13) {  // Enter key pressed
            $('#sendMessageBtn').click();
        }
    });

    function displayMessage(sender, content) {
        const chatWindow = $('#chatWindow');
        chatWindow.append('<div class="message"><strong>' + sender + ':</strong> ' + content + '</div>');
        chatWindow.scrollTop(chatWindow.prop("scrollHeight"));
    }

    $('#messageInputContainer').hide();

    // 1분마다 fetchLogOnUsers 실행
    setInterval(fetchLogOnUsers, 60000);  // 60000 ms = 1 minute

    // 페이지를 떠날 때 소켓 연결 해지
    window.onbeforeunload = function() {
        if (stompClient) {
            stompClient.disconnect(() => {
                console.log('Disconnected');
            });
        }
    };
});
