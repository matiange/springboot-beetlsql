package com.matiange.service.impl;

import com.matiange.dao.SysRoleDao;
import com.matiange.entity.SysRole;
import com.matiange.service.SysRoleMenuService;
import com.matiange.service.SysRoleService;
import com.matiange.service.SysUserRoleService;
import com.matiange.utils.BtsParams;
import com.matiange.utils.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <br>Created by Admin on 2020/5/9.
 * <br>星期二 at 16:44.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysRole> rolePageList(BtsParams params, Long createUserId) {
        return sysRoleDao.rolePageList(params, createUserId);
    }

    @Override
    public Long allCount(BtsParams params) {
        return sysRoleDao.allCount(params);
    }

    @Override
    public List<SysRole> roleList(long createUserId) {
        return sysRoleDao.roleList(createUserId);
    }

    @Override
    public List<Long> roleIdList(long createUserId) {
        return sysRoleDao.roleIdList(createUserId);
    }

    @Override
    public SysRole findById(Long roleId) {
        return sysRoleDao.findById(roleId);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleDao.deleteById(roleId);
        sysRoleMenuService.deleteByRoleId(roleId);
        sysUserRoleService.deleteByRoleId(roleId);

    }

    @Override
    public void addRole(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        sysRoleDao.insert(sysRole, true);
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), sysRole.getMenuIdList());
    }

    @Override
    public void editRole(SysRole sysRole) {
        sysRoleDao.updateById(sysRole);
        if (sysRole.getRoleId() != Constant.SUPER_ROLE) {
            sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), sysRole.getMenuIdList());
        }
    }
}
