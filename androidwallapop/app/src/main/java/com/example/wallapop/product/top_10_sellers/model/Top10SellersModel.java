package com.example.wallapop.product.top_10_sellers.model;

import com.example.wallapop.product.top_10_sellers.ContractTop10Sellers;
import com.example.wallapop.product.top_10_sellers.data.DataTop10Sellers;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Response;

public class Top10SellersModel implements ContractTop10Sellers.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void fetchTop10Sellers(OnLoadTop10SellersListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataTop10Sellers> call = apiService.getTop10Sellers();

        call.enqueue(new retrofit2.Callback<DataTop10Sellers>() {
            @Override
            public void onResponse(Call<DataTop10Sellers> call, Response<DataTop10Sellers> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onTop10SellersLoaded(response.body().convertToUsers());
                } else {
                    listener.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataTop10Sellers> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}