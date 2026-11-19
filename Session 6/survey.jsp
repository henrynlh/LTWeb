<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Survey results</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body class="thank-page">
    <main class="result-card">
        <p class="result-eyebrow">SURVEY COMPLETED</p>
        <h1>Thanks for taking our survey!</h1>
        <p class="result-intro">Here is the information that you entered:</p>

        <dl class="result-details">
            <div><dt>Email:</dt><dd><c:out value="${user.email}" /></dd></div>
            <div><dt>First Name:</dt><dd><c:out value="${user.firstName}" /></dd></div>
            <div><dt>Last Name:</dt><dd><c:out value="${user.lastName}" /></dd></div>
            <div><dt>Heard From:</dt><dd><c:out value="${user.heardFrom}" /></dd></div>
            <div><dt>Updates:</dt><dd><c:out value="${user.updates}" /></dd></div>
            <c:if test="${user.updates eq 'Yes'}">
                <div><dt>Contact Via:</dt><dd><c:out value="${user.contactVia}" /></dd></div>
            </c:if>
        </dl>

        <div class="result-actions">
            <p>Want to submit another response?</p>
            <form action="index.html" method="get">
                <button type="submit">Return to survey <span aria-hidden="true">&rarr;</span></button>
            </form>
        </div>
    </main>
</body>
</html>
