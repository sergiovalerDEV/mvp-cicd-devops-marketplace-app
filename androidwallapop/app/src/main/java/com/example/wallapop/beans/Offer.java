package com.example.wallapop.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Offer {
    private int id;
    private String imagen;
    private String descripcion;
    private BigDecimal precio;
    private int user_id;
    private int category_id;
    private String palabras_clave;
    private BigDecimal precio_oferta;
    private Date fecha_inicio_oferta;
    private Date fecha_fin_oferta;

    // Default constructor
    public Offer() {
    }

    // Constructor with all fields
    public Offer(int id, String imagen, String descripcion, BigDecimal precio, int user_id, int category_id,
                 String palabras_clave, BigDecimal precio_oferta, Date fecha_inicio_oferta, Date fecha_fin_oferta) {
        this.id = id;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.user_id = user_id;
        this.category_id = category_id;
        this.palabras_clave = palabras_clave;
        this.precio_oferta = precio_oferta;
        this.fecha_inicio_oferta = fecha_inicio_oferta;
        this.fecha_fin_oferta = fecha_fin_oferta;
    }



    public Offer(int id, String descripcion, BigDecimal precio, BigDecimal precio_oferta, Date fecha_inicio_oferta, Date fecha_fin_oferta) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precio_oferta = precio_oferta;
        this.fecha_inicio_oferta = fecha_inicio_oferta;
        this.fecha_fin_oferta = fecha_fin_oferta;
    }



    // Getters and setters
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    public BigDecimal getPrecioOferta() {
        return precio_oferta;
    }

    public void setPrecioOferta(BigDecimal precio_oferta) {
        this.precio_oferta = precio_oferta;
    }

    public Date getFechaInicioOferta() {
        return fecha_inicio_oferta;
    }

    public void setFechaInicioOferta(Date fecha_inicio_oferta) {
        this.fecha_inicio_oferta = fecha_inicio_oferta;
    }

    public Date getFechaFinOferta() {
        return fecha_fin_oferta;
    }

    public void setFechaFinOferta(Date fecha_fin_oferta) {
        this.fecha_fin_oferta = fecha_fin_oferta;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", user_id=" + user_id +
                ", category_id=" + category_id +
                ", palabras_clave='" + palabras_clave + '\'' +
                ", precio_oferta=" + precio_oferta +
                ", fecha_inicio_oferta=" + fecha_inicio_oferta +
                ", fecha_fin_oferta=" + fecha_fin_oferta +
                '}';
    }
}