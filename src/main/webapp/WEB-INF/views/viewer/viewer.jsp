<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/viewer.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>DICOM Viewer</h1>
<div id ="toolbar" class="toolbar">
    <button id="report">Report</button>
    <button id="backButton">Worklist</button>
    <button id="toggleThumbnails">Toggle Thumbnails</button>
    <button class="tools" id="Zoom">Zoom</button>
    <button class="tools" id="Pan">Pan</button>
    <button class="tools" id="Length">Length</button>
    <button class="tools" id="Angle">Angle</button>
    <button class="tools" id="Magnify">Magnify</button>
    <button class="tools" id="StackScroll">Stack Scroll</button>
    <button class="tools" id="WindowLevel">Window Level</button>
    <button id="layoutButton">Layout</button>
</div>
<div id="gridModal" class="modal">
    <div class="modal-content">
<%--        <span class="close">&times;</span>--%>
        <div id="grid-container" class="grid-container" style="grid-template-columns: repeat(5, 1fr);">
            <c:forEach begin="1" end="5" step="1" var="i">
                <c:forEach begin="1" end="5" step="1" var="j" >
                    <div class="grid-option" data-row="${i}" data-col="${j}">
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </div>
</div>
<div id="mainContent">
    <div id="thumbnails" class="thumbnails">
        <c:forEach var="series" items="${seriesList}">
            <div class="thumbnail-viewport" data-series-key="${series.seriesKey}">
                <div id="thumbnail-${series.seriesKey}" class="thumbnail-element" style="width: 150px; height: 150px;"></div>
                <p>${series.description}</p>
            </div>
        </c:forEach>
    </div>
    <div id="dicomViewport1" class="viewport"></div>
</div>

<!-- 모달창 HTML -->
<div id="reportModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <div id="reportContainer" class="container">
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>
