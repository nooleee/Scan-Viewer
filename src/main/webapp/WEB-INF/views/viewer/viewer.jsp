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
    <button id="report">Report</button>
    <button id="backButton">Worklist</button>
    <button id="toggleThumbnails">Toggle Thumbnails</button>
    <button id="layoutButton">Layout</button>
    <button id="zoomTool">Zoom</button>
    <button id="panTool">Pan</button>
    <button id="lengthTool">Length</button>
    <button id="angleTool">Angle</button>
    <button id="magnifyTool">Magnify</button>
    <button id="stackScrollTool">Stack Scroll</button>
    <div id="layoutMenu" class="layout-menu hidden">
        <button id="layoutOne">1 Viewport</button>
        <button id="layoutTwo">2 Viewports</button>
        <button id="layoutFour">4 Viewports</button>
    </div>
</div>
<div id="mainContent">
    <div id="thumbnails" class="thumbnails">
        <c:forEach var="series" items="${seriesList}">
            <div class="thumbnail-viewport" data-series-index="${series.index}">
                <div id="thumbnail-${series.index}" class="thumbnail-element" style="width: 150px; height: 150px;"></div>
                <p>${series.index}</p>
            </div>
        </c:forEach>
    </div>
    <div id="dicomViewport1" class="viewport"></div>
</div>
<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>
