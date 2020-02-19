package com.matiange.dao;

import com.matiange.entity.EditNews;
import com.matiange.utils.BtsParams;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>Created by 马天歌 on 2020/02/5.
 * <br>星期一 at 11:36.
 */
@Repository
public interface EditNewsDao extends BaseMapper<EditNews> {

    @SqlStatement(params = "newsId")
    EditNews selectNewsById(Long newsId);

    @SqlStatement(params = "params")
    List<EditNews> newsList(BtsParams params);

    /**
     * 总记录数
     *
     * @param params 参数
     * @return 总数
     */
    @SqlStatement(params = "params")
    Long allCount(BtsParams params);
}
