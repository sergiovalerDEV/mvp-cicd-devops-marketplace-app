package com.example.wallapop.product.lst_all_products.model;

import android.util.Log;

import com.example.wallapop.product.lst_all_products.ContractAllProducts;
import com.example.wallapop.product.lst_all_products.data.DataAllProducts;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductsModel implements ContractAllProducts.Model {
    private final ContractAllProducts.Model.OnProductsListener listener;
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    public AllProductsModel(ContractAllProducts.Model.OnProductsListener listener) {
        this.listener = listener;
    }

    public void fetchAllProducts() {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataAllProducts> call = apiService.getAllProducts(); // Asegúrate de que este método esté definido en ApiService

        call.enqueue(new Callback<DataAllProducts>() {
            @Override
            public void onResponse(Call<DataAllProducts> call, Response<DataAllProducts> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataAllProducts dataProducts = response.body();
                    if (dataProducts.getProducts() != null && !dataProducts.getProducts().isEmpty()) {
                        listener.onProductsFetched(new ArrayList<>(dataProducts.getProducts()));
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
            public void onFailure(Call<DataAllProducts> call, Throwable t) {
                Log.e("API Error", "Network failure: " + t.getMessage());
                listener.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}