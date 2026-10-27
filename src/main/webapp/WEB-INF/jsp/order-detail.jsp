<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Order #${order.id} — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <a href="${ctx}/orders" class="back-link">&larr; Back to orders</a>

                    <div class="order-header">
                        <h1>Order #${order.id}</h1>
                        <span class="status-badge status-${order.status}">${order.status}</span>
                    </div>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <div class="order-meta">
                        <div class="meta-row">
                            <span class="meta-label">Order Date:</span>
                            <span>${order.createdAt}</span>
                        </div>
                        <div class="meta-row">
                            <span class="meta-label">Shipping Address:</span>
                            <span>${order.shippingAddress}</span>
                        </div>
                        <div class="meta-row">
                            <span class="meta-label">Customer:</span>
                            <span>${order.user.username}</span>
                        </div>
                    </div>

                    <table class="order-items-table">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Unit Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.items}">
                                <tr>
                                    <td>
                                        <a href="${ctx}/products/${item.product.id}">${item.product.name}</a>
                                    </td>
                                    <td>$
                                        <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00" />
                                    </td>
                                    <td>${item.quantity}</td>
                                    <td>$
                                        <fmt:formatNumber value="${item.unitPrice * item.quantity}"
                                            pattern="#,##0.00" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="cart-total-label">Total:</td>
                                <td class="cart-total">
                                    $
                                    <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                                </td>
                            </tr>
                        </tfoot>
                    </table>

                    <div class="cart-actions">
                        <a href="${ctx}/products" class="btn">Continue Shopping</a>
                    </div>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>