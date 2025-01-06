package com.example.wallapop.user.login_user;

import com.example.wallapop.beans.User;

public interface ContractLoginUser {
    interface View {
        void successLogin(User user);
        void failureLogin(String err);
    }

    interface Presenter {
        void login(User user);
    }

    interface Model {
        interface OnLoginUserListener {
            void onFinished(User user);
            void onFailure(String error);
        }

        void loginApi(User user, OnLoginUserListener onLoginUserListener);
    }
}
