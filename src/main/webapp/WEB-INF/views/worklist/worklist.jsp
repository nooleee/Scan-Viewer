<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Worklist</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/worklist.css">
</head>
<body>
<div class="container">
    <div class="content">
        <div class="search">
            <h3>검색</h3>
            <input type="text" placeholder="환자 아이디">
            <input type="text" placeholder="환자 이름">
            <select>
                <option disabled selected>판독 상태</option>
                <option>판독 상태</option>
                <option>읽지않음</option>
                <option>판독취소</option>
                <option>판독</option>
            </select>
            <button class="button" id="getAllStudiesBtn">전체</button>
            <button>1일</button>
            <button>3일</button>
            <button>1주일</button>
            <button class="button">재설정</button>
            <button class="button search-button" id="searchStudies">검색</button>
        </div>
        <div>
            <span class="totalStudies">총 검사 건수 : </span>
            <div>
                <button class="download">다운로드</button>
                <button class="deleteStudy">검사삭제</button>
                <select>
                    <option selected>10개씩보기</option>
                </select>
            </div>
        </div>


        <main class="main-content">
            <div class="all-info">
                <table class="data-table" id="data-table">
                    <thead>
                    <tr>
                        <th>환자 아이디</th>
                        <th>환자 이름</th>
                        <th>검사장비</th>
                        <th>검사설명</th>
                        <th>검사일시</th>
                        <th>판독상태</th>
                        <th>시리즈</th>
                        <th>이미지</th>
                        <th>Verify</th>
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
                        <thead>
                        <tr>
                            <th>검사장비</th>
                            <th>검사설명</th>
                            <th>검사일시</th>
                            <th>판독상태</th>
                            <th>시리즈</th>
                            <th>이미지</th>
                            <th>Verify</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <div class="report">
                    <div class="report-title">
                        <h2>Report</h2>
                        <button class="button">판독 지우기</button>
                    </div>
                    <div class="report-box">
                        <div class="write-box">
                            <textarea class="comment" placeholder="코멘트"></textarea>
                            <textarea class="quest" placeholder="탐색"></textarea>
                        </div>
                        <div class="input-box">
                            <div>판독의</div>
                            <div>
                                <p class="reading" id="reading"></p>
                            </div>
                            <div class="reading-box">
                                <button class="button blue-button">판독</button>
                                <button class="button">판독취소</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/worklist.js"></script>

</body>
</html>
