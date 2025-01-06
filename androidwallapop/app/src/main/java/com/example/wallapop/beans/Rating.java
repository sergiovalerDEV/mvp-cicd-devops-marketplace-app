package com.example.wallapop.beans;

public class Rating {
    private int product_id;
    private int user_id;
    private int rating;

    public Rating(int product_id, int user_id, int rating) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.rating = rating;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRating() {
        return rating;
    }
}
