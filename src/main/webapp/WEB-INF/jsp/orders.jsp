<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Order History — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <h1>Order History</h1>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty orders}">
                            <div class="empty-state">
                                <h2>No orders yet</h2>
                                <p>When you place an order, it'll appear here.</p>
                                <a href="${ctx}/products" class="btn btn-primary">Browse Products</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="orders-table">
                                <thead>
                                    <tr>
                                        <th>Order #</th>
                                        <th>Date</th>
                                        <th>Status</th>
                                        <th>Total</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="o" items="${orders}">
                                        <tr>
                                            <td>#${o.id}</td>
                                            <td>${o.createdAt}</td>
                                            <td>
                                                <span class="status-badge">${o.status}</span>
                                            </td>
                                            <td>$
                                                <fmt:formatNumber value="${o.totalAmount}" pattern="#,##0.00" />
                                            </td>
                                            <td>
                                                <a href="${ctx}/orders/${o.id}" class="btn btn-sm">View Details</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>