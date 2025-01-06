package com.example.wallapop.product.upload_product.model;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.upload_product.ContractUploadProduct;
import com.example.wallapop.product.upload_product.data.DataUpload;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadProductModel implements ContractUploadProduct.Model {
    private static final String IP_BASE = "http://54.225.144.72:3000/";

    @Override
    public void uploadProduct(Product product, OnUploadProductListener listener) {
        ApiService apiService = RetrofitCliente.getClient(IP_BASE).create(ApiService.class);

        // Llama a la API para subir el producto
        Call<DataUpload> call = apiService.uploadProduct(product);

        call.enqueue(new Callback<DataUpload>() {
            @Override
            public void onResponse(Call<DataUpload> call, Response<DataUpload> response) {
                if (response.isSuccessful()) {
                    DataUpload myData = response.body();
                    if (myData != null) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("Error: el cuerpo de la respuesta es nulo.");
                    }
                } else {
                    listener.onFailure("Error en la respuesta del servidor: " + response.message() + " (Código: " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<DataUpload> call, Throwable t) {
                handleNetworkError(t, listener);
            }
        });
    }

    private void handleNetworkError(Throwable t, OnUploadProductListener listener) {
        listener.onFailure("Error de red al subir el producto: " + t.getMessage());
    }
}
