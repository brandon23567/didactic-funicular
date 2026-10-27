<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${product.name} — ShopSphere</title>
                <link rel="stylesheet" href="${ctx}/css/style.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/_header.jsp" />

                <main class="container">
                    <a href="${ctx}/products" class="back-link">&larr; Back to products</a>

                    <div class="product-detail">
                        <div class="product-image-large">
                            <img src="${product.imageUrl}" alt="${product.name}"
                                onerror="this.src='https://placehold.co/600x450?text=No+Image'">
                        </div>
                        <div class="product-info-large">
                            <h1>${product.name}</h1>

                            <div class="rating-summary">
                                <c:choose>
                                    <c:when test="${avgRating != null}">
                                        <span class="stars">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= avgRating}">&#9733;</c:when>
                                                    <c:otherwise>&#9734;</c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </span>
                                        <span class="rating-text">
                                            <fmt:formatNumber value="${avgRating}" maxFractionDigits="1" /> / 5
                                            (${reviewCount} review${reviewCount != 1 ? 's' : ''})
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="rating-text">No reviews yet</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <p class="price-large">$
                                <fmt:formatNumber value="${product.price}" pattern="#,##0.00" />
                            </p>

                            <p class="stock">
                                <c:choose>
                                    <c:when test="${product.stock > 0}">
                                        <span class="in-stock">In stock (${product.stock} available)</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="out-of-stock">Out of stock</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>

                            <p class="description">${product.description}</p>

                            <c:if test="${product.stock > 0}">
                                <form action="${ctx}/cart" method="post" class="add-to-cart-form">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <label>
                                        Qty:
                                        <input type="number" name="quantity" value="1" min="1" max="${product.stock}">
                                    </label>
                                    <button type="submit" class="btn btn-primary">Add to Cart</button>
                                </form>
                            </c:if>
                        </div>
                    </div>

                    <section class="reviews">
                        <h2>Reviews</h2>

                        <c:if test="${not empty error}">
                            <div class="alert alert-error">${error}</div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div class="alert alert-success">${success}</div>
                        </c:if>

                        <c:choose>
                            <c:when test="${empty product.reviews}">
                                <p class="empty-state">No reviews yet. Be the first!</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="r" items="${product.reviews}">
                                    <div class="review">
                                        <div class="review-header">
                                            <strong>${r.user.username}</strong>
                                            <span class="stars">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i <= r.rating}">&#9733;</c:when>
                                                        <c:otherwise>&#9734;</c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </span>
                                            <c:if test="${username == r.user.username or isAdmin}">
                                                <form action="${ctx}/products/${product.id}/reviews/${r.id}/delete"
                                                    method="post" class="inline-form">
                                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                </form>
                                            </c:if>
                                        </div>
                                        <p class="review-comment">${r.comment}</p>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${isLoggedIn}">
                                <div class="review-form">
                                    <h3>Write a Review</h3>
                                    <form action="${ctx}/products/${product.id}/reviews" method="post">
                                        <div class="form-group">
                                            <label>Rating:</label>
                                            <select name="rating" required>
                                                <option value="">Select...</option>
                                                <option value="5">5 stars</option>
                                                <option value="4">4 stars</option>
                                                <option value="3">3 stars</option>
                                                <option value="2">2 stars</option>
                                                <option value="1">1 star</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Comment (optional):</label>
                                            <textarea name="comment" rows="3" maxlength="1000"
                                                placeholder="Share your thoughts..."></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Post Review</button>
                                    </form>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="login-prompt">
                                    <a href="${ctx}/auth/login">Sign in</a> to write a review.
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </main>

                <jsp:include page="/WEB-INF/jsp/_footer.jsp" />
            </body>

            </html>