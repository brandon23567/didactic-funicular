<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>ShopSphere — Quality Tech Products</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <section class="hero">
                        <div class="hero-text">
                            <h1>Tech that moves you forward.</h1>
                            <p>Premium peripherals, monitors, and accessories. Curated for makers, by makers.</p>
                            <a href="${ctx}/products" class="btn btn-primary">Browse Products</a>
                        </div>
                    </section>

                    <section class="featured">
                        <h2>Featured Products</h2>
                        <div class="product-grid">
                            <c:forEach var="p" items="${recentProducts}" varStatus="loop">
                                <c:if test="${loop.count <= 4}">
                                    <a href="${ctx}/products/${p.id}" class="product-card">
                                        <div class="product-image">
                                            <img src="${p.imageUrl}" alt="${p.name}" loading="lazy"
                                                onerror="this.src='https://placehold.co/400x300?text=No+Image'">
                                        </div>
                                        <div class="product-info">
                                            <h3>${p.name}</h3>
                                            <p class="price">$
                                                <fmt:formatNumber value="${p.price}" pattern="#,##0.00" />
                                            </p>
                                        </div>
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </section>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>