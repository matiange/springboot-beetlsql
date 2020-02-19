package com.matiange.service.impl;


import com.matiange.dao.TablesDao;
import com.matiange.entity.Tables;
import com.matiange.service.TablesService;
import com.matiange.utils.BtsParams;
import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库表业务接口实现
 * <br>Created by Admin on 2020/4/28.
 * <br>星期三 at 14:29.
 */
@Service
public class TablesServiceImpl implements TablesService {

    @Resource
    private TablesDao dao;

    @Override
    public List<Tables> tablePageList(BtsParams params) {
        return dao.tablePageList(params);
    }

    @Override
    public Long allCount(BtsParams params) {
        return dao.allCount(params);
    }

    @Override
    public SQLManager getSQLManager() {
        return dao.getSQLManager();
    }
}
