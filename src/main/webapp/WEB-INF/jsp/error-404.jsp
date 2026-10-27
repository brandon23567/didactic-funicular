<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>404 — Page Not Found</title>
            <link rel="stylesheet" href="${ctx}/css/style.css">
        </head>

        <body>
            <div class="error-page">
                <div class="error-card">
                    <div class="error-code">404</div>
                    <h1>Page not found</h1>
                    <p>We couldn't find what you were looking for.</p>
                    <c:if test="${not empty requestedPath}">
                        <p class="requested-path">Requested: <code>${requestedPath}</code></p>
                    </c:if>
                    <div class="error-actions">
                        <a href="${ctx}/" class="btn btn-primary">Go Home</a>
                        <a href="${ctx}/products" class="btn">Browse Products</a>
                    </div>
                </div>
            </div>
        </body>

        </html>