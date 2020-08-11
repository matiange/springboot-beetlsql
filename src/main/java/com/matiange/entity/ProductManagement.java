package com.matiange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matiange.annotation.ModelField;
import lombok.Data;
import lombok.ToString;
import org.beetl.sql.core.TailBean;

import java.io.Serializable;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 生产管理类
 * @date 2020/8/6 18:59
 */
@Data
@ToString
public class ProductManagement extends TailBean implements Serializable {

    @ModelField(fieldDesc="产品ID")
    private Long produceSid;

    @ModelField(fieldDesc="产品名")
    private String produceName;

    @ModelField(fieldDesc="制作人姓名")
    private String makerName;

    @ModelField(fieldDesc="制作人联系方式")
    private String makerPhone;

    @ModelField(fieldDesc="产品编码")
    private String produceCode;

    @ModelField(fieldDesc="岗位编码")
    private String postCode;

    @ModelField(fieldDesc="岗位名称")
    private String postName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ModelField(fieldDesc="创建时间")
    private String createTime;

}
