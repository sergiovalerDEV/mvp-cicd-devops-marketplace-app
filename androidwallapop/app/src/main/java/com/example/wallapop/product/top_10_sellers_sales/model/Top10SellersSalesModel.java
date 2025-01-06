package com.example.wallapop.product.top_10_sellers_sales.model;

import com.example.wallapop.product.top_10_sellers_sales.data.DataTop10SellersSales;
import com.example.wallapop.product.top_10_sellers_sales.ContractTop10SellersSales;
import com.example.wallapop.beans.User;
import com.example.wallapop.utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Top10SellersSalesModel implements ContractTop10SellersSales.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void fetchTop10SellersSales(OnLoadTop10SellersSalesListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<DataTop10SellersSales> call = apiService.getTop10SellersSales();

        call.enqueue(new Callback<DataTop10SellersSales>() {
            @Override
            public void onResponse(Call<DataTop10SellersSales> call, Response<DataTop10SellersSales> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> topVendors = response.body().getTopVendors();
                    if (topVendors != null && !topVendors.isEmpty()) {
                        listener.onTop10SellersSalesLoaded(topVendors);
                    } else {
                        listener.onFailure("No se encontraron vendedores.");
                    }
                } else {
                    listener.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataTop10SellersSales> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}