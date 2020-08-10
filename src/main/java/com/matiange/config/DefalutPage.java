package com.matiange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: shiro缓存redis后无法跳转到默认登录页的问题
 * @date 2020/8/10 17:10
 */
@Configuration
public class DefalutPage extends WebMvcConfigurerAdapter {

    /**
     * @Description: ${description}
     * @author MaTianGe
     * @params
     * @return
     * @date 17:12 2020/8/10
     * @version 0.1
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry )
    {
//        registry.addViewController( "/" ).setViewName( "forward:/login" );//请求转发到
        registry.addViewController( "/" ).setViewName( "redirect:/login" );//重定向到
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    }
}