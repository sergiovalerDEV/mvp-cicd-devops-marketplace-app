package com.example.wallapop.user.buyer_first_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.product.lst_all_products.view.AllProductsView;
import com.example.wallapop.product.purchased_products.view.PurchasedProductsView;
import com.example.wallapop.product.top_10_products.view.Top10ProductsView;
import com.example.wallapop.product.lst_all_offers.view.AllOffersView;
import com.example.wallapop.product.top_10_rated_products.view.Top10RatedProductsView;

public class BuyerFirstScreen extends AppCompatActivity {

    private Button btnBuyProduct, btnListPurchases, btnTopSales, btnViewOffers, btnTopRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_first_screen);

        // Inicializar los botones con los IDs correctos
        btnBuyProduct = findViewById(R.id.btnBuyProduct);
        btnListPurchases = findViewById(R.id.btnListPurchases);
        btnTopSales = findViewById(R.id.btnTopSales);
        btnViewOffers = findViewById(R.id.btnViewOffers);
        btnTopRated = findViewById(R.id.btnTopRated);

        // Configurar los OnClickListeners de los botones
        btnBuyProduct.setOnClickListener(v -> {
            Intent intent = new Intent(BuyerFirstScreen.this, AllProductsView.class);
            startActivity(intent);
        });

        btnListPurchases.setOnClickListener(v -> {
            Intent intent = new Intent(BuyerFirstScreen.this, PurchasedProductsView.class);
            startActivity(intent);
        });

        btnTopSales.setOnClickListener(v -> {
            Intent intent = new Intent(BuyerFirstScreen.this, Top10ProductsView.class);
            startActivity(intent);
        });

        btnViewOffers.setOnClickListener(v -> {
            Intent intent = new Intent(BuyerFirstScreen.this, AllOffersView.class);
            startActivity(intent);
        });

        btnTopRated.setOnClickListener(v -> {
            Intent intent = new Intent(BuyerFirstScreen.this, Top10RatedProductsView.class);
            startActivity(intent);
        });
    }
}