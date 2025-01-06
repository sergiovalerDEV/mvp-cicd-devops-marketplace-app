package com.example.wallapop.product.top_10_sellers.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.User; // Asegúrate de que la clase User esté correctamente definida
import com.example.wallapop.product.top_10_sellers.ContractTop10Sellers;
import com.example.wallapop.product.top_10_sellers.adapters.Top10SellersAdapter;
import com.example.wallapop.product.top_10_sellers.presenter.Top10SellersPresenter;

import java.util.ArrayList;

public class Top10SellersView extends AppCompatActivity implements ContractTop10Sellers.View {
    private RecyclerView recyclerViewTopSellers;
    private Top10SellersAdapter top10SellersAdapter;
    private Top10SellersPresenter presenter;
    private ArrayList<User> topSellersList; // Lista de los mejores vendedores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_sellers_view); // Asegúrate de que el XML esté correctamente nombrado

        // Inicializa RecyclerView
        recyclerViewTopSellers = findViewById(R.id.recyclerViewTopSellers); // Asegúrate de que el ID exista en el XML
        recyclerViewTopSellers.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de vendedores
        topSellersList = new ArrayList<>();
        top10SellersAdapter = new Top10SellersAdapter(this, topSellersList);
        recyclerViewTopSellers.setAdapter(top10SellersAdapter);

        // Inicializa el presenter y carga los mejores vendedores
        presenter = new Top10SellersPresenter(this);
        presenter.loadTop10Sellers(); // Llama al método loadTop10Sellers correctamente
    }

    @Override
    public void showTop10Sellers(ArrayList<User> topSellers) {
        if (topSellers != null) { // Verifica que la lista no sea nula
            topSellersList.clear();
            topSellersList.addAll(topSellers);
            top10SellersAdapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
        } else {
            showError("Error: No se han encontrado vendedores."); // Manejo de error si la lista es nula
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Muestra un mensaje de error
    }
}