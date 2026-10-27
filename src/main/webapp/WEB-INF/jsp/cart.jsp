<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Cart — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <h1>Your Cart</h1>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty cartItems}">
                            <div class="empty-state">
                                <h2>Your cart is empty</h2>
                                <p>Browse our products and add some items!</p>
                                <a href="${ctx}/products" class="btn btn-primary">Browse Products</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="cart-table">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Subtotal</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${cartItems}">
                                        <tr>
                                            <td class="cart-product">
                                                <img src="${item.product.imageUrl}" alt="${item.product.name}"
                                                    onerror="this.src='https://placehold.co/100x100?text=No+Image'">
                                                <a href="${ctx}/products/${item.product.id}">${item.product.name}</a>
                                            </td>
                                            <td>$
                                                <fmt:formatNumber value="${item.product.price}" pattern="#,##0.00" />
                                            </td>
                                            <td>
                                                <form action="${ctx}/cart/update" method="post" class="qty-form">
                                                    <input type="hidden" name="productId" value="${item.product.id}">
                                                    <input type="number" name="quantity" value="${item.quantity}"
                                                        min="1" max="${item.product.stock}">
                                                    <button type="submit" class="btn btn-sm">Update</button>
                                                </form>
                                            </td>
                                            <td class="subtotal">
                                                $
                                                <fmt:formatNumber value="${item.product.price * item.quantity}"
                                                    pattern="#,##0.00" />
                                            </td>
                                            <td>
                                                <form action="${ctx}/cart/remove" method="post" class="inline-form">
                                                    <input type="hidden" name="productId" value="${item.product.id}">
                                                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="3" class="cart-total-label">Subtotal (${itemCount} items):</td>
                                        <td class="cart-total" colspan="2">
                                            $
                                            <fmt:formatNumber value="${subtotal}" pattern="#,##0.00" />
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>

                            <div class="cart-actions">
                                <form action="${ctx}/cart/clear" method="post" class="inline-form">
                                    <button type="submit" class="btn">Clear Cart</button>
                                </form>
                                <a href="${ctx}/products" class="btn">Continue Shopping</a>
                                <a href="${ctx}/orders/checkout" class="btn btn-primary">Proceed to Checkout</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>