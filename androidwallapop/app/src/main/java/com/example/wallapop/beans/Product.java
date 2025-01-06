package com.example.wallapop.beans;

public class Product {
    private int id;
    private String imagen;
    private String descripcion;
    private String precio;
    private double promedioCalificacion;
    private int user_id;
    private int category_id;
    private String palabras_clave;
    private int total_ventas;
    private double average_rating;
    private long total_ratings;
    private int sales;

    public Product() {
    }

    // Constructor for products with average rating
    public Product(int id, String imagen, String descripcion, String precio, double promedioCalificacion) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.promedioCalificacion = promedioCalificacion;
    }

    // Constructor for products without average rating
    public Product(int id, String imagen, String descripcion, String precio, int user_id, int category_id, String palabras_clave) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.user_id = user_id;
        this.category_id = category_id;
        this.palabras_clave = palabras_clave;
    }

    // Constructor for products with average rating and total sales
    public Product(int id, String imagen, String descripcion, String precio, double promedioCalificacion, int user_id, int category_id, String palabras_clave, int total_ventas) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.promedioCalificacion = promedioCalificacion;
        this.user_id = user_id;
        this.category_id = category_id;
        this.palabras_clave = palabras_clave;
        this.total_ventas = total_ventas;
    }

    // Constructor for top rated products
    public Product(int id, String imagen, String descripcion, String precio, double average_rating, long total_ratings) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.average_rating = average_rating;
        this.total_ratings = total_ratings;
    }

    // New constructor for products with sales information
    public Product(int id, String imagen, String descripcion, String precio, int sales) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.sales = sales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public double getPromedioCalificacion() {
        return promedioCalificacion;
    }

    public void setPromedioCalificacion(double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public String getPalabrasClave() {
        return palabras_clave;
    }

    public void setPalabrasClave(String palabras_clave) {
        this.palabras_clave = palabras_clave;
    }

    public int getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(int total_ventas) {
        this.total_ventas = total_ventas;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    public long getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(long total_ratings) {
        this.total_ratings = total_ratings;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\'' +
                ", promedioCalificacion=" + promedioCalificacion +
                ", user_id=" + user_id +
                ", category_id=" + category_id +
                ", palabras_clave='" + palabras_clave + '\'' +
                ", total_ventas=" + total_ventas +
                ", average_rating=" + average_rating +
                ", total_ratings=" + total_ratings +
                ", sales=" + sales +
                '}';
    }
}