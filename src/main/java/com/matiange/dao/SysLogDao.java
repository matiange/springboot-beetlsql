package com.matiange.dao;

import com.matiange.entity.SysLog;
import com.matiange.utils.BtsParams;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志数据访问接口
 * <br>Created by Admin on 2020/2/19
 * <br>星期三 at 16:41.
 */
@Repository
public interface SysLogDao extends BaseMapper<SysLog> {

    /**
     * 操作日志分页查询列表
     *
     * @param params 参数
     * @return 操作日志分页集合
     */
    @SqlStatement(params = "params")
    List<SysLog> logPageList(BtsParams params);

    /**
     * 总记录数
     *
     * @param params 参数
     * @return 总数
     */
    @SqlStatement(params = "params")
    Long allCount(BtsParams params);
}
