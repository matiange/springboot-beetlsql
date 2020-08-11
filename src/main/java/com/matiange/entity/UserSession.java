package com.matiange.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.beetl.sql.core.TailBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: session缓存起来的用户信息
 * @date 2020/8/10 13:41
 */
@Data
@ToString
public class UserSession extends TailBean implements Serializable {

    private String authCacheKey; //用户会话唯一key

    private Long userId;//用户Id

    private Integer status;//是否可用状态

    private Long createUserId;//创建者Id

    private String email;//邮箱

    private String mobile;//手机

    private String password;//密码

    private String username;//用户名

    private Date createTime;//创建时间

    private List<Long> roleIdList;//角色Id列表
}
