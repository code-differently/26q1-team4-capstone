package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;

public class User {
    private int  id;
    private String name;
    private String password;
    private UserRole role;

    public User(int id, String name, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }




}
