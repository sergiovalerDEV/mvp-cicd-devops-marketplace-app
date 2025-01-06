package com.example.wallapop.product.lst_admin_offers.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.product.lst_admin_offers.ContractAdminOffers;
import com.example.wallapop.product.lst_admin_offers.adapters.AdminOffersAdapter;
import com.example.wallapop.product.lst_admin_offers.presenter.AdminOffersPresenter;
import com.example.wallapop.beans.Offer;

import java.util.ArrayList;

public class AdminOffersView extends AppCompatActivity implements ContractAdminOffers.View {
    private RecyclerView recyclerViewAdminOffers;
    private AdminOffersAdapter adminOffersAdapter;
    private AdminOffersPresenter presenter;
    private ArrayList<Offer> offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offers_view);

        recyclerViewAdminOffers = findViewById(R.id.recyclerViewAdminOffers);
        recyclerViewAdminOffers.setLayoutManager(new LinearLayoutManager(this));

        offersList = new ArrayList<>();
        adminOffersAdapter = new AdminOffersAdapter(this, offersList);
        recyclerViewAdminOffers.setAdapter(adminOffersAdapter);

        presenter = new AdminOffersPresenter(this);
        presenter.fetchOffers();
    }

    @Override
    public void successOffers(ArrayList<Offer> offers) {
        if (offers != null) {
            offersList.clear();
            offersList.addAll(offers);
            adminOffersAdapter.notifyDataSetChanged();
        } else {
            showError("Error: No se han encontrado ofertas.");
        }
    }

    @Override
    public void failureOffers(String error) {
        showError(error);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}