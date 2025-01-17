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
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

$(document).ready(function () {
    function setCurrentDateTime() {
        var now = new Date();
        var year = now.getFullYear();
        var month = ('0' + (now.getMonth() + 1)).slice(-2);
        var day = ('0' + now.getDate()).slice(-2);
        var hours = ('0' + now.getHours()).slice(-2);
        var minutes = ('0' + now.getMinutes()).slice(-2);
        var seconds = ('0' + now.getSeconds()).slice(-2);
        var formattedDateTime = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
        $('#date').val(formattedDateTime);
    }

    function getStudyKeyFromUrl() {
        var pathParts = window.location.pathname.split('/');
        if (pathParts.length >= 3) {
            return pathParts[2]; // 예: /images/17/1에서 17 추출
        }
        return null;
    }

    $('.button').off('click').on('click', function (e) {
        e.preventDefault();

        setCurrentDateTime();

        var studyKey = getStudyKeyFromUrl();
        if (!studyKey) {
            alert('유효한 studykey를 추출할 수 없습니다.');
            return;
        }

        var reportData = {
            studyKey: studyKey,
            userCode: $('#userCode').val(),
            date: $('#date').val(),
            diseaseCode: $('#diseaseCode').val(),
            content: $('#content').val(),
            patient: $('#patient').val(),
            videoReplay: $('#videoReplay').val(),
        };

        if (!reportData.content || !reportData.patient || !reportData.diseaseCode) {
            alert('리포트 내용을 작성해주세요.');
            return;
        }

        $.ajax({
            type: "POST",
            url: "/report/" + studyKey,
            contentType: "application/x-www-form-urlencoded",
            data: $.param(reportData),
            success: function (response) {
                console.log('성공 응답:', response);
                alert("리포트가 생성되었습니다.");
                location.href="/worklist";
            },
            error: function (xhr, status, error) {
                console.error('에러 응답:', xhr, status, error);
                alert("리포트 생성을 실패했습니다.");
            }
        });
    });

    $('#searchICDButton').on('click', function () {
        var query = $('#diseaseCode').val();
        $.ajax({
            type: "GET",
            url: "/report/searchICD",
            data: { query: query },
            success: function (response) {
                console.log('ICD 코드 검색 결과:', response);
                if (response === "검색 결과가 없습니다.") {
                    alert("ICD 코드 검색 결과가 없습니다.");
                } else {
                    // 검색 결과를 표시합니다.
                    $('#icdResults').html('<ul>' + response.slice(1, -1).split(',').map(function(item) {
                        var parts = item.trim().split('|');
                        var title = parts[0];
                        var code = parts[1];
                        return '<li class="icd-result-item" data-code="' + code + '">' + title + '</li>';
                    }).join('') + '</ul>');

                    // 검색 결과 항목에 클릭 이벤트 핸들러 추가
                    $('.icd-result-item').on('click', function () {
                        $('#diseaseCode').val($(this).data('code'));
                        $('#icdResults').empty();  // 검색 결과 목록 비우기
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error('ICD 코드 검색 에러:', xhr, status, error);
                alert("ICD 코드 검색에 실패했습니다.");
            }
        });
    });
});
