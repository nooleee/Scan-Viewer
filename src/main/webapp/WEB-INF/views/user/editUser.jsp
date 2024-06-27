<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 27.
  Time: 오후 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit User</title>
</head>
<body>
<h2>Edit User</h2>
<form action="/user/update" method="post">
  <div>
    <label for="userCode">User Code:</label>
    <input type="text" id="userCode" name="userCode" value="${user.userCode}" readonly>
  </div>
  <div>
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="${user.name}" readonly>
  </div>
  <div>
    <label for="group">Group:</label>
    <select id="group" name="group">
      <option value="Admin" ${user.group == 'Admin' ? 'selected' : ''}>Admin</option>
      <option value="Radiologist" ${user.group == 'Radiologist' ? 'selected' : ''}>Radiologist</option>
      <option value="Technician" ${user.group == 'Technician' ? 'selected' : ''}>Technician</option>
      <option value="Doctor" ${user.group == 'Doctor' ? 'selected' : ''}>Doctor</option>
    </select>
  </div>
  <div>
    <button type="submit">Update</button>
  </div>
</form>
</body>
</html>

