package com.example.ecom.util;

import com.example.ecom.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * Thin wrapper around the raw HttpSession attribute names.
 * Centralizes the keys so we don't have string literals scattered around.
 */
public final class SessionUtil {

    public static final String USER_ID_KEY = "userId";
    public static final String USERNAME_KEY = "username";
    public static final String USER_ROLE_KEY = "userRole";
    public static final String REDIRECT_AFTER_LOGIN_KEY = "redirectAfterLogin";

    private SessionUtil() {
    }

    public static void loginUser(HttpSession session, User user) {
        session.setAttribute(USER_ID_KEY, user.getId());
        session.setAttribute(USERNAME_KEY, user.getUsername());
        session.setAttribute(USER_ROLE_KEY, user.getRole());
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(USER_ID_KEY);
        session.removeAttribute(USERNAME_KEY);
        session.removeAttribute(USER_ROLE_KEY);
        session.removeAttribute(REDIRECT_AFTER_LOGIN_KEY);
    }

    public static Long getUserId(HttpSession session) {
        Object id = session.getAttribute(USER_ID_KEY);
        return id == null ? null : (Long) id;
    }

    public static String getUsername(HttpSession session) {
        Object name = session.getAttribute(USERNAME_KEY);
        return name == null ? null : (String) name;
    }

    public static String getUserRole(HttpSession session) {
        Object role = session.getAttribute(USER_ROLE_KEY);
        return role == null ? null : (String) role;
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(USER_ID_KEY) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(getUserRole(session));
    }

    public static void setRedirectAfterLogin(HttpSession session, String url) {
        session.setAttribute(REDIRECT_AFTER_LOGIN_KEY, url);
    }

    public static String consumeRedirectAfterLogin(HttpSession session) {
        Object url = session.getAttribute(REDIRECT_AFTER_LOGIN_KEY);
        if (url != null) {
            session.removeAttribute(REDIRECT_AFTER_LOGIN_KEY);
            return (String) url;
        }
        return null;
    }
}