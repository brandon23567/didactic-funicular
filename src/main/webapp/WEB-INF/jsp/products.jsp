<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Products — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <div class="page-header">
                        <h1>All Products</h1>
                        <form action="${ctx}/products" method="get" class="search-form">
                            <input type="text" name="q" value="${searchQuery}" placeholder="Search products...">
                            <button type="submit" class="btn btn-sm">Search</button>
                        </form>
                    </div>

                    <c:choose>
                        <c:when test="${empty products}">
                            <div class="empty-state">
                                <h2>No products found</h2>
                                <p>Try a different search term.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="product-grid">
                                <c:forEach var="p" items="${products}">
                                    <a href="${ctx}/products/${p.id}" class="product-card">
                                        <div class="product-image">
                                            <img src="${p.imageUrl}" alt="${p.name}" loading="lazy"
                                                onerror="this.src='https://placehold.co/400x300?text=No+Image'">
                                        </div>
                                        <div class="product-info">
                                            <h3>${p.name}</h3>
                                            <p class="description">${p.description}</p>
                                            <p class="price">$
                                                <fmt:formatNumber value="${p.price}" pattern="#,##0.00" />
                                            </p>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>