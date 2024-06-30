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
</head>
<body>
<h1>DICOM Viewer</h1>
<div class="toolbar">
    <!-- 툴바에 필요한 버튼들 추가 -->
    <button onclick="toolAction('Zoom')">Zoom</button>
    <button onclick="toolAction('Pan')">Pan</button>
    <button onclick="toolAction('Length')">Length</button>
    <button onclick="toolAction('Angle')">Angle</button>
    <button onclick="toolAction('StackScroll')">Stack Scroll</button>
</div>
<div id="content">
    <div id="dicomViewport" class ="viewport"></div>
<%--    <script src="${pageContext.request.contextPath}/js/cornerstone.min.js"></script>--%>
<%--    <script src="${pageContext.request.contextPath}/js/cornerstoneTools.min.js"></script>--%>
<%--    <script src="${pageContext.request.contextPath}/js/cornerstoneWADOImageLoader.min.js"></script>--%>
<%--    <script src="${pageContext.request.contextPath}/js/viewer.js"></script>--%>

</div>
<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>

