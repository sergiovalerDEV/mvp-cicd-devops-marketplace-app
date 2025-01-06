package com.example.wallapop.product.purchased_products.model;

import android.util.Log;

import com.example.wallapop.product.purchased_products.ContractPurchasedProducts;
import com.example.wallapop.product.purchased_products.data.DataPurchasedProducts;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;
import com.example.wallapop.utils.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasedProductsModel implements ContractPurchasedProducts.Model {
    private static final String TAG = "PurchasedProductsModel";
    private final ContractPurchasedProducts.Model.OnPurchasedProductsListener listener;
    private static final String BASE_URL = "http://54.225.144.72:3000/";
    private final UserSession userSession;

    public PurchasedProductsModel(ContractPurchasedProducts.Model.OnPurchasedProductsListener listener) {
        this.listener = listener;
        this.userSession = UserSession.getInstance();
    }

    @Override
    public void fetchPurchasedProducts() {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        int userId = userSession.getUserId();
        Log.d(TAG, "Fetching purchased products for user ID: " + userId);

        Call<DataPurchasedProducts> call = apiService.getPurchasedProducts(userId);

        call.enqueue(new Callback<DataPurchasedProducts>() {
            @Override
            public void onResponse(Call<DataPurchasedProducts> call, Response<DataPurchasedProducts> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataPurchasedProducts data = response.body();
                    Log.d(TAG, "Response received: " + data.getMessage());
                    if (data.getProducts() != null && !data.getProducts().isEmpty()) {
                        Log.d(TAG, "Products found: " + data.getProducts().size());
                        listener.onPurchasedProductsFetched(data.getProducts());
                    } else {
                        Log.w(TAG, "No products found in the response");
                        listener.onFailure("No se encontraron productos comprados.");
                    }
                } else {
                    Log.e(TAG, "Error in response: " + response.code());
                    listener.onFailure("Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataPurchasedProducts> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage(), t);
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}