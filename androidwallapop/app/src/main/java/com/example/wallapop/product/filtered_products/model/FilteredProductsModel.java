package com.example.wallapop.product.filtered_products.model;

import com.example.wallapop.product.filtered_products.FilteredProductsContract;
import com.example.wallapop.product.filtered_products.data.ProductData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilteredProductsModel implements FilteredProductsContract.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";
    private final FilteredProductsContract.Model.OnFilteredProductsListener listener;

    public FilteredProductsModel(FilteredProductsContract.Model.OnFilteredProductsListener listener) {
        this.listener = listener;
    }

    @Override
    public void getFilteredProducts(int categoryId) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<ProductData> call = apiService.getProductsByCategory(categoryId);

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onProductsFetched(response.body().getProducts());
                } else {
                    listener.onFailure("Error loading products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                listener.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}