package com.example.wallapop.product.product_detail.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.product.product_detail.ContractProductDetail;
import com.example.wallapop.product.product_detail.presenter.ProductDetailPresenter;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

public class ProductDetailView extends AppCompatActivity implements ContractProductDetail.View {

    private ImageView ivProductImage;
    private TextView tvDescripcion;
    private TextView tvPrecio;
    private ProductDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_view);

        ivProductImage = findViewById(R.id.ivProductImage);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPrecio = findViewById(R.id.tvPrecio);

        presenter = new ProductDetailPresenter(this);

        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        String descripcion = getIntent().getStringExtra("DESCRIPCION");
        String precioString = getIntent().getStringExtra("PRECIO");
        double precio = 0.0;
        try {
            precio = Double.parseDouble(precioString);
        } catch (NumberFormatException e) {
            Log.e("ProductDetailView", "Error parsing precio: " + precioString, e);
        }

        presenter.loadProductDetails(imageUrl, descripcion, precio);
    }

    @Override
    public void showProductDetails(String imageUrl, String descripcion, double precio) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                imageUrl = "https://" + imageUrl; // Add https:// if missing
            }
            new _SV2_INF_THE_BEST.Builder(imageUrl, ivProductImage)
                    .build()
                    .load();
        } else {
            System.out.println("Iamgen no encontrada");

        }
        tvDescripcion.setText(descripcion);
        tvPrecio.setText(String.format("%.2f €", precio));
    }
}