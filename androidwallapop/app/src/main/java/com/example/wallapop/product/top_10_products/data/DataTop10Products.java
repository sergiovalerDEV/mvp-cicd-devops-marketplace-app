package com.example.wallapop.product.top_10_products.data;

import com.example.wallapop.beans.Product;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataTop10Products {
    @SerializedName("topRatedProducts")//Serializamos de nuevo
    //Nos aseguramos que mapeemos de manera correcta
    private List<Product> topRatedProducts;

    public List<Product> getTopRatedProducts() {
        return topRatedProducts;
    }

    public void setTopRatedProducts(List<Product> topRatedProducts) {
        this.topRatedProducts = topRatedProducts;
    }
}