package com.example.wallapop.purchase.lst_user_products.data;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public class DataProducts {
    private ArrayList<Product> products;

    public DataProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}