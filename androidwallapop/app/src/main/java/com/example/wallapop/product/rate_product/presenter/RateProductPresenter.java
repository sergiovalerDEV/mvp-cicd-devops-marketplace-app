// rate_product/presenter/RateProductPresenter.java
package com.example.wallapop.product.rate_product.presenter;

import com.example.wallapop.product.rate_product.model.RateProductModel;
import com.example.wallapop.product.rate_product.ContractRateProduct;
import com.example.wallapop.utils.UserSession;

public class RateProductPresenter implements ContractRateProduct.Presenter, ContractRateProduct.Model.OnRateProductListener {
    private ContractRateProduct.View view;
    private ContractRateProduct.Model model;

    public RateProductPresenter(ContractRateProduct.View view) {
        this.view = view;
        this.model = new RateProductModel();
    }

    @Override
    public void rateProduct(int productId, int userId, int rating) {

        Integer userRoleId = UserSession.getInstance().getRole_id();

        if (userRoleId != null && userRoleId == 1){
            view.failureRate("No tienes permisos para valorar este producto");
            return;
        }

        model.rateProductApi(productId, userId, rating, this);
    }

    @Override
    public void onFinished(String message) {
        view.successRate(message);
    }

    @Override
    public void onFailure(String err) {
        view.failureRate(err);
    }
}