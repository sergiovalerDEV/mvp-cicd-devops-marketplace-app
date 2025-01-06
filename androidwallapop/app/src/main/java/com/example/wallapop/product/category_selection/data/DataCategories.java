package com.example.wallapop.product.category_selection.data;

import com.example.wallapop.beans.Category;

import java.util.ArrayList;

public class DataCategories {
    private ArrayList<Category> categories;

    public DataCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}