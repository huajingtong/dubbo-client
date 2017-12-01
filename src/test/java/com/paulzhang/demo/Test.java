package com.paulzhang.demo;

import java.util.ServiceLoader;

/**
 * Created by paul on 2017/11/28.
 */
public class Test {
    public static void main(String[] args) {
        ServiceLoader<Developer> developers = ServiceLoader.load(Developer.class);
        for (Developer developer:developers) {
            System.out.println(developer.getType());
        }
        System.out.println(developers);
    }
}
