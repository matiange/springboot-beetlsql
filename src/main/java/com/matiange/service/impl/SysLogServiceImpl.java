package com.matiange.service.impl;

import com.matiange.dao.SysLogDao;
import com.matiange.entity.SysLog;
import com.matiange.service.SysLogService;
import com.matiange.utils.BtsParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志业务接口实现
 * <br>Created by Admin on 2020/2/19
 * <br>星期三 at 16:43.
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao dao;

    @Override
    public void save(SysLog sysLog) {
        dao.insert(sysLog);
    }

    @Override
    public Long allCount(BtsParams params) {
        return dao.allCount(params);
    }

    @Override
    public List<SysLog> logPageList(BtsParams params) {
        return dao.logPageList(params);
    }
}
