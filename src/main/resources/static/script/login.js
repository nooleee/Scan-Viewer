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
            localStorage.setItem('jwt', data.jwt);
            window.location.href = '/user/mypage';
        })
        .catch(error => {
            alert('Login failed: ' + error.message);
        });
});
