<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Worklist</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/worklist.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" type="text/css" href="https://npmcdn.com/flatpickr/dist/themes/dark.css">
</head>
<body>

<div id="page-container">
<jsp:include page="../commons/header.jsp"></jsp:include>
<div id="app">
    <div class="container">
<%--        <header>--%>
<%--            <button id="mainBtn">--%>
<%--                <img src="${pageContext.request.contextPath}/images/logo.png" width="115px" height="60px">--%>
<%--            </button>--%>
<%--        </header>--%>
        <div id="allBox" >
            <div class="aside">
                <div class="Menuwrapper">
                    <button id="mypage">내 정보</button>
                    <button id="toggleSearchDetail">세부검색</button>
                </div>
                <div class="Settingwrapper">
                    <button onclick="window.location='/chat'">채팅</button>
                    <button id="logout">로그아웃</button>
                </div>
            </div>
            <div class="content">

                <!-- 상세 검색용 div -->
                <div id="searchDetail" class="search-detail hidden">
                    <h3>상세 검색</h3>
                    <div>
                        <div id="calendar" class="flatpickr inline"></div>
                    </div>
                    <label for="startDate">시작 날짜:</label>
                    <input type="text" id="startDate">
                    <label for="endDate">종료 날짜:</label>
                    <input type="text" id="endDate">
                    <button id="searchDetailSearch" class="button search-button">조회</button>
                    <button id="searchDetailReset" class="button reset">재설정</button>
                    <div>
                        <label>검사장비</label>
                        <select id="modalitySelect">
                            <option value>선택해주세요</option>
                            <option value="AS">AS</option>
                            <option value="AU">AU</option>
                            <option value="BI">BI</option>
                            <option value="CD">CD</option>
                            <option value="CF">CF</option>
                            <option value="CP">CP</option>
                            <option value="CR">CR</option>
                            <option value="CS">CS</option>
                            <option value="CT">CT</option>
                            <option value="DD">DD</option>
                            <option value="DF">DF</option>
                            <option value="DG">DG</option>
                            <option value="DM">DM</option>
                            <option value="DR">DR</option>
                            <option value="DS">DS</option>
                            <option value="DX">DX</option>
                            <option value="EC">EC</option>
                            <option value="ES">ES</option>
                            <option value="FA">FA</option>
                            <option value="FS">FS</option>
                            <option value="LS">LS</option>
                            <option value="LP">LP</option>
                            <option value="MA">MA</option>
                            <option value="MR">MR</option>
                            <option value="MS">MS</option>
                            <option value="MM">MM</option>
                            <option value="OT">OT</option>
                            <option value="PT">PT</option>
                            <option value="RF">RF</option>
                            <option value="RG">RG</option>
                            <option value="ST">ST</option>
                            <option value="TG">TG</option>
                            <option value="US">US</option>
                            <option value="VF">VF</option>
                            <option value="XA">XA</option>
                        </select>
                    </div>
                </div>
                <div class="mainBox">
                    <div class="search">
                        <h3>검색</h3>
                        <input type="text" placeholder="환자 아이디" id="pid">
                        <input type="text" placeholder="환자 이름" id="pname">
                        <select id="reportStatus">
                            <option value="" disabled selected>판독 상태</option>
                            <option value="">판독상태</option>
                            <option value="0">읽지않음</option>
                            <option value="1">판독취소</option>
                            <option value="2">판독완료</option>
                        </select>
                        <button class="button" id="getAllStudiesBtn">전체</button>
                        <button class="button reset" id="reset">재설정</button>
                        <button class="button search-button" id="searchStudies">검색</button>
                    </div>
                    <div>
                        <span class="totalStudies">총 검사 건수 : </span>
                        <div class="combo-box">
                            <select id="pageSizeSelect">
                                <option value="5">5개씩 보기</option>
                                <option value="10">10개씩 보기</option>
                                <option value="20">20개씩 보기</option>
                                <option value="50">50개씩 보기</option>
                                <option value="100">100개씩 보기</option>
                            </select>
                        </div>
                    </div>

                    <main class="main-content">
                        <div class="all-info">
                            <table class="data-table" id="data-table">
                                <thead class="table-header">
                                <tr>
                                    <th>환자 아이디</th>
                                    <th>환자 이름</th>
                                    <th>검사장비</th>
                                    <th>검사설명</th>
                                    <th>ai score</th>
                                    <th>ai 판독</th>
                                    <th>검사일시</th>
                                    <th>판독상태</th>
                                    <th>시리즈</th>
                                    <th>이미지</th>
                                    <th>동의서</th>
                                </tr>
                                </thead>
                                <tbody class="contentBody">
                                <!-- Add more rows as necessary -->
                                </tbody>
                            </table>
                            <div class="button-set">
                                <button class="loadMoreBtn" id="loadMoreBtn" style="display: none">더보기</button>
                            </div>
                        </div>

                        <div class="box">
                            <div class="previous">
                                <h3>Previous</h3>
                                <div class="info-box">
                                    <p class="previous-id">환자 아이디: </p>
                                    <p class="previous-name">환자 이름: </p>
                                </div>
                                <table class="previous-table" id="previous-table">
                                    <thead class="previous-head">
                                    <tr>
                                        <th>검사장비</th>
                                        <th>검사설명</th>
                                        <th>검사일시</th>
                                        <th>판독상태</th>
                                        <th>시리즈</th>
                                        <th>이미지</th>
                                        <th>동의서</th>
                                    </tr>
                                    </thead>
                                    <tbody class="previous-body">
                                    <!-- Add rows here -->
                                    </tbody>
                                </table>
                            </div>
                            <div class="report">
                                <div class="report-title">
                                    <h2>Report</h2>
                                </div>
                                <form id="reportForm" method="post">
                                    <div class="report-box">
                                        <input type="hidden" id="studyKey" value="" style="display: none"/>
                                        <div class="write-box">
                                            <h3>의사소견</h3>
                                            <textarea class="comment" placeholder="의사소견"></textarea>
                                            <h3>결론</h3>
                                            <textarea class="patient" placeholder="결론"></textarea>
                                        </div>
                                        <div class="input-box">
                                            <div>
                                                <label for="userCode">판독의</label>
                                                <input type="text" id="userCode" class="userCode" readonly/>
                                            </div>
                                            <div>
                                                <label for="date">판독 일시</label>
                                                <input type="text" id="date" class="date" placeholder="리포트 생성 시 자동 추가" readonly/>
                                            </div>
                                            <div>
                                                <label>질병 코드:</label>
                                                <input type="text" class="diseaseCode" id="diseaseCode"/>
                                                <button type="button" id="searchICDButton">ICD 코드 검색</button>
                                                <div id="icdResults"></div> <!-- 검색 결과 표시 -->
                                            </div>
                                            <div class="form-group">
                                                <label for="videoReplay">판독 상태</label>
                                                <select id="videoReplay">
                                                    <option value="" selected disabled>판독 상태</option>
                                                    <option value="읽지않음">읽지않음</option>
                                                    <option value="판독완료">판독완료</option>
                                                    <option value="판독취소">판독취소</option>
                                                </select>
                                            </div>
                                            <div class="button-container">
                                                <button class="reportCreate" type="submit">리포트 생성</button>
                                                <button class="reportUpdate" type="submit">리포트 수정</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/worklist.js"></script>

</div>
</body>
</html>
