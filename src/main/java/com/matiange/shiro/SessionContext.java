package com.matiange.shiro;

import com.alibaba.fastjson.JSON;
import com.matiange.entity.UserSession;
import com.matiange.utils.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.beetl.ext.fn.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description:
 * @date 2020/8/10 14:03
 */
public class SessionContext {
    private static final Logger logger = LoggerFactory.getLogger(SessionContext.class);
    public static final String USER_SESSION_KEY = "userSessionKey";

    public static void destroy() {
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute(USER_SESSION_KEY);
    }

    public static void init(UserSession userSession){
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(USER_SESSION_KEY, JSON.toJSON(userSession));
    }

    /**
     * 获取登录用户UserSession对象
     * @return
     */
    public static UserSession getUserSession() {
        Session session = SecurityUtils.getSubject().getSession();
        Object userJson = session.getAttribute(USER_SESSION_KEY);
        if( null != userJson) {
            return JSON.toJavaObject((JSON) userJson, UserSession.class);
        }else {
            logger.warn("获取的UserSession对象为缺省设置,非用户实际登录的Session对象!");
            return new UserSession();
        }
    }

    /**
     * 获取json格式的登录用户UserSession
     * @return
     */
    public static String getJsonUserSession() {
        Session session = SecurityUtils.getSubject().getSession();
        String userJson = (String)session.getAttribute(USER_SESSION_KEY);
        return userJson;
    }

    /**
     * 获得用户姓名
     * @return
     */
    public static String getUserName() {
        return getUserSession().getUsername();
    }

    /**
     * 获得用户sid
     * @return
     */
    public static Long getUserSid() {
        return getUserSession().getUserId();
    }



    /**
     * 获得用户角色列表
     * @return
     */
    public static List<Long> getRoles() {
        return getUserSession().getRoleIdList();
    }

    /**
     * 获取手机号
     * @return
     */
    public static String getMobile() {
        return getUserSession().getMobile();
    }

    /**
     * 获取用户所有岗位名拼成的字符串
     * @return
     */
    public static String getEmail() {
        return getUserSession().getEmail();
    }

    /**
     * 获取是否可用状态
     * @return
     */
    public static Integer getStatus(){
        return getUserSession().getStatus();
    }
}
