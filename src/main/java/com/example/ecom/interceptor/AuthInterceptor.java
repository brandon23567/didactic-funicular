package com.example.ecom.interceptor;

import com.example.ecom.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Authentication gate.
 *
 * Intercepts every request that's mapped to a controller (static resources
 * and /auth/* are excluded in WebConfig). If the user is not logged in,
 * redirect to /auth/login and stash the original URL so we can return
 * there after a successful login.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (SessionUtil.isLoggedIn(request.getSession())) {
            return true; // already authenticated, proceed
        }

        // Stash the original URL (with query string) for post-login redirect
        String originalUrl = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isBlank()) {
            originalUrl += "?" + queryString;
        }
        SessionUtil.setRedirectAfterLogin(request.getSession(), originalUrl);

        // Redirect to login page (use a 302 so the URL in the browser updates)
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return false;
    }
}