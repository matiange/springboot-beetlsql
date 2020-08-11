package com.matiange;

import com.matiange.entity.UserSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description:
 * @date 2020/8/10 14:55
 */
public class NewTestOne {
    public static void main(String[] args) {
        List<String> permissions = new ArrayList<String>(){{
        }};
        List<String> collect = permissions.stream().filter(notBank -> StringUtils.isNotBlank(notBank)).collect(Collectors.toList());
        System.out.println(collect);
    }
}
