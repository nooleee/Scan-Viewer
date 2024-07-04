let currentPage = 0;
let pageSize = 5;
let currentSearch = { pid: '', pname: '', modality: '' };

document.getElementById('loadMoreBtn').addEventListener('click', function() {
    currentPage++;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const start = startDate.replaceAll("-","");
    const end = endDate.replaceAll("-","");
    searchStudies(currentSearch.pid, currentSearch.pname,start,end, currentPage, pageSize, currentSearch.modality);
});

document.getElementById('getAllStudiesBtn').addEventListener('click', function() {
    reset();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    searchStudies( '','',startDate,endDate,currentPage,pageSize, '');
});

document.querySelectorAll('.search-button').forEach(button => {
    button.addEventListener('click', function () {
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        const start = startDate.replaceAll("-", "");
        const end = endDate.replaceAll("-", "");
        const pid = document.querySelector('input[placeholder="환자 아이디"]').value;
        const pname = document.querySelector('input[placeholder="환자 이름"]').value;
        const modality = document.getElementById('modalitySelect').value;
        const reportStatus = document.getElementById('reportStatus').value;
        currentPage = 0;  // Reset page count for new search
        currentSearch = {pid, pname, modality};  // Store current search
        searchStudies(pid, pname, start, end, currentPage, pageSize, modality, reportStatus);
    })
});

document.querySelectorAll('.reset').forEach(button => {
    button.addEventListener('click', function () {
        reset();
    })
});

document.getElementById('mypage').addEventListener('click', function() {
    window.location.href = '/user/mypage';
});

document.getElementById('logout').addEventListener('click', function() {
    window.location.href = '/user/logout';
});

document.getElementById('pageSizeSelect').addEventListener('change', function() {
    pageSize = parseInt(this.value);
    const pid = document.querySelector('input[placeholder="환자 아이디"]').value;
    const pname = document.querySelector('input[placeholder="환자 이름"]').value;
    currentPage = 0;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const start = startDate.replaceAll("-", "");
    const end = endDate.replaceAll("-", "");
    const modality = document.getElementById('modalitySelect').value;
    currentSearch = { pid, pname, modality };
    searchStudies(pid, pname, start, end, currentPage, pageSize, modality);
});

function appendStudies(studies) {
    const dataTable = document.getElementById('data-table').getElementsByTagName('tbody')[0];

    studies.forEach(study => {
        const row = dataTable.insertRow();

        let reportStatusText = '';
        switch (study.reportstatus) {
            case 0:
                reportStatusText = '읽지않음';
                break;
            case 1:
                reportStatusText = '판독취소';
                break;
            case 2:
                reportStatusText = '판독완료';
                break;
        }

        let examStatusText = '';
        switch (study.examstatus){
            case 1:
                examStatusText = 'O';
                break;
            case 0:
                examStatusText = 'X';
                break;
        }

        row.innerHTML = `
            <td>${study.pid}</td>
            <td>${study.pname}</td>
            <td>${study.modality}</td>
            <td>${study.studydesc}</td>
            <td>${study.studydate}</td>
            <td>${reportStatusText}</td>  
            <td>${study.seriescnt}</td>
            <td>${study.imagecnt}</td>
            <td>${examStatusText}</td>
            <input type="hidden" class="studykey" value="${study.studykey}">
        `;
    });
}

document.addEventListener('DOMContentLoaded', function () {

    const table = document.getElementById('data-table').getElementsByTagName('tbody')[0];

    table.addEventListener('click', function(event) {
        const targetRow = event.target.closest('tr');

        const pid = targetRow.querySelector('td:nth-child(1)').textContent;
        fetchStudiesByPid(pid);

        // 보고서 정보 가져오기
        const studyKey = targetRow.querySelector('.studykey').value;
        populateReportSection({ studykey: studyKey });
    });

    table.addEventListener('dblclick', function (event) {
        const targetRow = event.target.closest('tr');
        if (targetRow) {
            const studyKey = targetRow.querySelector('.studykey').value;
            const url = `/consent/${encodeURIComponent(studyKey)}`;
            window.location.href = url;
        }
    });

    // 페이지 초기 로드 시 날짜를 설정
    var today = new Date();
    var formattedToday = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);

    $('#startDate').datepicker({
        dateFormat: 'yy-mm-dd',
        defaultDate: new Date(1990, 0, 1)
    }).datepicker('setDate', new Date(1990, 0, 1));

    $('#endDate').datepicker({
        dateFormat: 'yy-mm-dd',
        defaultDate: formattedToday
    }).datepicker('setDate', formattedToday);

    // 세부 검색 버튼 클릭 시 상세 검색 div 토글
    $('#toggleSearchDetail').click(function() {
        $('#searchDetail').toggleClass('hidden');
    });

    // 날짜로 검색 버튼 클릭 시 처리
    $('#searchByDate').click(function() {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        // 여기에 날짜로 검색하는 기능을 추가하세요.
        console.log('Searching from', startDate, 'to', endDate);
    });

    flatpickr("#calendar", {
        inline: true,
        mode: "range",
        defaultDate: ["1990-01-01", formattedToday],
        locale: "ko", // 언어 설정
        onReady: function(selectedDates, dateStr, instance) {
            instance.jumpToDate(formattedToday); // 달력을 현재 날짜로 이동
        },
        onChange: function(selectedDates, dateStr, instance) {
            var startDate = selectedDates[0];
            var endDate = selectedDates[1] || startDate;
            $('#startDate').val(instance.formatDate(startDate, "Y-m-d"));
            $('#endDate').val(instance.formatDate(endDate, "Y-m-d"));
        }
    });
});

function fetchStudiesByPid(pid) {
    fetch(`/studiesByPid/${pid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            displayStudyKeys(data);
        })
        .catch(error => {
            console.error('오류 발생:', error);
            alert('데이터를 불러오는 중 오류가 발생했습니다.');
        });
}

function displayStudyKeys(data) {
    const dataTable = document.getElementById('previous-table').getElementsByTagName('tbody')[0];
    dataTable.innerHTML = '';

    data.forEach(study => {
        const row = dataTable.insertRow();

        let reportStatusText = '';
        switch (study.reportstatus) {
            case 0:
                reportStatusText = '읽지않음';
                break;
            case 1:
                reportStatusText = '판독취소';
                break;
            case 2:
                reportStatusText = '판독완료';
                break;
        }

        let examStatusText = '';
        switch (study.examstatus){
            case 1:
                examStatusText = 'O';
                break;
            case 0:
                examStatusText = 'X';
                break;
        }

        row.innerHTML = `
            <td>${study.modality}</td>
            <td>${study.studydesc}</td>
            <td>${study.studydate}</td>
            <td>${reportStatusText}</td>
            <td>${study.seriescnt}</td>
            <td>${study.imagecnt}</td>
            <td>${examStatusText}</td>
            <input type="hidden" class="pid" value="${study.pid}">
            <input type="hidden" class="pname" value="${study.pname}">
        `;

        const previousId = document.querySelector('.previous-id');
        const previousName = document.querySelector('.previous-name');

        previousId.textContent = `환자 아이디: ${study.pid}`;
        previousName.textContent = `환자 이름: ${study.pname}`;
    });
}

function toggleLoadMoreButton(data) {
    const loadMoreBtn = document.getElementById('loadMoreBtn');
    if (data.number + 1 < data.totalPages) {
        loadMoreBtn.style.display = 'block';
    } else {
        loadMoreBtn.style.display = 'none';
    }
}

function searchStudies(pid, pname, startDate, endDate, page, size, modality, reportStatus) {
    let url = `/search/studies?page=${page}&size=${size}`;
    if (pid) {
        url += `&pid=${pid}`;
    }
    if (pname) {
        url += `&pname=${pname}`;
    }
    if (startDate) {
        url += `&startDate=${startDate}`;
    }
    if (endDate) {
        url += `&endDate=${endDate}`;
    }
    if (modality) {
        url += `&modality=${modality}`;
    }
    if (reportStatus) {
        url += `&reportStatus=${reportStatus}`;
    }
    console.log("url : " + url);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            if (page === 0) clearStudies();
            appendStudies(data.content);
            updateTotalStudiesCount(data.totalElements);
            toggleLoadMoreButton(data);
        })
        .catch(error => {
            console.error('오류 발생:', error);
            alert('데이터를 불러오는 중 오류가 발생했습니다.');
        });
}


function clearStudies() {
    const dataTable = document.getElementById('data-table').getElementsByTagName('tbody')[0];
    dataTable.innerHTML = '';
}

function updateTotalStudiesCount(total) {
    const totalStudiesElement = document.querySelector('.totalStudies');
    totalStudiesElement.textContent = `총 검사 건수 : ${total}`;
}

// 날짜, pid, pname, modality 초기화
function reset(){
    currentPage=0;
    clearStudies();
    updateTotalStudiesCount('');
    currentSearch = { pid: '', pname: '', modality: '' };
    document.querySelector('input[placeholder="환자 아이디"]').value = '';
    document.querySelector('input[placeholder="환자 이름"]').value = '';
    document.getElementById('modalitySelect').value = '';
    document.getElementById('startDate').value = '1990-01-01';
    var today = new Date();
    var formattedToday = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
    document.getElementById('endDate').value = formattedToday;
    document.querySelector('#searchDetail select').value = '';

    // 달력 초기화
    const calendarInstance = flatpickr("#calendar", {
        inline: true,
        mode: "range",
        defaultDate: ["1990-01-01", formattedToday],
        locale: "ko", // 언어 설정
        onReady: function(selectedDates, dateStr, instance) {
            instance.jumpToDate(formattedToday); // 달력을 현재 날짜로 이동
        },
        onChange: function(selectedDates, dateStr, instance) {
            var startDate = selectedDates[0];
            var endDate = selectedDates[1] || startDate;
            $('#startDate').val(instance.formatDate(startDate, "Y-m-d"));
            $('#endDate').val(instance.formatDate(endDate, "Y-m-d"));
        }
    });

    // 강제로 초기화 범위를 설정합니다.
    calendarInstance.setDate(["1990-01-01", formattedToday]);
}

function populateReportSection(study) {
    fetch(`/report/ByStudyKey?studyKey=${study.studykey}`)
        .then(response => {
            if (!response.ok) {
                if (response.status === 404) {
                    return null;  // 리포트가 없으면 null 반환
                } else {
                    throw new Error('서버 응답 오류: ' + response.status);
                }
            }
            return response.json();
        })
        .then(report => {
            if (report) {
                document.querySelector('.reading').value = report.userCode || '';
                document.querySelector('.readingDate').value = report.date || '';
                document.querySelector('.diseaseCode').value = report.diseaseCode || '';
                document.querySelector('.comment').value = report.content || '';
                document.querySelector('.answer').value = report.patient || '';
            } else {
                // 리포트가 없으면 공백으로 설정
                document.querySelector('.reading').value = '';
                document.querySelector('.readingDate').value = '';
                document.querySelector('.diseaseCode').value = '';
                document.querySelector('.comment').value = '';
                document.querySelector('.answer').value = '';
            }
        })
        .catch(error => {
            if (error.message.includes('서버 응답 오류: 404')) {
                // 404 에러는 별도의 처리 없이 무시
                console.log('리포트를 찾을 수 없습니다.');
            } else {
                console.error('오류 발생:', error);
                alert('데이터를 불러오는 중 오류가 발생했습니다.');
            }
        });
}

