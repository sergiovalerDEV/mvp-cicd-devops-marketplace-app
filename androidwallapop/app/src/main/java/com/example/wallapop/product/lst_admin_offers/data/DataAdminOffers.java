package com.example.wallapop.product.lst_admin_offers.data;

import com.example.wallapop.beans.Offer;

import java.util.ArrayList;

public class DataAdminOffers {
    private ArrayList<Offer> offers;

    public DataAdminOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }
}