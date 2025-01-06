package com.example.wallapop.product.rate_product.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.product.rate_product.ContractRateProduct;
import com.example.wallapop.product.rate_product.presenter.RateProductPresenter;
import com.example.wallapop.utils.UserSession;

public class RateProductView extends AppCompatActivity implements ContractRateProduct.View {
    private RatingBar ratingBar;
    private Button btnSubmitRating;
    private RateProductPresenter presenter;

    private int productId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_product_view);

        presenter = new RateProductPresenter(this);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        UserSession userSession = UserSession.getInstance();
        userId = userSession.getUserId();

        initComponents();
    }

    private void initComponents() {
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rating = (int) ratingBar.getRating();
                if (rating > 0) {
                    presenter.rateProduct(productId, userId, rating);
                } else {
                    Toast.makeText(RateProductView.this, "Por favor, selecciona una valoración.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void successRate(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failureRate(String err) {
        Toast.makeText(this, "Error al valorar: " + err, Toast.LENGTH_SHORT).show();
    }
}