package com.example.wallapop.product.delete_product.model;

import com.example.wallapop.product.delete_product.ContractDeleteProduct;
import com.example.wallapop.product.delete_product.data.DeleteProductData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProductModel implements ContractDeleteProduct.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void deleteProduct(int productId, final OnDeleteProductListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DeleteProductData> call = apiService.deleteProduct(productId);

        call.enqueue(new Callback<DeleteProductData>() {
            @Override
            public void onResponse(Call<DeleteProductData> call, Response<DeleteProductData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onDeleteSuccess(response.body().getMessage());
                } else {
                    listener.onDeleteFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DeleteProductData> call, Throwable t) {
                listener.onDeleteFailure("Network error: " + t.getMessage());
            }
        });
    }
}