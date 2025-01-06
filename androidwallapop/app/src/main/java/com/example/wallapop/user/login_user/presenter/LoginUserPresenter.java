package com.example.wallapop.user.login_user.presenter;

import com.example.wallapop.beans.User;
import com.example.wallapop.user.login_user.ContractLoginUser;
import com.example.wallapop.user.login_user.model.LoginUserModel;

public class LoginUserPresenter implements ContractLoginUser.Presenter {
    private ContractLoginUser.View view;
    private LoginUserModel model;

    public LoginUserPresenter(ContractLoginUser.View view) {
        this.view = view;
        this.model = new LoginUserModel();
    }

    @Override
    public void login(User user) {
        model.loginApi(user, new ContractLoginUser.Model.OnLoginUserListener() {
            @Override
            public void onFinished(User loggedInUser) {
                // Asegúrate de que loggedInUser contenga el role_id
                if (loggedInUser.getRole_id() != null) {
                    view.successLogin(loggedInUser);
                } else {
                    view.failureLogin("Role ID is not available.");
                }
            }

            @Override
            public void onFailure(String error) {
                view.failureLogin(error);
            }
        });
    }
}