package com.example.wallapop.product.top_10_rated_products.data;

import com.example.wallapop.beans.Product;
import java.util.List;

public class DataTop10RatedProducts {
    private List<Product> topRatedProducts;

    private String message;

    public List<Product> getTopRatedProducts() {
        return topRatedProducts;
    }

    public void setTopRatedProducts(List<Product> topRatedProducts) {
        this.topRatedProducts = topRatedProducts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}