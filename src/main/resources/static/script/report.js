$(document).ready(function () {
    $('.blue-button').on('click', function (e) {
        e.preventDefault();

        var reportData = {
            userCode: $('input[name="userCode"]').val(),
            date: $('input[name="date"]').val(),
            diseaseCode: $('input[name="diseaseCode"]').val(),
            content: $('.comment').val(),
            patient: $('.quest').val(),
            videoReplay: '읽지않음',  // 필요에 따라 적절한 값을 넣어줍니다.
            // reportStatus: '읽지않음' // 적절한 값을 넣어줍니다.
            // 필요한 경우 추가 필드를 여기에 추가
        };

        console.log('전송할 데이터:', reportData);

        $.ajax({
            type: "POST",
            url: "/report/save",
            contentType: "application/json",
            data: JSON.stringify(reportData),
            success: function (response) {
                console.log('성공 응답:', response);
                alert("리포트가 저장되었습니다.");
                // 필요한 경우 UI를 업데이트합니다.
            },
            error: function (xhr, status, error) {
                console.error('에러 응답:', xhr, status, error);
                alert("리포트를 저장하는 데 실패했습니다.");
            }
        });
    });
});
