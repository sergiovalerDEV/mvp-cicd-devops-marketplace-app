package com.example.wallapop.product.filter_word.model;

import android.util.Log;

import com.example.wallapop.product.filter_word.ContractFilterWord;
import com.example.wallapop.product.filter_word.data.FilterWordData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterWordModel implements ContractFilterWord.Model {
    private final ContractFilterWord.Model.OnFilterWordListener listener;
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    public FilterWordModel(ContractFilterWord.Model.OnFilterWordListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchProductsByKeywords(String keywords) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<FilterWordData> call = apiService.getProductsByKeywords(keywords);

        call.enqueue(new Callback<FilterWordData>() {
            @Override
            public void onResponse(Call<FilterWordData> call, Response<FilterWordData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FilterWordData data = response.body();
                    if (data.getProducts() != null && !data.getProducts().isEmpty()) {
                        listener.onFilterWordFetched(new ArrayList<>(data.getProducts()));
                    } else {
                        Log.e("API Error", "No products found");
                        listener.onFailure("No products found.");
                    }
                } else {
                    Log.e("API Error", "Unsuccessful response: " + response.code());
                    listener.onFailure("Error in response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FilterWordData> call, Throwable t) {
                Log.e("API Error", "Network failure: " + t.getMessage());
                listener.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}