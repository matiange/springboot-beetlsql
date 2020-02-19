package com.matiange.service;

import com.matiange.entity.EditNews;
import com.matiange.utils.BtsParams;

import java.util.List;

/**
 * <br>Created by 马天歌 on 2020/02/5.
 * <br>星期一 at 11:36.
 */
public interface EditNewsService {
    void saveNews(EditNews news);

    EditNews selectNewsById(Long newsId);

    void editNews(EditNews news);

    void deleteById(Long newsId);

    List<EditNews> newsList(BtsParams params);

    /**
     * 总数量
     *
     * @return Long型数值
     */
    Long allCount(BtsParams params);
}
