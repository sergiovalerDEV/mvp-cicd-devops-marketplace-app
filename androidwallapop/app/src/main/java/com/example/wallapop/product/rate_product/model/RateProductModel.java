package com.example.wallapop.product.rate_product.model;

import android.util.Log;

import com.example.wallapop.beans.Rating;
import com.example.wallapop.product.rate_product.ContractRateProduct;
import com.example.wallapop.user.login_user.data.MyData; // Cambiado para usar el MyData correcto
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateProductModel implements ContractRateProduct.Model {
    private static final String IP_BASE = "http://54.225.144.72:3000/";

    @Override
    public void rateProductApi(int productId, int userId, int rating, OnRateProductListener listener) {
        ApiService apiService = RetrofitCliente.getClient(IP_BASE).create(ApiService.class);

        // Crear una instancia de Rating
        Rating ratingData = new Rating(productId, userId, rating);

        // Llamada a la API con el objeto Rating
        Call<MyData> call = apiService.rateProduct(ratingData);

        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if (response.isSuccessful()) {
                    MyData data = response.body();
                    if (data != null) {
                        listener.onFinished(data.getMessage());
                    } else {
                        listener.onFailure("Respuesta vacía del servidor");
                    }
                } else {
                    String errorMessage = "Error del servidor: " + response.code();
                    Log.e("RateProductModel", errorMessage);
                    listener.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                String errorMessage = "Error de red: " + t.getMessage();
                Log.e("RateProductModel", errorMessage);
                listener.onFailure(errorMessage);
            }
        });
    }
}