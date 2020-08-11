package com.matiange.service.impl;

import com.matiange.dao.ProductManagementDao;
import com.matiange.entity.ProductManagement;
import com.matiange.service.ProductManagementService;
import com.matiange.utils.BtsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 生产管理实现类
 * @date 2020/8/6 18:57
 */
@Service
@Transactional
public class ProductManagementServiceImpl implements ProductManagementService {

    @Autowired
    private ProductManagementDao productManagementDao;

    @Override
    public List<ProductManagement> productPageList(BtsParams params) {
        return productManagementDao.productPageList(params);
    }

    @Override
    public Long allCount(BtsParams params) {
        return productManagementDao.allCount(params);
    }
}
