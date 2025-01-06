package com.example.wallapop.product.lst_all_products.data;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public class DataAllProducts {
    private ArrayList<Product> products;

    public DataAllProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}