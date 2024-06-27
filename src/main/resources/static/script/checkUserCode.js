// static/js/checkUserCode.js
let isDuplicateUserCode = false;

async function checkUserCode() {
    const userCode = document.getElementById('userCode').value;
    if (userCode.length === 0) {
        return;
    }

    const response = await fetch(`/user/checkUserCode?userCode=${userCode}`);
    const isDuplicate = await response.json();
    isDuplicateUserCode = isDuplicate;  // 중복 여부 저장
    console.log("isDuplicate : ",isDuplicate);

    const userCodeMessage = document.getElementById('userCodeMessage');
    if (isDuplicate) {
        userCodeMessage.textContent = '이미 사용 중인 유저 ID입니다.';
        userCodeMessage.style.color = 'red';
    } else {
        userCodeMessage.textContent = '사용 가능한 유저 ID입니다.';
        userCodeMessage.style.color = 'green';
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('userCode').addEventListener('blur', checkUserCode);

    document.querySelector('form').addEventListener('submit', function(event) {
        if (isDuplicateUserCode) {
            alert('유저 ID를 확인해주세요.');
            event.preventDefault();  // submit 요청 막기
        }
    });
});
