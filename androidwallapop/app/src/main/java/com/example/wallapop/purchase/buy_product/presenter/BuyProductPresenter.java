package com.example.wallapop.purchase.buy_product.presenter;

import android.content.Context;

import com.example.wallapop.purchase.buy_product.ContractBuyProduct;
import com.example.wallapop.purchase.buy_product.model.BuyProductModel;

public class BuyProductPresenter implements ContractBuyProduct.Presenter, ContractBuyProduct.Model.OnBuyProductListener {
    private final ContractBuyProduct.View view;
    private final BuyProductModel model;

    public BuyProductPresenter(ContractBuyProduct.View view, Context context, String emailRecipient) {
        this.view = view;
        this.model = new BuyProductModel(this, context, emailRecipient);
    }

    @Override
    public void buyProduct(int userId, int productId, String productDescription) {
        model.purchaseProduct(userId, productId, productDescription);
    }

    @Override
    public void confirmPurchase(int purchaseId) {
        model.confirmPurchase(purchaseId);
    }

    @Override
    public void onPurchaseSuccess(String message) {
        view.successPurchase(message);
    }

    @Override
    public void onPurchaseFailure(String error) {
        view.failurePurchase(error);
    }
}