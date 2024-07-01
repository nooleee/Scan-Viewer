document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const userCode = document.getElementById('userCode').value;
    const password = document.getElementById('password').value;

    fetch('/user/loginProcess', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userCode, password })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Login failed');
            }
        })
        .then(data => {
            document.cookie = `jwt=${data.jwt}; path=/;`;
            window.location.href = '/user/mypage';  // 로그인 성공 시 이동할 페이지
        })
        .catch(error => {
            alert('Login failed: ' + error.message);
        });
});
