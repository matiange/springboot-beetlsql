package com.matiange.service;

import com.matiange.entity.ProductManagement;
import com.matiange.utils.BtsParams;

import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 生产管理接口
 * @date 2020/8/6 18:56
 */
public interface ProductManagementService {
    List<ProductManagement> productPageList(BtsParams params);

    Long allCount(BtsParams params);
}
