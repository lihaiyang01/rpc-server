package com.oceanli;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;

    private int age;

    private String sex;

}
