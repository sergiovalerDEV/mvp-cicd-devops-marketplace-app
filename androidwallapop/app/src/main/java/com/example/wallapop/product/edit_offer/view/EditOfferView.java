package com.example.wallapop.product.edit_offer.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.edit_offer.ContractEditOffer;
import com.example.wallapop.product.edit_offer.presenter.EditOfferPresenter;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class EditOfferView extends AppCompatActivity implements ContractEditOffer.View {

    private EditText etDescripcion, etPrecio, etPrecioOferta, etPalabrasClave;
    private DatePicker dpFechaInicioOferta, dpFechaFinOferta;
    private ImageView ivOfferImage;
    private Button btnSaveOffer;
    private Spinner spinnerCategory;
    private EditOfferPresenter presenter;
    private int offerId;
    private String imageUrl;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer_view);

        initializeViews();
        setupCategorySpinner();
        presenter = new EditOfferPresenter(this);

        offerId = getIntent().getIntExtra("offer_id", -1);
        if (offerId != -1) {
            presenter.getOfferDetails(offerId);
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el ID de la oferta", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSaveOffer.setOnClickListener(v -> saveOffer());
    }

    private void initializeViews() {
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        etPrecioOferta = findViewById(R.id.etPrecioOferta);
        dpFechaInicioOferta = findViewById(R.id.dpFechaInicioOferta);
        dpFechaFinOferta = findViewById(R.id.dpFechaFinOferta);
        etPalabrasClave = findViewById(R.id.etPalabrasClave);
        ivOfferImage = findViewById(R.id.ivOfferImage);
        btnSaveOffer = findViewById(R.id.btnSaveOffer);
        spinnerCategory = findViewById(R.id.spinnerCategory);
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    @Override
    public void showOfferDetails(Offer offer) {
        if (offer != null) {
            etDescripcion.setText(offer.getDescripcion());
            etPrecio.setText(offer.getPrecio().toString());
            etPrecioOferta.setText(offer.getPrecioOferta().toString());
            spinnerCategory.setSelection(offer.getCategoryId() - 1);
            etPalabrasClave.setText(offer.getPalabrasClave());

            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(offer.getFechaInicioOferta());
            dpFechaInicioOferta.updateDate(calInicio.get(Calendar.YEAR), calInicio.get(Calendar.MONTH), calInicio.get(Calendar.DAY_OF_MONTH));

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(offer.getFechaFinOferta());
            dpFechaFinOferta.updateDate(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH), calFin.get(Calendar.DAY_OF_MONTH));

            imageUrl = offer.getImagen();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                new _SV2_INF_THE_BEST.Builder(imageUrl, ivOfferImage)
                        .build()
                        .load();
            }

            userId = offer.getUserId();
        } else {
            Toast.makeText(this, "Error: No se pudo cargar los detalles de la oferta", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOffer() {
        String descripcion = etDescripcion.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        String precioOfertaStr = etPrecioOferta.getText().toString().trim();
        int categoryId = spinnerCategory.getSelectedItemPosition() + 1;
        String palabrasClave = etPalabrasClave.getText().toString().trim();

        if (descripcion.isEmpty() || precioStr.isEmpty() || precioOfertaStr.isEmpty() || palabrasClave.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(precioStr);
            BigDecimal precioOferta = new BigDecimal(precioOfertaStr);
            Date fechaInicio = getDateFromDatePicker(dpFechaInicioOferta);
            Date fechaFin = getDateFromDatePicker(dpFechaFinOferta);

            Offer updatedOffer = new Offer(offerId, imageUrl, descripcion, precio, userId, categoryId,
                    palabrasClave, precioOferta, fechaInicio, fechaFin);
            presenter.editOffer(updatedOffer);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error: Los precios deben ser números válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public void showEditSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showEditError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}