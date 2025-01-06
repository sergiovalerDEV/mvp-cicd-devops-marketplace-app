package com.example.wallapop.product.lst_all_offers.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.product.lst_all_offers.ContractAllOffers;
import com.example.wallapop.product.lst_all_offers.adapters.AllOffersAdapter;
import com.example.wallapop.product.lst_all_offers.presenter.AllOffersPresenter;
import com.example.wallapop.beans.Offer;

import java.util.ArrayList;

public class AllOffersView extends AppCompatActivity implements ContractAllOffers.View {
    private RecyclerView recyclerViewOffers;
    private AllOffersAdapter offersAdapter;
    private AllOffersPresenter presenter;
    private ArrayList<Offer> offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offers_view);

        recyclerViewOffers = findViewById(R.id.recyclerViewOffers);
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this));

        offersList = new ArrayList<>();
        offersAdapter = new AllOffersAdapter(this, offersList);
        recyclerViewOffers.setAdapter(offersAdapter);

        presenter = new AllOffersPresenter(this);
        presenter.fetchOffers();
    }

    @Override
    public void successOffers(ArrayList<Offer> offers) {
        if (offers != null) {
            offersList.clear();
            offersList.addAll(offers);
            offersAdapter.notifyDataSetChanged();
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