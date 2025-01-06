package com.example.wallapop.user.register_user;

import com.example.wallapop.beans.User;

public interface ContractRegisterUser {
    interface View {
        void successRegister(String message);
        void failureRegister(String err);
    }

    interface Presenter {
        void register(User user);
    }

    interface Model {
        interface OnRegisterUserListener {
            void onFinished(String message);
            void onFailure(String err);
        }

        void registerApi(User user, OnRegisterUserListener onRegisterUserListener);
    }
}
