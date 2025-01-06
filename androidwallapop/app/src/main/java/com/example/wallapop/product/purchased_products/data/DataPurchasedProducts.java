package com.example.wallapop.product.purchased_products.data;

import com.example.wallapop.beans.Product;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DataPurchasedProducts {
    private String message;

    @SerializedName("productos")//Serializamos por seguridad
    private ArrayList<Product> products;

    // Constructor
    public DataPurchasedProducts(String message, ArrayList<Product> products) {
        this.message = message;
        this.products = products;
    }

    // Getter para el mensaje
    public String getMessage() {
        return message;
    }

    // Setter para el mensaje
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter para la lista de productos
    public ArrayList<Product> getProducts() {
        return products;
    }

    // Setter para la lista de productos
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}