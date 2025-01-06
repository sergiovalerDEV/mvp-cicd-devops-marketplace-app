package com.example.wallapop.purchase.upload_offer.data;

import com.example.wallapop.beans.Offer;

public class DataUploadOffer {
    private String message;
    private Offer offer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}