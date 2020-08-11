package com.matiange.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description:
 * @date 2020/7/4 12:19
 */
public class A {
    private List<String> list = new ArrayList<>();
    static {
        System.out.println("1");
    }
    static {
        System.out.println("1.1");
    }

    public A() {
        System.out.println("A");
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
