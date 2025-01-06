package com.example.wallapop.product.top_10_sellers.data;

import com.example.wallapop.beans.User;

import java.util.ArrayList;

public class DataTop10Sellers {
    private String message;
    private ArrayList<TopVendor> topVendors;

    public static class TopVendor {
        private String email;
        private int total_productos;

        public String getEmail() {
            return email;
        }

        public int getTotal_productos() {
            return total_productos;
        }
    }

    public ArrayList<User> convertToUsers() {
        ArrayList<User> users = new ArrayList<>();
        if (topVendors != null) {
            for (TopVendor vendor : topVendors) {
                User user = new User();
                user.setEmail(vendor.getEmail());
                user.setTotal_productos(vendor.getTotal_productos());
                users.add(user);
            }
        }
        return users;
    }
}