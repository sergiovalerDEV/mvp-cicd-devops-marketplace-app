package com.example.wallapop.product.upload_product.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.purchase.lst_user_products.view.LstUserProducts;
import com.example.wallapop.product.upload_product.ContractUploadProduct;
import com.example.wallapop.product.upload_product.presenter.UploadProductPresenter;
import com.example.wallapop.utils.UserSession;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class UploadProductM extends AppCompatActivity implements ContractUploadProduct.View {
    private static final int GALLERY_REQUEST_CODE = 102; // Código para seleccionar imágenes de la galería

    private EditText editTextDescripcion, editTextPrecio, editTextPalabrasClave; // EditText para palabras clave
    private Button buttonUpload, buttonGallery; // Botones para subir y seleccionar imagen
    private ImageView imageViewPreview; // Vista previa de la imagen
    private UploadProductPresenter presenter;

    private Uri selectedImageUri; // Almacena la URI de la imagen seleccionada
    private Spinner spinnerCategory; // Spinner para categorías
    private int categoryId; // ID de categoría seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product_m); // Layout de la actividad

        initializeViews(); // Inicializa las vistas
        setupListeners(); // Configura los listeners
        setupSpinner(); // Configura el Spinner para las categorías
    }

    private void initializeViews() {
        editTextDescripcion = findViewById(R.id.etDescripcion);
        editTextPrecio = findViewById(R.id.etPrecio);
        editTextPalabrasClave = findViewById(R.id.etPalabrasClave); // Inicializa el EditText para palabras clave
        buttonUpload = findViewById(R.id.btnUpload);
        buttonGallery = findViewById(R.id.btnCamera); // Cambia el nombre a btnGallery
        imageViewPreview = findViewById(R.id.imagePreview);
        spinnerCategory = findViewById(R.id.spinnerCategory); // Inicializa el Spinner
        presenter = new UploadProductPresenter(this, this);
        buttonUpload.setEnabled(false); // Desactiva el botón de subir al inicio
    }

    private void setupListeners() {
        buttonGallery.setOnClickListener(v -> openGallery()); // Listener para el botón de galería
        buttonUpload.setOnClickListener(v -> handleUpload()); // Listener para el botón de subir
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = position + 1; // Las posiciones en el spinner inician en 0, sumamos 1 para el ID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryId = 0; // Valor por defecto
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE); // Abre la galería
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Obtiene la URI de la imagen seleccionada
            if (selectedImageUri != null) {
                imageViewPreview.setImageURI(selectedImageUri); // Muestra la imagen en la vista previa
                imageViewPreview.setVisibility(View.VISIBLE); // Hace visible la imagen
                buttonUpload.setEnabled(true); // Habilita el botón de subir
            }
        }
    }

    private void handleUpload() {
        String descripcion = editTextDescripcion.getText().toString().trim(); // Obtiene la descripción
        if (descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una descripción", Toast.LENGTH_SHORT).show();
            return;
        }

        String precioStr = editTextPrecio.getText().toString().trim(); // Obtiene el precio
        if (precioStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un precio", Toast.LENGTH_SHORT).show();
            return;
        }

        float precio;
        try {
            // Reemplazar la coma con un punto y convertir el precio a float
            precioStr = precioStr.replace(",", ".");
            precio = Float.parseFloat(precioStr); // Convierte a float
            if (precio <= 0) {
                Toast.makeText(this, "El precio debe ser mayor que cero", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String palabrasClave = editTextPalabrasClave.getText().toString().trim(); // Obtiene las palabras clave
        Log.d("UploadProduct", "Palabras clave: " + palabrasClave); // Verificar palabras clave

        if (selectedImageUri == null) {
            Toast.makeText(this, "Por favor, selecciona una imagen primero", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = UserSession.getInstance().getUserId(); // Obtiene el ID del usuario
        String fileName = "producto_" + System.currentTimeMillis() + ".jpg"; // Nombre del archivo
        String imageUrl = "https://wallapuff2.s3.amazonaws.com/" + fileName; // URL del archivo en S3

        // Subir imagen a S3
        uploadImageToS3(selectedImageUri, fileName, descripcion, precio, userId, palabrasClave);
    }

    private void uploadImageToS3(Uri uri, String fileName, String descripcion, float precio, int userId, String palabrasClave) {
        new Thread(() -> {
            try {
                // Obtiene la dirección del archivo
                InputStream inputStream = getContentResolver().openInputStream(uri);
                HttpURLConnection connection = (HttpURLConnection) new URL("https://wallapuff2.s3.amazonaws.com/" + fileName).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "image/jpeg");

                // Lee la imagen y escribe en la conexión
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    connection.getOutputStream().write(buffer, 0, bytesRead);
                }

                // Verifica la respuesta de la conexión
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        String imageUrl = "https://wallapuff2.s3.amazonaws.com/" + fileName;
                        // Convert float precio to String and create Product with the correct constructor
                        String precioStr = String.format(Locale.US, "%.2f", precio); // Format precio as String with 2 decimal places
                        // Using the second constructor: (int id, String imagen, String descripcion, String precio, int userId, int categoryId, String palabrasClave)
                        Product product = new Product(
                                0, // temporary id, will be assigned by the server
                                imageUrl,
                                descripcion,
                                precioStr,
                                userId,
                                categoryId,
                                palabrasClave
                        );
                        presenter.uploadProduct(product);
                        finish();
                    });
                } else {
                    Log.e("UploadProduct", "Error uploading image: " + connection.getResponseCode());
                }
                inputStream.close();
            } catch (Exception e) {
                Log.e("UploadProduct", "Exception: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public void successUpload() {
        Toast.makeText(this, "Producto subido con éxito", Toast.LENGTH_SHORT).show();

        Intent productoCorrecto = new Intent(UploadProductM.this, LstUserProducts.class);
        startActivity(productoCorrecto);
    }

    @Override
    public void failureUpload(String err) {
        Toast.makeText(this, "Error al subir el producto: " + err, Toast.LENGTH_SHORT).show();
    }
}