package com.matiange.service.impl;

import com.matiange.dao.SysFileUploadDao;
import com.matiange.entity.SysFileUpload;
import com.matiange.service.SysFileUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <br>Created by 马天歌 on 2020/02/2.
 * <br>星期三 at 11:21.
 */
@Service
public class SysFileUploadServiceImpl implements SysFileUploadService {

    @Resource
    private SysFileUploadDao dao;
    @Override
    public void saveFile(SysFileUpload fileUpload) {
        dao.insert(fileUpload,true);
    }
}
