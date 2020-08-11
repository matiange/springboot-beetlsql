package com.matiange.controller;

import com.matiange.entity.ProductManagement;
import com.matiange.service.ProductManagementService;
import com.matiange.utils.BtsParams;
import com.matiange.utils.PageUtils;
import com.matiange.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 产品管理
 * @date 2020/8/6 18:55
 */
@Controller
//@RequestMapping("/produce")
public class ProductManagementController extends AbstractController{

    @Autowired
    private ProductManagementService produceManagementService;


    @RequestMapping("/productManagement")
    public String produceView(){
        return "/md/productManagement";
    }

    /**
     * @Description: 产品管理查询
     * @author MaTianGe
     * @params 分页参数
     * @params 分页封装
     * @return 产品管理查询分页合集
     * @date 8:43 2020/8/10
     * @version 0.1
     */
    @ResponseBody
    @RequestMapping("/productPageList")
    @RequiresPermissions("md:product:list")
    public PageUtils productPageList(BtsParams params, PageUtils pageUtils){
        List<ProductManagement> productManagementList = produceManagementService.productPageList(params);
        Long total = produceManagementService.allCount(params);//总记录数
        pageUtils.setTotal(total);
        pageUtils.setRows(productManagementList);
        return pageUtils;
    }

}
