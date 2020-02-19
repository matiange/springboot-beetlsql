package com.matiange.service.impl;

import com.matiange.dao.SysUserDao;
import com.matiange.entity.SysMenu;
import com.matiange.entity.SysUser;
import com.matiange.service.SysMenuService;
import com.matiange.service.SysUserRoleService;
import com.matiange.service.SysUserService;
import com.matiange.utils.BtsParams;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <br>Created by Admin on 2020/5/4.
 * <br>星期四 at 9:23.
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser findByName(String username) {
        return sysUserDao.findByName(username);
    }

    @Override
    public SysUser findById(Long userId) {
        return sysUserDao.findById(userId);
    }

    @Override
    public List<SysUser> userPageList(BtsParams params) {
        return sysUserDao.userPageList(params);
    }

    @Override
    public List<String> userPermsList(Long userId) {
        return sysUserDao.userPermsList(userId);
    }

    @Override
    public List<SysMenu> userMenuIdList(Long userId) {
        List<Long> menuIdList = sysUserDao.userMenuIdList(userId);
        return sysMenuService.getAllMenuList(menuIdList);
    }

    @Override
    public String ajaxExistUser(String usernameAjax) {
        return sysUserDao.ajaxExistUser(usernameAjax);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserDao.deleteById(userId);
        sysUserRoleService.deleteByUserId(userId);
    }

    @Override
    public Long allCount(BtsParams params) {
        return sysUserDao.allCount(params);
    }

    @Override
    public void addUser(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        //密码加密
        sysUser.setPassword(sysUser.getPassword());
        sysUserDao.insert(sysUser, true);//可获取刚插入的id
        //checkRole(sysUser);
        sysUserRoleService.saveOrUpdate(
                sysUser.getUserId(), sysUser.getRoleIdList());
    }

    @Override
    public void editUser(SysUser sysUser) {
        sysUser.setPassword(sysUser.getPassword());
        sysUserDao.updateById(sysUser);
        sysUserRoleService.saveOrUpdate(
                sysUser.getUserId(), sysUser.getRoleIdList());
    }

    @Override
    public Integer updatePassword(Long userId, String oldPassword, String newPassword) {
        return sysUserDao.updatePassword(userId, oldPassword, newPassword);
    }

}
