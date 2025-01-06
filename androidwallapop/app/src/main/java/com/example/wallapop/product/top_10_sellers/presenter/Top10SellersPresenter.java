package com.example.wallapop.product.top_10_sellers.presenter;

import com.example.wallapop.beans.User;
import com.example.wallapop.product.top_10_sellers.model.Top10SellersModel;
import com.example.wallapop.product.top_10_sellers.ContractTop10Sellers;

import java.util.ArrayList;

public class Top10SellersPresenter implements ContractTop10Sellers.Presenter, ContractTop10Sellers.Model.OnLoadTop10SellersListener {
    private final ContractTop10Sellers.View view; // Interfaz de la vista
    private final Top10SellersModel model; // Instancia del modelo

    // Constructor del presentador que recibe la vista
    public Top10SellersPresenter(ContractTop10Sellers.View view) {
        this.view = view;
        this.model = new Top10SellersModel(); // Inicializa el modelo
    }

    @Override
    public void loadTop10Sellers() {
        model.fetchTop10Sellers(this); // Llama al modelo para cargar los vendedores
    }

    // Método que se llama cuando los vendedores han sido cargados exitosamente
    @Override
    public void onTop10SellersLoaded(ArrayList<User> topSellers) {
        if (topSellers != null && !topSellers.isEmpty()) {
            view.showTop10Sellers(topSellers); // Muestra los vendedores en la vista
        } else {
            view.showError("No se encontraron vendedores."); // Manejo de caso vacío
        }
    }

    // Método que se llama en caso de error al cargar los vendedores
    @Override
    public void onFailure(String error) {
        view.showError(error); // Muestra el error en la vista
    }
}