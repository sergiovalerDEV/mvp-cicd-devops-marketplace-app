package com.example.wallapop.product.top_10_products;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;
import java.util.List;

public interface ContractTop10Products {
    interface Presenter {
        void loadTop10Products(); // Cargar el top 10 de productos
    }

    interface Model {
        interface OnLoadTop10ProductsListener {
            void onTop10ProductsLoaded(List<Product> products); // Cambiado a List<Product>
            void onFailure(String error); // Error al cargar datos
        }

        void fetchTop10Products(OnLoadTop10ProductsListener listener); // Método para obtener el top 10 de productos
    }

    interface View {
        void showTop10Products(ArrayList<Product> topProducts); // Muestra el top 10 en la vista
        void showError(String error); // Muestra un mensaje de error
    }
}
