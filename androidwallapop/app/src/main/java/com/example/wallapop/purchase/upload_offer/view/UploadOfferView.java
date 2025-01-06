package com.example.wallapop.purchase.upload_offer.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.lst_all_offers.view.AllOffersView;
import com.example.wallapop.purchase.upload_offer.ContractUploadOffer;
import com.example.wallapop.purchase.upload_offer.presenter.UploadOfferPresenter;
import com.example.wallapop.utils.UserSession;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class UploadOfferView extends AppCompatActivity implements ContractUploadOffer.View {
    private static final int GALLERY_REQUEST_CODE = 102; //Garantizamos el acceso a la galería
    //Junto con el permiso de Manifest

    private EditText editTextDescripcion, editTextPrecio, editTextPrecioOferta, editTextPalabrasClave;
    private Button buttonUpload, buttonGallery;
    private ImageView imageViewPreview;
    private DatePicker datePickerInicio, datePickerFin;
    private UploadOfferPresenter presenter;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_offer_view);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        editTextDescripcion = findViewById(R.id.etDescripcion);
        editTextPrecio = findViewById(R.id.etPrecio);
        editTextPrecioOferta = findViewById(R.id.etPrecioOferta);
        editTextPalabrasClave = findViewById(R.id.etPalabrasClave);
        buttonUpload = findViewById(R.id.btnUpload);
        buttonGallery = findViewById(R.id.btnGallery);
        imageViewPreview = findViewById(R.id.imagePreview);
        datePickerInicio = findViewById(R.id.datePickerInicio);
        datePickerFin = findViewById(R.id.datePickerFin);
        presenter = new UploadOfferPresenter(this, this);
        buttonUpload.setEnabled(false);
    }

    private void setupListeners() {
        buttonGallery.setOnClickListener(v -> openGallery());
        buttonUpload.setOnClickListener(v -> handleUpload());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageViewPreview.setImageURI(selectedImageUri);
                imageViewPreview.setVisibility(View.VISIBLE);
                buttonUpload.setEnabled(true);
            }
        }
    }

    private void handleUpload() {
        String descripcion = editTextDescripcion.getText().toString().trim();
        String precioStr = editTextPrecio.getText().toString().trim();
        String precioOfertaStr = editTextPrecioOferta.getText().toString().trim();
        String palabrasClave = editTextPalabrasClave.getText().toString().trim();

        if (descripcion.isEmpty() || precioStr.isEmpty() || precioOfertaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal precio, precioOferta;
        try {
            precio = new BigDecimal(precioStr.replace(",", "."));
            precioOferta = new BigDecimal(precioOfertaStr.replace(",", "."));
            if (precio.compareTo(BigDecimal.ZERO) <= 0 || precioOferta.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(this, "Los precios deben ser mayores que cero", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa precios válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Por favor, selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = UserSession.getInstance().getUserId();
        String fileName = "oferta_" + System.currentTimeMillis() + ".jpg";
        String imageUrl = "https://wallapuff2.s3.amazonaws.com/" + fileName;

        Date fechaInicio = getDateFromDatePicker(datePickerInicio);
        Date fechaFin = getDateFromDatePicker(datePickerFin);

        if (fechaFin.before(fechaInicio)) {
            Toast.makeText(this, "La fecha de fin debe ser posterior a la fecha de inicio", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadImageToS3(selectedImageUri, fileName, descripcion, precio, precioOferta, userId, palabrasClave, fechaInicio, fechaFin);
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void uploadImageToS3(Uri uri, String fileName, String descripcion, BigDecimal precio, BigDecimal precioOferta, int userId, String palabrasClave, Date fechaInicio, Date fechaFin) {
        new Thread(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                HttpURLConnection connection = (HttpURLConnection) new URL("https://wallapuff2.s3.amazonaws.com/" + fileName).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "image/jpeg");

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    connection.getOutputStream().write(buffer, 0, bytesRead);
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        String imageUrl = "https://wallapuff2.s3.amazonaws.com/" + fileName;
                        Offer offer = new Offer(
                                0, // temporary id, will be assigned by the server
                                imageUrl,
                                descripcion,
                                precio,
                                userId,
                                1, // Assuming default category_id is 1, adjust as needed
                                palabrasClave,
                                precioOferta,
                                fechaInicio,
                                fechaFin
                        );
                        presenter.uploadOffer(offer);
                    });
                } else {
                    Log.e("UploadOffer", "Error uploading image: " + connection.getResponseCode());
                }
                inputStream.close();
            } catch (Exception e) {
                Log.e("UploadOffer", "Exception: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public void successUpload() {
        Toast.makeText(this, "Oferta subida con éxito", Toast.LENGTH_SHORT).show();
        Intent ofertaCorrecta = new Intent(UploadOfferView.this, AllOffersView.class);
        startActivity(ofertaCorrecta);
        finish();
    }

    @Override
    public void failureUpload(String err) {
        Toast.makeText(this, "Error al subir la oferta: " + err, Toast.LENGTH_SHORT).show();
    }
}