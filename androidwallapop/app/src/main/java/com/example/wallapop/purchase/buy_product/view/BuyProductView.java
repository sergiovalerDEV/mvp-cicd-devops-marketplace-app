package com.example.wallapop.purchase.buy_product.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.purchase.buy_product.ContractBuyProduct;
import com.example.wallapop.purchase.buy_product.presenter.BuyProductPresenter;
import com.example.wallapop.utils.UserSession;

public class BuyProductView extends AppCompatActivity implements ContractBuyProduct.View {
    private BuyProductPresenter presenter;
    private int productId;
    private String productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_view);

        String emailRecipient = "a27675@svalero.com"; //Importante ver mailtrap para visualizar dónde llegan los correos
        //Pongo mi correo de San Valero para evitar errores con correos falsos
        //Y para facilitar el testing
        //Si se prefiere, podemos coger el email de UserSession, settearlo y ponerlo aquí

        presenter = new BuyProductPresenter(this, this, emailRecipient);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        productDescription = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        int userId = UserSession.getInstance().getUserId();

        if (productId != -1 && userId != -1) {
            presenter.buyProduct(userId, productId, productDescription);
        } else {
            failurePurchase("Error: Datos de compra inválidos");
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