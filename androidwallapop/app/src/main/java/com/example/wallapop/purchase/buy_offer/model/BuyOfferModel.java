package com.example.wallapop.purchase.buy_offer.model;

import com.example.wallapop.purchase.buy_offer.ContractBuyOffer;
import com.example.wallapop.purchase.buy_offer.data.BuyOfferData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyOfferModel implements ContractBuyOffer.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000";

    @Override
    public void purchaseOffer(int userId, int offerId, String offerDescription, final OnBuyOfferListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);

        BuyOfferData.PurchaseInfo purchaseInfo = new BuyOfferData.PurchaseInfo();
        purchaseInfo.setIdUsuario(userId);
        purchaseInfo.setIdOferta(offerId);  // Cambiado desde setIdProducto a setIdOferta
        //Nos aseguramos de que establecer los id de manera correcta (setteamos)

        Call<BuyOfferData> call = apiService.comprarOferta(purchaseInfo);

        call.enqueue(new Callback<BuyOfferData>() {
            @Override
            public void onResponse(Call<BuyOfferData> call, Response<BuyOfferData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onPurchaseSuccess(response.body().getMessage());
                } else {
                    listener.onPurchaseFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BuyOfferData> call, Throwable t) {
                listener.onPurchaseFailure("Network error: " + t.getMessage());
            }
        });
    }
}