package com.example.wallapop.user.register_user.presenter;

import com.example.wallapop.beans.User;
import com.example.wallapop.user.register_user.ContractRegisterUser;
import com.example.wallapop.user.register_user.model.RegisterUserModel;

public class RegisterUserPresenter implements ContractRegisterUser.Presenter, ContractRegisterUser.Model.OnRegisterUserListener {
    private ContractRegisterUser.View view;
    private ContractRegisterUser.Model model;

    public RegisterUserPresenter(ContractRegisterUser.View view) {
        this.view = view;
        this.model = new RegisterUserModel();
    }

    @Override
    public void register(User user) {
        if (isValidInput(user)) {
            model.registerApi(user, this);
        } else {
            view.failureRegister("El email y la contraseña no pueden estar vacíos");
        }
    }

    private boolean isValidInput(User user) {
        return user != null && user.getEmail() != null && !user.getEmail().isEmpty()
                && user.getPassword() != null && !user.getPassword().isEmpty();
    }

    @Override
    public void onFinished(String message) {
        view.successRegister(message);
    }




    @Override
    public void onFailure(String err) {
        view.failureRegister(err);
    }
}