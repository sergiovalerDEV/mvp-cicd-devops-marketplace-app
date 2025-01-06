package com.example.wallapop.product.edit_product.model;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.edit_product.ContractEditProduct;
import com.example.wallapop.product.edit_product.data.EditProductData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductModel implements ContractEditProduct.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void editProduct(Product product, final OnEditProductListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<EditProductData> call = apiService.editProduct(product);

        call.enqueue(new Callback<EditProductData>() {
            @Override
            public void onResponse(Call<EditProductData> call, Response<EditProductData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onEditSuccess(response.body().getMessage());
                } else {
                    listener.onEditFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EditProductData> call, Throwable t) {
                listener.onEditFailure("Network error: " + t.getMessage());
            }
        });
    }
}
