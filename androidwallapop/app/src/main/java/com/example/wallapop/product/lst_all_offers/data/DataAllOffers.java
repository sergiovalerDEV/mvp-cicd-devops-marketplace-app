package com.example.wallapop.product.lst_all_offers.data;

import com.example.wallapop.beans.Offer;

import java.util.ArrayList;

public class DataAllOffers {
    private ArrayList<Offer> offers;

    public DataAllOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }
}