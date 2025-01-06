package com.example.wallapop.product.lst_all_offers.model;

import android.util.Log;

import com.example.wallapop.product.lst_all_offers.ContractAllOffers;
import com.example.wallapop.product.lst_all_offers.data.DataAllOffers;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOffersModel implements ContractAllOffers.Model {
    private final ContractAllOffers.Model.OnOffersListener listener;
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    public AllOffersModel(ContractAllOffers.Model.OnOffersListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchAllOffers() {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataAllOffers> call = apiService.listOffers();

        call.enqueue(new Callback<DataAllOffers>() {
            @Override
            public void onResponse(Call<DataAllOffers> call, Response<DataAllOffers> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataAllOffers dataOffers = response.body();
                    if (dataOffers.getOffers() != null && !dataOffers.getOffers().isEmpty()) {
                        listener.onOffersFetched(new ArrayList<>(dataOffers.getOffers()));
                    } else {
                        Log.e("API Error", "No offers found");
                        listener.onFailure("No offers found.");
                    }
                } else {
                    Log.e("API Error", "Unsuccessful response: " + response.code());
                    listener.onFailure("Error in response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataAllOffers> call, Throwable t) {
                Log.e("API Error", "Network failure: " + t.getMessage());
                listener.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}