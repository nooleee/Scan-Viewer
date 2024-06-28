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
    <title>Title</title>
</head>
<body>
<h1>DICOM Viewer</h1>
<div id="content">
    <div id="dicomViewport" style="width: 512px; height: 512px;"></div>
</div>
<%--<input id ="file" type="file" accept="application/dicom">--%>
<script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</body>
</html>

