package com.example.wallapop.beans;

public class User {
    private int id;
    private String email;
    private String password;
    private String token;
    private Integer role_id;
    private int total_productos;
    private String total_ventas; // Nuevo campo añadido

    // Constructor vacío
    public User() {
    }

    // Constructor con email, password, y token
    public User(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    // Constructor con email, password, token, y total_productos
    public User(String email, String password, String token, int total_productos) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.total_productos = total_productos;
    }

    // Constructor con ID, email, password, y role_id
    public User(int id, String email, String password, Integer role_id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    // Constructor con email, password, y role_id
    public User(String email, String password, Integer role_id) {
        this.id = 0; // Valor por defecto para ID
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    // Nuevo constructor para incluir total_ventas
    public User(int id, String email, String total_ventas) {
        this.id = id;
        this.email = email;
        this.total_ventas = total_ventas;
    }

    // Getters y Setters para todos los campos (incluyendo el nuevo)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public int getTotal_productos() {
        return total_productos;
    }

    public void setTotal_productos(int total_productos) {
        this.total_productos = total_productos;
    }

    public String getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(String total_ventas) {
        this.total_ventas = total_ventas;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", role_id=" + role_id +
                ", total_productos=" + total_productos +
                ", total_ventas='" + total_ventas + '\'' +
                '}';
    }
}