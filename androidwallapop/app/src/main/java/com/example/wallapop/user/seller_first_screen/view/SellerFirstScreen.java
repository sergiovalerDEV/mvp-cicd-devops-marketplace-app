package com.example.wallapop.user.seller_first_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.product.upload_product.view.UploadProductM;
import com.example.wallapop.purchase.lst_user_products.view.LstUserProducts;
import com.example.wallapop.product.top_10_sellers.view.Top10SellersView;
import com.example.wallapop.product.top_10_sellers_sales.view.Top10SellersSalesView;

public class SellerFirstScreen extends AppCompatActivity {

    private Button btnUploadProduct, btnTopSellers, btnUserProducts, btnTopSellersSales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_seller_first_screen);

        // Inicializar los botones
        btnUploadProduct = findViewById(R.id.btnUploadProduct);
        btnTopSellers = findViewById(R.id.btnTopSellers);
        btnUserProducts = findViewById(R.id.btnUserProducts);
        btnTopSellersSales = findViewById(R.id.btnTopSellersSales);

        // Establecer OnClickListeners para los botones
        btnUploadProduct.setOnClickListener(v -> {
            // Iniciar la actividad para subir productos
            Intent intent = new Intent(SellerFirstScreen.this, UploadProductM.class);
            startActivity(intent);
        });

        btnTopSellers.setOnClickListener(v -> {
            // Iniciar la actividad para ver el Top 10 de vendedores
            Intent intent = new Intent(SellerFirstScreen.this, Top10SellersView.class);
            startActivity(intent);
        });

        btnUserProducts.setOnClickListener(v -> {
            // Iniciar la actividad para ver los productos del usuario
            Intent intent = new Intent(SellerFirstScreen.this, LstUserProducts.class);
            startActivity(intent);
        });

        btnTopSellersSales.setOnClickListener(v -> {
            // Iniciar la actividad para ver el Top 10 de vendedores con más ventas
            Intent intent = new Intent(SellerFirstScreen.this, Top10SellersSalesView.class);
            startActivity(intent);
        });
    }
}