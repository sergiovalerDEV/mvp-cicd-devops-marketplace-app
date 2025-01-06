package com.example.wallapop.product.category_selection.model;

import com.example.wallapop.product.category_selection.ContractCategories;
import com.example.wallapop.product.category_selection.data.DataCategories;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback; // Asegúrate de importar el Callback de Retrofit
import retrofit2.Response;

public class CategoriesModel implements ContractCategories.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";
    private final OnCategoriesListener listener;

    public CategoriesModel(OnCategoriesListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchCategories() {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataCategories> call = apiService.getCategories();

        call.enqueue(new Callback<DataCategories>() {
            @Override
            public void onResponse(Call<DataCategories> call, Response<DataCategories> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onCategoriesFetched(response.body().getCategories());
                } else {
                    listener.onFailure("Error al obtener categorías: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataCategories> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}
