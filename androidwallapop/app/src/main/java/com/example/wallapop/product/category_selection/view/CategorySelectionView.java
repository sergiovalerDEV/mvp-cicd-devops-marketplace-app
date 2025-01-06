package com.example.wallapop.product.category_selection.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.beans.Category;
import com.example.wallapop.product.category_selection.ContractCategories;
import com.example.wallapop.product.category_selection.presenter.CategoriesPresenter;
import com.example.wallapop.product.filtered_products.view.FilteredProductsView;

import java.util.ArrayList;

public class CategorySelectionView extends AppCompatActivity implements ContractCategories.View {
    private LinearLayout linearLayoutCategories; // Cambiado de ListView a LinearLayout
    private CategoriesPresenter presenter;
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection_view);

        linearLayoutCategories = findViewById(R.id.linearLayoutCategories);
        categories = new ArrayList<>();
        presenter = new CategoriesPresenter(this);

        presenter.fetchCategories();
    }

    @Override
    public void successCategories(ArrayList<Category> categories) {
        this.categories = categories;
        linearLayoutCategories.removeAllViews(); // Limpiar las vistas previas

        for (Category category : categories) {
            Button button = new Button(this);
            button.setText(category.getName()); // Con esto asignamos el nombre de la categoría
            button.setOnClickListener(v -> {
                // Manejamos la acción al hacer clic en el botón  recibir la info
                Intent intent = new Intent(CategorySelectionView.this, FilteredProductsView.class);
                intent.putExtra("CATEGORY_ID", category.getId());
                intent.putExtra("CATEGORY_NAME", category.getName());
                startActivity(intent);
            });
            linearLayoutCategories.addView(button); // Agrega el botón al LinearLayout
        }
    }

    @Override
    public void failureCategories(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
