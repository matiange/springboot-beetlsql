package com.matiange.utils;

import com.matiange.entity.SysUser;
import com.matiange.entity.UserSession;
import com.matiange.shiro.SessionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * <br>Created by Admin on 2020/2/19
 * <br>星期三 at 21:44.
 */
public class ShiroUtils {
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static UserSession getSysUser() {
        return SessionContext.getUserSession();
    }

    public static Long getUserId() {
        return getSysUser().getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        getSubject().login(token);
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        String kaptcha = getSessionAttribute(key).toString();
        getSession().removeAttribute(key);
        return kaptcha;
    }
}
