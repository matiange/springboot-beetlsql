package com.matiange.utils;

import com.alibaba.fastjson.JSON;
import com.matiange.entity.SysUser;
import com.matiange.entity.UserSession;
import com.matiange.service.SysRoleService;
import com.matiange.service.SysUserService;
import com.matiange.shiro.SessionContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义权限登录验证
 * <br>Created by Admin on 2020/5/4.
 * <br>星期四 at 22:12.
 */
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService SysRoleService;

    /**
     * 认证(登录时调用)
     *
     * @param token 用户输入的token
     * @return 登录信息
     * @throws AuthenticationException 异常信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;//用户输入的token
        String username = userToken.getUsername();
        String password = new String((char[]) userToken.getCredentials());
        //查询用户信息
        SysUser sysUser = sysUserService.findByName(username);
        if (sysUser == null) {//账号为空
            throw new UnknownAccountException("账号不存在");
        } else if (!password.equals(sysUser.getPassword())) {
            //密码不匹配
            throw new IncorrectCredentialsException("密码不正确");
        } else if (sysUser.getStatus() == 0) {
            //状态值为锁定状态
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        Session session = SecurityUtils.getSubject().getSession();//获取session
        UserSession userSession = new UserSession();
        userSession.setAuthCacheKey(session.getId().toString());
        userSession.setUsername(((UsernamePasswordToken) token).getUsername());
        userSession.setUserId(sysUser.getUserId());
        userSession.setStatus(sysUser.getStatus());
        userSession.setCreateUserId(sysUser.getCreateUserId());
        userSession.setEmail(sysUser.getEmail());
        userSession.setMobile(sysUser.getMobile());
        userSession.setPassword(sysUser.getPassword());
        userSession.setCreateTime(sysUser.getCreateTime());
        //角色
        List<Long> roleIdList = SysRoleService.roleIdList(sysUser.getCreateUserId());
        if(roleIdList!=null && roleIdList.size()>0){
            userSession.setRoleIdList(roleIdList);
        }
        session.setAttribute(SessionContext.USER_SESSION_KEY, JSON.toJSON(userSession));

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userSession, sysUser.getPassword(), getName());//放入shiro.调用CredentialsMatcher检验密码
        return authenticationInfo;//放入shiro.调用CredentialsMatcher检验密码
    }

    /**
     * 授权(认证时调用)
     *
     * @param principal 当前账号
     * @return 认证信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principal) {
        Object userSession = principal.getPrimaryPrincipal();//这里强转报错暂时用beanutil转一下

        UserSession sysUser = new UserSession();
        try {
            BeanUtils.copyProperties(sysUser,userSession);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //当前用户id
        Long userId = sysUser.getUserId();
        //根据id获得权限列表
        List<String> permissions = sysUserService.userPermsList(userId);
        List<String> collect = permissions.stream().filter(notBank -> StringUtils.isNotBlank(notBank)).collect(Collectors.toList());
        Set<String> permsSet = new HashSet<>();
        for (String perms : permissions) {//将空的权限去除
            if (StringUtils.isNotBlank(perms)) {
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);//将权限放入shiro中
        return info;
    }

}
