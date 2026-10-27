<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <header>
            <div class="header-inner">
                <a href="${ctx}/" class="brand">ShopSphere</a>
                <nav>
                    <a href="${ctx}/products">Products</a>
                    <a href="${ctx}/cart" class="cart-link">
                        Cart
                        <c:if test="${cartCount > 0}">
                            <span class="cart-badge">${cartCount}</span>
                        </c:if>
                    </a>
                    <a href="${ctx}/orders">Orders</a>
                    <c:choose>
                        <c:when test="${not empty username}">
                            <span class="user-pill">Hi, <strong>${username}</strong></span>
                            <a href="${ctx}/auth/logout" class="nav-link danger">Sign out</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/auth/login" class="nav-link">Sign in</a>
                            <a href="${ctx}/auth/signup" class="nav-link primary">Sign up</a>
                        </c:otherwise>
                    </c:choose>
                </nav>
            </div>
        </header>