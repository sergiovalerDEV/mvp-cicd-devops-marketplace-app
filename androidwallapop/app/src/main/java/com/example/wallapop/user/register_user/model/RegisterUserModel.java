package com.example.wallapop.user.register_user.model;

import android.util.Log;

import com.example.wallapop.beans.User;
import com.example.wallapop.user.register_user.ContractRegisterUser;
import com.example.wallapop.user.login_user.data.MyData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserModel implements ContractRegisterUser.Model {
    private static final String IP_BASE = "http://54.225.144.72:3000/";

    @Override
    public void registerApi(User user, OnRegisterUserListener onRegisterUserListener) {
        ApiService apiService = RetrofitCliente.getClient(IP_BASE).create(ApiService.class);

        Call<MyData> call = apiService.register(user);

        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                // Verificamos si la respuesta es exitosa y si el código es 201
                if (response.code() == 201) {
                    // El registro fue exitoso
                    onRegisterUserListener.onFinished("Registro exitoso."); // Mensaje de éxito
                } else {
                    // Manejo de errores para otros códigos de respuesta
                    String errorMessage = "No se pudo registrar el usuario. Código de error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += " - " + response.errorBody().string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("RegisterUserModel", errorMessage);
                    onRegisterUserListener.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                onRegisterUserListener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}