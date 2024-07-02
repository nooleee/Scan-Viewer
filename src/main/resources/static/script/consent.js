document.addEventListener('DOMContentLoaded', function () {
    fetchUserInfo();
});

function fetchUserInfo() {
    fetch('/user/userInfo', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch user info');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('userCode').value = data.userCode;
            document.getElementById('name').value = data.name;
            document.getElementById('birth').value = data.birth;
            document.getElementById('phone').value = data.phone;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}