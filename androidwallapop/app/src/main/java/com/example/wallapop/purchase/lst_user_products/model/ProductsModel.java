package com.example.wallapop.purchase.lst_user_products.model;

import android.util.Log;

import com.example.wallapop.purchase.lst_user_products.ContractListProducts;
import com.example.wallapop.purchase.lst_user_products.data.DataProducts;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsModel {
    private final ContractListProducts.Model.OnProductsListener listener;
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    public ProductsModel(ContractListProducts.Model.OnProductsListener listener) {
        this.listener = listener;
    }

    public void fetchProductsByUser(int userId) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataProducts> call = apiService.getUserProducts(userId);  // Asegúrate de que este método esté definido en tu ApiService

        call.enqueue(new Callback<DataProducts>() {
            @Override
            public void onResponse(Call<DataProducts> call, Response<DataProducts> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataProducts dataProducts = response.body();
                    if (dataProducts.getProducts() != null && !dataProducts.getProducts().isEmpty()) {
                        listener.onProductsFetched(new ArrayList<>(dataProducts.getProducts())); // Notifica al presenter
                    } else {
                        Log.e("API Error", "No products found");
                        listener.onFailure("No products found."); // Notifica al presenter
                    }
                } else {
                    Log.e("API Error", "Unsuccessful response: " + response.code());
                    listener.onFailure("Error in response: " + response.code()); // Notifica al presenter
                }
            }

            @Override
            public void onFailure(Call<DataProducts> call, Throwable t) {
                Log.e("API Error", "Network failure: " + t.getMessage());
                listener.onFailure("Network error: " + t.getMessage()); // Notifica al presenter
            }
        });
    }
}