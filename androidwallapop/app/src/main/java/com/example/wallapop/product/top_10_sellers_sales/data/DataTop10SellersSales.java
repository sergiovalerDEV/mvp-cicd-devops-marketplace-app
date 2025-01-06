package com.example.wallapop.product.top_10_sellers_sales.data;

import com.example.wallapop.beans.User;
import java.util.List;

public class DataTop10SellersSales {
    private String message;
    private List<User> topVendors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getTopVendors() {
        return topVendors;
    }

    public void setTopVendors(List<User> topVendors) {
        this.topVendors = topVendors;
    }
}