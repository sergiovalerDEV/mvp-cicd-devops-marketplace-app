package com.example.wallapop.purchase.lst_user_products.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.purchase.lst_user_products.ContractListProducts;
import com.example.wallapop.purchase.lst_user_products.adapters.UserProductsAdapter;
import com.example.wallapop.purchase.lst_user_products.presenter.ProductsPresenter;
import com.example.wallapop.utils.UserSession; // Importa la clase UserSession

import java.util.ArrayList;

public class LstUserProducts extends AppCompatActivity implements ContractListProducts.View {
    private RecyclerView recyclerView;
    private UserProductsAdapter adapter;
    private ProductsPresenter presenter;
    private ArrayList<Product> products;
    private int userId; // ID del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_user_products); // Asegúrate de que el XML esté correctamente nombrado

        // Obtener el ID del usuario desde UserSession
        userId = UserSession.getInstance().getUserId(); // Obtén el ID del usuario

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewUserProducts); // Asegúrate de que el ID exista en el XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de productos
        products = new ArrayList<>();
        adapter = new UserProductsAdapter(this, products);
        recyclerView.setAdapter(adapter);

        // Inicializar el presenter y cargar los productos del usuario
        presenter = new ProductsPresenter(this);
        presenter.fetchProducts(userId); // Llama al método fetchProducts
    }

    @Override
    public void successProducts(ArrayList<Product> products) {
        // Muestra los productos en la vista
        displayProducts(products);
    }

    @Override
    public void failureProducts(String error) {
        // Muestra el error en la vista
        showError(error);
    }

    private void displayProducts(ArrayList<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        adapter.notifyDataSetChanged();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
