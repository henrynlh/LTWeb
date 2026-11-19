<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your cart</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
    <h1>Your cart</h1>
    <c:if test="${not empty message}"><p class="message" role="alert"><c:out value="${message}" /></p></c:if>
    <c:url var="cartUrl" value="/cart" />
    <c:choose>
        <c:when test="${empty items}"><p>Your cart is empty.</p></c:when>
        <c:otherwise>
            <div class="table-scroll">
            <table>
                <thead><tr><th scope="col">Quantity</th><th scope="col">Description</th><th scope="col" class="number">Price</th><th scope="col" class="number">Amount</th><th scope="col"><span class="sr-only">Remove</span></th></tr></thead>
                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td class="quantity-cell">
                            <form action="${cartUrl}" method="post" class="quantity-form">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productCode" value="${item.product.code}">
                                <label for="quantity-${item.product.code}" class="sr-only">Quantity for <c:out value="${item.product.description}" /></label>
                                <input type="number" id="quantity-${item.product.code}" name="quantity" value="${item.quantity}" min="1" max="999" required>
                                <input type="submit" value="Update">
                            </form>
                        </td>
                        <td><c:out value="${item.product.description}" /></td>
                        <td class="number"><c:out value="${item.product.priceCurrencyFormat}" /></td>
                        <td class="number"><c:out value="${item.totalCurrencyFormat}" /></td>
                        <td class="actions">
                            <form action="${cartUrl}" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="productCode" value="${item.product.code}">
                                <input type="submit" value="Remove Item">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot><tr><th colspan="3" scope="row" class="number">Total</th><td class="number"><c:out value="${cartTotal}" /></td><td></td></tr></tfoot>
            </table>
            </div>
            <p><strong>To change the quantity</strong>, enter the new quantity and click on the Update button.</p>
        </c:otherwise>
    </c:choose>
    <c:url var="catalogUrl" value="/" />
    <form action="${catalogUrl}" method="get" class="navigation"><input type="submit" value="Continue Shopping"></form>
    <c:if test="${not empty items}">
        <form action="${cartUrl}" method="get" class="navigation">
            <input type="hidden" name="action" value="checkout">
            <input type="submit" value="Checkout">
        </form>
    </c:if>
</body>
</html>
