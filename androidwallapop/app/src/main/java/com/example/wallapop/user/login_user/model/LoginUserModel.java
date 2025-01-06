package com.example.wallapop.user.login_user.model;

import com.example.wallapop.beans.User;
import com.example.wallapop.user.login_user.ContractLoginUser;
import com.example.wallapop.user.login_user.data.MyData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserModel implements ContractLoginUser.Model {
    private static final String IP_BASE = "http://54.225.144.72:3000/";

    @Override
    public void loginApi(User user, final OnLoginUserListener listener) {
        ApiService apiService = RetrofitCliente.getClient(IP_BASE).create(ApiService.class);

        // Llamamos a la API de login con email y password
        Call<MyData> call = apiService.login(user);

        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if (response.isSuccessful()) {
                    MyData data = response.body();
                    if (data != null && data.getUser() != null) {
                        listener.onFinished(data.getUser());  // Retorna el objeto usuario si la respuesta es correcta
                    } else {
                        listener.onFailure("Usuario no encontrado");
                    }
                } else {
                    listener.onFailure("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                listener.onFailure("Error en la conexión: " + t.getMessage());
            }
        });
    }
}