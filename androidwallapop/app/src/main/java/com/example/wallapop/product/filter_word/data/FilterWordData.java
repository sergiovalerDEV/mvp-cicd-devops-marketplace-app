package com.example.wallapop.product.filter_word.data;

import com.example.wallapop.beans.Product;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FilterWordData {
    @SerializedName("products")//Por si acaso, mapeamos por seguridad
    private List<Product> products;

    public FilterWordData(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}