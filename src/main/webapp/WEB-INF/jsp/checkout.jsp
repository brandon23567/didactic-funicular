<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Checkout — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container narrow">
                    <h1>Checkout</h1>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${cartCount == 0}">
                            <div class="empty-state">
                                <h2>Your cart is empty</h2>
                                <p>Add some products before checking out.</p>
                                <a href="${ctx}/products" class="btn btn-primary">Browse Products</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="checkout-card">
                                <form action="${ctx}/orders/checkout" method="post">
                                    <div class="form-group">
                                        <label for="shippingAddress">Shipping Address</label>
                                        <textarea id="shippingAddress" name="shippingAddress" rows="4"
                                            placeholder="123 Main St, City, State, ZIP" required></textarea>
                                    </div>

                                    <div class="checkout-summary">
                                        <div class="summary-row">
                                            <span>Items in cart:</span>
                                            <strong>${cartCount}</strong>
                                        </div>
                                    </div>

                                    <div class="checkout-actions">
                                        <a href="${ctx}/cart" class="btn">Back to Cart</a>
                                        <button type="submit" class="btn btn-primary">Place Order</button>
                                    </div>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>