package com.example.wallapop.product.upload_product.presenter;

import android.content.Context;
import android.content.Intent;
import com.example.wallapop.beans.Product;
import com.example.wallapop.product.upload_product.model.UploadProductModel;
import com.example.wallapop.purchase.lst_user_products.view.LstUserProducts;
import com.example.wallapop.product.upload_product.ContractUploadProduct;
import com.example.wallapop.utils.UserSession;

public class UploadProductPresenter implements ContractUploadProduct.Presenter, ContractUploadProduct.Model.OnUploadProductListener {
    private final ContractUploadProduct.View view;
    private final ContractUploadProduct.Model model;
    private final Context context;

    public UploadProductPresenter(Context context, ContractUploadProduct.View view) {
        this.context = context;
        this.view = view;
        this.model = new UploadProductModel();
    }

    @Override
    public void uploadProduct(Product product) {
        // Obtener el role_id desde UserSession
        Integer currentUserRoleId = UserSession.getInstance().getRole_id();

        // Verificamos si el usuario tiene el rol de vendedor (suponiendo que el rol de vendedor es 1)
        if (currentUserRoleId == null || currentUserRoleId != 1) {
            view.failureUpload("No tienes permisos para subir productos. Solo los vendedores pueden realizar esta acción.");
            return;
        }

        // Si tiene el rol correcto, proceder con la subida
        model.uploadProduct(product, this);
    }

    @Override
    public void onSuccess() {
        view.successUpload();

        //Iremos a subir producto cuando estemos logueados
        Intent intent = new Intent(context, LstUserProducts.class);
        context.startActivity(intent);
    }

    @Override
    public void onFailure(String err) {
        view.failureUpload(err);
    }
}
