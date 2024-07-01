$(document).ready(function () {
    $('.button:not(.delete)').on('click', function (e) {
        e.preventDefault();

        var reportData = {
            studyKey: $('#studyKey').val(),
            userCode: $('#userCode').val(),
            date: $('#date').val(),
            diseaseCode: $('#diseaseCode').val(),
            content: $('#content').val(),
            patient: $('#patient').val(),
            videoReplay: $('#videoReplay').val(),  // 필요에 따라 적절한 값을 넣어줍니다.
        };

        console.log('전송할 데이터:', reportData);

        $.ajax({
            type: "PUT",
            url: "/report/" + $('#studyKey').val(),
            contentType: "application/x-www-form-urlencoded",
            data: $.param(reportData),
            success: function (response) {
                console.log('성공 응답:', response);
                alert("리포트가 수정 되었습니다.");
                location.href="/worklist";
                // 필요한 경우 UI를 업데이트합니다.
            },
            error: function (xhr, status, error) {
                console.error('에러 응답:', xhr, status, error);
                alert("리포트 수정을 실패했습니다.");
            }
        });
    });

    $('#deleteButton').on('click', function (e) {
        e.preventDefault();

        if (confirm("정말로 리포트를 삭제하시겠습니까?")) {
            $.ajax({
                type: "DELETE",
                url: "/report/" + $('#studyKey').val() + "/" + $('#userCode').val(),
                success: function (response) {
                    console.log('성공 응답:', response);
                    alert("리포트가 삭제 되었습니다.");
                    location.href = "/worklist";
                },
                error: function (xhr, status, error) {
                    console.error('에러 응답:', xhr, status, error);
                    alert("리포트 삭제를 실패했습니다.");
                }
            });
        }
    });
});
