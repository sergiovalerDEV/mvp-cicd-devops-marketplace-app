package com.example.wallapop.utils;

public class UserSession {
    private static UserSession instance;
    private Integer userId;
    private Integer role_id; // Cambiar 'role' a 'role_id'

    // Constructor privado para el singleton
    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setRole_id(Integer role_id) { // Cambiar el nombre del método
        this.role_id = role_id;
    }

    public Integer getRole_id() { // Cambiar el nombre del método
        return role_id;
    }
}
