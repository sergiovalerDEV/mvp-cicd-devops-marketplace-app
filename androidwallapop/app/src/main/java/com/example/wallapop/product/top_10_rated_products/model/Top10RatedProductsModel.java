package com.example.wallapop.product.top_10_rated_products.model;

import com.example.wallapop.product.top_10_rated_products.data.DataTop10RatedProducts;
import com.example.wallapop.product.top_10_rated_products.ContractTop10RatedProducts;
import com.example.wallapop.beans.Product;
import com.example.wallapop.utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Top10RatedProductsModel implements ContractTop10RatedProducts.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void fetchTop10RatedProducts(OnLoadTop10RatedProductsListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<DataTop10RatedProducts> call = apiService.getTop10RatedProducts();

        call.enqueue(new Callback<DataTop10RatedProducts>() {
            @Override
            public void onResponse(Call<DataTop10RatedProducts> call, Response<DataTop10RatedProducts> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> topRatedProducts = response.body().getTopRatedProducts();
                    if (topRatedProducts != null && !topRatedProducts.isEmpty()) {
                        listener.onTop10RatedProductsLoaded(topRatedProducts);
                    } else {
                        listener.onFailure("No se encontraron productos valorados.");
                    }
                } else {
                    listener.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataTop10RatedProducts> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}