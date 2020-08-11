package com.matiange.dao;

import com.matiange.entity.ProductManagement;
import com.matiange.utils.BtsParams;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 生产管理持久层
 * @date 2020/8/6 18:58
 */
@Repository
public interface ProductManagementDao extends BaseMapper<ProductManagement> {


    /**
     * 总记录数
     *
     * @param params 参数
     * @return 总数
     */
    @SqlStatement(params = "params")
    public Long allCount(BtsParams params);

    /**
     * 查询产品列表
     *
     * @param params 参数
     * @return 产品分页集合
     */
    @SqlStatement(params = "params")
    List<ProductManagement> productPageList(BtsParams params);
}
