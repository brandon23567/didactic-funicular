<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Request Error</title>
            <link rel="stylesheet" href="${ctx}/css/style.css">
        </head>

        <body>
            <div class="error-page">
                <div class="error-card">
                    <div class="error-code">!</div>
                    <h1>Request error</h1>
                    <c:if test="${not empty errorMessage}">
                        <div class="error-detail">
                            <strong>Details:</strong> ${errorMessage}
                        </div>
                    </c:if>
                    <div class="error-actions">
                        <a href="javascript:history.back()" class="btn btn-primary">Go Back</a>
                        <a href="${ctx}/" class="btn">Go Home</a>
                    </div>
                </div>
            </div>
        </body>

        </html>