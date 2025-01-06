package com.example.wallapop.product.filtered_products.data;

import com.example.wallapop.beans.Product;
import java.util.ArrayList;

public class ProductData {
    private ArrayList<Product> products;

    public ProductData(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}