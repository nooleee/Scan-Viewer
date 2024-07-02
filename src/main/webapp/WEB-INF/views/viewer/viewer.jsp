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
    <title>Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/viewer.css">
</head>
<body>
<h1>DICOM Viewer</h1>
<div class="toolbar">
    <button id="backButton">Worklist</button>
    <!-- 툴바에 필요한 버튼들 추가 -->
    <button id="toggleThumbnails">Toggle Thumbnails</button>
    <button id="zoomTool">Zoom</button>
    <button id="panTool">Pan</button>
    <button id="lengthTool">Length</button>
    <button id="angleTool">Angle</button>
    <button id="magnifyTool">Magnify</button>
    <button id="stackScrollTool">Stack Scroll</button>
</div>
<div id="mainContent">
    <div id="thumbnails" class="hidden">
        <c:forEach var="series" items="${seriesList}">
            <div class="thumbnail-viewport" data-series-index="${series.index}">
                <div class="thumbnail-element" id="thumbnail-${series.index}" style="width: 100px; height: 100px;"></div>
                <p>${series.description}</p>
            </div>
        </c:forEach>
    </div>
    <div id="dicomViewport" class="viewport"></div>
</div>
<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>
