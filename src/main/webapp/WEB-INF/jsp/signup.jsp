<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Sign Up — E-Commerce App</title>
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
                    max-width: 450px;
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
                input[type="password"],
                input[type="email"] {
                    width: 100%;
                    padding: 0.7rem 0.9rem;
                    border: 1px solid #d2d2d7;
                    border-radius: 8px;
                    font-size: 0.95rem;
                    transition: border-color 0.15s;
                }

                input:focus {
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

                .hint {
                    font-size: 0.78rem;
                    color: #86868b;
                    margin-top: 0.3rem;
                }
            </style>
        </head>

        <body>
            <div class="card">
                <h1>Create your account</h1>
                <p class="subtitle">It's free and only takes a minute.</p>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/auth/signup" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="${username}" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="${email}" required>
                    </div>
                    <div class="form-group">
                        <label for="fullName">Full name (optional)</label>
                        <input type="text" id="fullName" name="fullName" value="${fullName}">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                        <div class="hint">At least 6 characters.</div>
                    </div>
                    <button type="submit" class="btn">Create Account</button>
                </form>

                <div class="footer-link">
                    Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Sign in</a>
                </div>
            </div>
        </body>

        </html>