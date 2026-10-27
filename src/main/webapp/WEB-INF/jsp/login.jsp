<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Sign In — E-Commerce App</title>
            <style>
                * {
                    box-sizing: border-box;
                    margin: 0;
                    padding: 0;
                }

                body {
                    font-family: 'Segoe UI', Tahoma, sans-serif;
                    background: #f5f5f7;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    padding: 1rem;
                }

                .card {
                    background: #fff;
                    padding: 2.5rem;
                    border-radius: 12px;
                    box-shadow: 0 4px 24px rgba(0, 0, 0, 0.08);
                    width: 100%;
                    max-width: 400px;
                }

                h1 {
                    color: #1d1d1f;
                    margin-bottom: 0.5rem;
                    font-size: 1.6rem;
                }

                .subtitle {
                    color: #6e6e73;
                    margin-bottom: 1.5rem;
                    font-size: 0.9rem;
                }

                .form-group {
                    margin-bottom: 1.2rem;
                }

                label {
                    display: block;
                    color: #1d1d1f;
                    font-size: 0.85rem;
                    margin-bottom: 0.4rem;
                    font-weight: 500;
                }

                input[type="text"],
                input[type="password"] {
                    width: 100%;
                    padding: 0.7rem 0.9rem;
                    border: 1px solid #d2d2d7;
                    border-radius: 8px;
                    font-size: 0.95rem;
                    transition: border-color 0.15s;
                }

                input[type="text"]:focus,
                input[type="password"]:focus {
                    outline: none;
                    border-color: #0071e3;
                }

                .btn {
                    width: 100%;
                    padding: 0.8rem;
                    background: #0071e3;
                    color: #fff;
                    border: none;
                    border-radius: 8px;
                    font-size: 1rem;
                    font-weight: 500;
                    cursor: pointer;
                    transition: background 0.15s;
                }

                .btn:hover {
                    background: #0058b9;
                }

                .error {
                    background: #fef2f2;
                    color: #b91c1c;
                    padding: 0.7rem 0.9rem;
                    border-radius: 8px;
                    margin-bottom: 1rem;
                    font-size: 0.9rem;
                    border: 1px solid #fecaca;
                }

                .success {
                    background: #f0fdf4;
                    color: #166534;
                    padding: 0.7rem 0.9rem;
                    border-radius: 8px;
                    margin-bottom: 1rem;
                    font-size: 0.9rem;
                    border: 1px solid #bbf7d0;
                }

                .footer-link {
                    text-align: center;
                    margin-top: 1.5rem;
                    color: #6e6e73;
                    font-size: 0.9rem;
                }

                .footer-link a {
                    color: #0071e3;
                    text-decoration: none;
                }

                .footer-link a:hover {
                    text-decoration: underline;
                }

                .demo-creds {
                    background: #f9fafb;
                    padding: 0.7rem;
                    border-radius: 6px;
                    margin-top: 1.5rem;
                    font-size: 0.8rem;
                    color: #6e6e73;
                    border: 1px dashed #d2d2d7;
                }

                .demo-creds strong {
                    color: #1d1d1f;
                }
            </style>
        </head>

        <body>
            <div class="card">
                <h1>Welcome back</h1>
                <p class="subtitle">Sign in to your account to continue</p>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <c:if test="${param.logout != null}">
                    <div class="success">You have been signed out.</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/auth/login" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="${username}" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn">Sign In</button>
                </form>

                <div class="footer-link">
                    Don't have an account? <a href="${pageContext.request.contextPath}/auth/signup">Sign up</a>
                </div>

                <div class="demo-creds">
                    <strong>Demo logins:</strong><br>
                    admin / admin123 (admin)<br>
                    alice / alice123<br>
                    bob / bob123<br>
                    charlie / charlie123
                </div>
            </div>
        </body>

        </html>