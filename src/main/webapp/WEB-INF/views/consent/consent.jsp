<%--
  Created by IntelliJ IDEA.
  User: ryuuki
  Date: 2024. 6. 25.
  Time: 오후 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consent Form</title>
</head>
<body>
<h2>Consent Form</h2>
<form action="/consent/submit" method="post">
    <div>
        <input type="hidden" id="studyKey" name="studyKey" value="${studyKey}">
    </div>
    <div>
        <label for="userCode">User Code:</label>
        <input type="text" id="userCode" name="userCode" value="${sessionScope.user.userCode}" readonly>
    </div>
    <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${sessionScope.user.name}" readonly>
    </div>
    <div>
        <label for="birth">Birth:</label>
        <input type="date" id="birth" name="birth"value="${sessionScope.user.birth}"  readonly>
    </div>
    <div>
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${sessionScope.user.phone}" readonly>
    </div>
    <div>
        <label for="position">Position:</label>
        <input type="text" id="position" name="position" required>
    </div>
    <div>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
    </div>
    <div>
        <label for="department">Department:</label>
        <input type="text" id="department" name="department" required>
    </div>
    <div>
        <label for="user">User:</label>
        <input type="text" id="user" name="user" required>
    </div>
    <div>
        <label for="detail">Detail:</label>
        <textarea id="detail" name="detail" required></textarea>
    </div>
    <div>
        <label for="purpose">Purpose:</label>
        <textarea id="purpose" name="purpose" required></textarea>
    </div>
    <div>
        <label for="period">Period:</label>
        <input type="text" id="period" name="period" required>
    </div>
    <div>
        <button type="submit">Submit</button>
    </div>
</form>
</body>
</html>
