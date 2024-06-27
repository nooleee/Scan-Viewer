let currentPage = 0;
const pageSize = 5;

document.getElementById('loadMoreBtn').addEventListener('click', function() {
    fetchStudies(currentPage, pageSize);
    currentPage++;
});

document.getElementById('getAllStudiesBtn').addEventListener('click', function() {
    if(currentPage === 0){
        fetchStudies(currentPage, pageSize);
        currentPage++;
    }
});

function fetchStudies(page, size) {
    fetch(`/worklistAllSearch?page=${page}&size=${size}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류: ' + response.status);
            }
            const contentType = response.headers.get('Content-Type');
            if (contentType && contentType.includes('application/json')) {
                return response.json();
            } else {
                throw new Error('서버에서 올바른 형식의 데이터를 반환하지 않았습니다.');
            }
        })
        .then(data => {
            appendStudies(data.content); // 기존 데이터를 지우지 않고 추가 데이터를 테이블에 추가
            toggleLoadMoreButton(data);
        })
        .catch(error => {
            console.error('오류 발생:', error);
            alert('데이터를 불러오는 중 오류가 발생했습니다.');
        });
}

function appendStudies(studies) {
    const dataTable = document.getElementById('data-table').getElementsByTagName('tbody')[0];

    studies.forEach(study => {
        const row = dataTable.insertRow();

        let reportStatusText = '';
        switch (study.reportstatus) {
            case 6:
                reportStatusText = '판독';
                break;
            case 5:
                reportStatusText = '예비판독';
                break;
            case 3:
                reportStatusText = '읽지않음';
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
            <td>${study.examstatus}</td>
            <input type="hidden" class="studykey" value="${study.studykey}">
        `;
    });
}

document.addEventListener('DOMContentLoaded', function () {
    // fetchStudies(currentPage, pageSize);

    const table = document.getElementById('data-table').getElementsByTagName('tbody')[0];

    table.addEventListener('click', function(event) {
        console.log("click이벤트 발생")
        const targetRow = event.target.closest('tr');

        const pid = targetRow.querySelector('td:nth-child(1)').textContent;
        fetchStudiesByPid(pid);
    });

    table.addEventListener('dblclick', function (event) {
        const targetRow = event.target.closest('tr');
        if (targetRow) {
            const studyKey = targetRow.querySelector('.studykey').value;
            const url = `/path/to/detailStudy?studykey=${encodeURIComponent(studyKey)}`;
            window.location.href = url;
        }
    });
});0

function fetchStudiesByPid(pid) {
    fetch(`/worklistPrevious/${pid}`)
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
            case 6:
                reportStatusText = '판독';
                break;
            case 5:
                reportStatusText = '예비판독';
                break;
            case 3:
                reportStatusText = '읽지않음';
                break;
        }

        row.innerHTML = `
            <td>${study.modality}</td>
            <td>${study.studydesc}</td>
            <td>${study.studydate}</td>
            <td>${reportStatusText}</td>
            <td>${study.seriescnt}</td>
            <td>${study.imagecnt}</td>
            <td>${study.examstatus}</td>
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

function loadAllStudies() {
    fetch('/worklistAllSearch')
        .then(response => response.json())
        .then(data => {
            document.getElementById('studyRows').innerHTML = '';
            appendStudies(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}
