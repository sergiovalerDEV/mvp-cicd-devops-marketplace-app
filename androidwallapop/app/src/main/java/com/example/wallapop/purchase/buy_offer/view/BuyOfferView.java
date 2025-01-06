package com.example.wallapop.purchase.buy_offer.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.purchase.buy_offer.ContractBuyOffer;
import com.example.wallapop.purchase.buy_offer.presenter.BuyOfferPresenter;
import com.example.wallapop.utils.UserSession;

public class BuyOfferView extends AppCompatActivity implements ContractBuyOffer.View {
    private BuyOfferPresenter presenter;
    private int offerId;
    private String offerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_offer_view);

        presenter = new BuyOfferPresenter(this);

        offerId = getIntent().getIntExtra("OFFER_ID", -1);
        offerDescription = getIntent().getStringExtra("OFFER_DESCRIPTION");
        int userId = UserSession.getInstance().getUserId();

        if (offerId != -1 && userId != -1) {
            presenter.buyOffer(userId, offerId, offerDescription);
        } else {
            failurePurchase("Error: Datos de compra de oferta inválidos");
            finish();
        }
    }

    @Override
    public void successPurchase(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failurePurchase(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        finish();
    }
}