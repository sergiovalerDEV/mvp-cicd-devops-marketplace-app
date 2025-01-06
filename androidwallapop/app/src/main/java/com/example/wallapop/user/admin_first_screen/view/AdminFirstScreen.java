package com.example.wallapop.user.admin_first_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.purchase.upload_offer.view.UploadOfferView;
import com.example.wallapop.product.lst_admin_offers.view.AdminOffersView;

public class AdminFirstScreen extends AppCompatActivity {

    private Button btnUploadOffer, btnManageOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_first_screen);

        // Initialize the buttons
        btnUploadOffer = findViewById(R.id.btnUploadOffer);
        btnManageOffers = findViewById(R.id.btnManageOffers);

        // Set OnClickListeners for the buttons
        btnUploadOffer.setOnClickListener(v -> {
            // Start the activity to upload offers
            Intent intent = new Intent(AdminFirstScreen.this, UploadOfferView.class);
            startActivity(intent);
        });

        btnManageOffers.setOnClickListener(v -> {
            // Start the activity to manage offers
            Intent intent = new Intent(AdminFirstScreen.this, AdminOffersView.class);
            startActivity(intent);
        });
    }
}