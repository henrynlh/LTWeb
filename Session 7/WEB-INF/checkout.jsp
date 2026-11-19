<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Checkout</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
    <h1>Checkout</h1>
    <c:choose>
        <c:when test="${empty items}"><p>Your cart is empty.</p></c:when>
        <c:otherwise>
            <p>Review your items below.</p>
            <div class="table-scroll">
            <table>
                <thead><tr><th scope="col">Quantity</th><th scope="col">Description</th><th scope="col" class="number">Amount</th></tr></thead>
                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr><td><c:out value="${item.quantity}" /></td><td><c:out value="${item.product.description}" /></td><td class="number"><c:out value="${item.totalCurrencyFormat}" /></td></tr>
                </c:forEach>
                </tbody>
                <tfoot><tr><th colspan="2" scope="row" class="number">Total</th><td class="number"><c:out value="${cartTotal}" /></td></tr></tfoot>
            </table>
            </div>
            <p>This classroom example displays an order summary. No payment or order is submitted.</p>
        </c:otherwise>
    </c:choose>
    <c:url var="cartUrl" value="/cart" />
    <form action="${cartUrl}" method="get"><input type="submit" value="Return to cart"></form>
</body>
</html>
