package com.redis.om.skeleton.json;

import com.opencsv.bean.CsvBindByName;

public class NamedColumnBean {

    @CsvBindByName(column = "nombre")
    private String name;

    // Automatically infer column name as Age
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ',' + age;
    }

}