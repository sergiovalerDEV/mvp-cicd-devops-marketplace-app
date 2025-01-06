package com.example.wallapop.product.edit_product.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide; // Importa Glide
import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.product.edit_product.ContractEditProduct;
import com.example.wallapop.product.edit_product.presenter.EditProductPresenter;

public class EditProductView extends AppCompatActivity implements ContractEditProduct.View {

    private EditText etDescripcion;
    private EditText etPrecio;
    private Spinner spinnerCategory; // Usamos Spinner para la categoría
    private EditText etPalabrasClave;
    private Button btnEdit;
    private ImageView ivProductImage;

    private Product product;
    private EditProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_view);

        presenter = new EditProductPresenter(this);
        initializeViews();
        loadProductData();
        setupEditButton();
        setupCategorySpinner();
    }

    private void initializeViews() {
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        spinnerCategory = findViewById(R.id.spinnerCategory); // Inicializar el Spinner
        etPalabrasClave = findViewById(R.id.etPalabrasClave);
        btnEdit = findViewById(R.id.btnEdit);
        ivProductImage = findViewById(R.id.ivProductImage);
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadProductData() {
        product = new Product();
        product.setId(getIntent().getIntExtra("id", -1));
        product.setDescripcion(getIntent().getStringExtra("descripcion"));
        product.setPrecio(getIntent().getStringExtra("precio"));
        product.setUserId(getIntent().getIntExtra("user_id", -1));
        product.setCategoryId(getIntent().getIntExtra("category_id", -1));
        product.setPalabrasClave(getIntent().getStringExtra("palabras_clave"));
        product.setImagen(getIntent().getStringExtra("imagen"));

        etDescripcion.setText(product.getDescripcion());
        etPrecio.setText(product.getPrecio());
        etPalabrasClave.setText(product.getPalabrasClave());

        //Excepción de recoger la imagen, al venir del S3 complica el uso de SV2
        //Solo aquí
        Glide.with(this)
                .load(product.getImagen())
                .into(ivProductImage);

        // Establecemos la posición del Spinner según el ID de categoría
        int categoryPosition = product.getCategoryId() - 1; // Asumimos que 1 = Hombre, 2 = Mujer, 3 = Niños
        spinnerCategory.setSelection(categoryPosition);
    }

    private void setupEditButton() {
        btnEdit.setOnClickListener(v -> editProduct());
    }

    private void editProduct() {
        String descripcion = etDescripcion.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        int categoryId = spinnerCategory.getSelectedItemPosition() + 1; // 0 = Hombre, 1 = Mujer, 2 = Niños
        String palabrasClave = etPalabrasClave.getText().toString().trim();

        if (descripcion.isEmpty() || precioStr.isEmpty() || palabrasClave.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        product.setDescripcion(descripcion);
        product.setPrecio(precioStr);
        product.setCategoryId(categoryId);
        product.setPalabrasClave(palabrasClave);

        presenter.editProduct(product);
    }

    @Override
    public void showEditSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showEditError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}
