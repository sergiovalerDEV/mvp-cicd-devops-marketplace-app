package com.example.wallapop.product.top_10_products.model;

import com.example.wallapop.product.top_10_products.data.DataTop10Products;
import com.example.wallapop.product.top_10_products.ContractTop10Products;
import com.example.wallapop.beans.Product;
import com.example.wallapop.utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Top10ProductsModel implements ContractTop10Products.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void fetchTop10Products(OnLoadTop10ProductsListener listener) {
        // Create Retrofit instance with GsonConverterFactory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Add this line
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<DataTop10Products> call = apiService.getTop10Products();

        call.enqueue(new Callback<DataTop10Products>() {
            @Override
            public void onResponse(Call<DataTop10Products> call, Response<DataTop10Products> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> topRatedProducts = response.body().getTopRatedProducts();
                    if (topRatedProducts != null && !topRatedProducts.isEmpty()) {
                        listener.onTop10ProductsLoaded(topRatedProducts);
                    } else {
                        listener.onFailure("No se encontraron productos.");
                    }
                } else {
                    listener.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataTop10Products> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}