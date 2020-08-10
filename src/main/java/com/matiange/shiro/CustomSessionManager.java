package com.matiange.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 自定义session管理配置
 * @date 2020/8/10 9:48
 */
@Configuration
@Slf4j
public class CustomSessionManager extends DefaultWebSessionManager {

    private static final String TOKEN = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public CustomSessionManager() {
        super();
    }


    /**
     * @Description: 重写
     * @author MaTianGe
     * @params
     * @return
     * @date 9:50 2020/8/10
     * @version 0.1
     */
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(TOKEN);
        //如果请求头上中没有，从请求参数中获取
        if(StringUtils.isEmpty(id)){
            HttpServletRequest req= (HttpServletRequest) request;
            Map<String, String[]> parameterMap = req.getParameterMap();
            String[] authorizations = parameterMap.get("Authorization");
            if(authorizations!=null&&authorizations.length>0){
                id=authorizations[0];
            }
        }

        //如果请求头中有 token 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
