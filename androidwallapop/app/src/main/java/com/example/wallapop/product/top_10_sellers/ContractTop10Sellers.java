package com.example.wallapop.product.top_10_sellers;

import com.example.wallapop.beans.User;
import java.util.ArrayList;

public interface ContractTop10Sellers {

    interface Presenter {
        void loadTop10Sellers(); // Cargar el top 10 de vendedores
    }

    interface Model {
        interface OnLoadTop10SellersListener {
            void onTop10SellersLoaded(ArrayList<User> topSellers); // Éxito al cargar datos
            void onFailure(String error); // Error al cargar datos
        }

        void fetchTop10Sellers(OnLoadTop10SellersListener listener); // Método para obtener el top 10 de vendedores
    }

    interface View {
        void showTop10Sellers(ArrayList<User> topSellers); // Muestra el top 10 en la vista
        void showError(String error); // Muestra un mensaje de error
    }
}
