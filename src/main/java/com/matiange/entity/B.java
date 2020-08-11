package com.matiange.entity;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description:
 * @date 2020/7/4 12:21
 */
public class B extends A{
    static {
        System.out.println("2");
    }
    static {
        System.out.println("2.1");
    }

    public B() {
        System.out.println("B");
    }
}
