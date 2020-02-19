package com.matiange.service.impl;

import com.matiange.dao.EditNewsDao;
import com.matiange.entity.EditNews;
import com.matiange.service.EditNewsService;
import com.matiange.utils.BtsParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>Created by 马天歌 on 2020/02/5.
 * <br>星期一 at 11:36.
 */
@Service
public class EditNewsServiceImpl implements EditNewsService {

    @Resource
    private EditNewsDao dao;

    @Override
    public void saveNews(EditNews news) {
        dao.insert(news,true);
    }

    @Override
    public EditNews selectNewsById(Long newsId) {
        return dao.selectNewsById(newsId);
    }

    @Override
    public void editNews(EditNews news) {
        dao.updateById(news);
    }

    @Override
    public void deleteById(Long newsId) {
        dao.deleteById(newsId);
    }

    @Override
    public List<EditNews> newsList(BtsParams params) {
        return dao.newsList(params);
    }

    @Override
    public Long allCount(BtsParams params) {
        return dao.allCount(params);
    }
}
