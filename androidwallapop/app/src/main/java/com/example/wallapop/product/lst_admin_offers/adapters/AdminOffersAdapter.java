package com.example.wallapop.product.lst_admin_offers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.edit_offer.view.EditOfferView;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdminOffersAdapter extends RecyclerView.Adapter<AdminOffersAdapter.ViewHolder> {
    private final ArrayList<Offer> offers;
    private final Context context;
    private final SimpleDateFormat dateFormat;

    public AdminOffersAdapter(Context context, ArrayList<Offer> offers) {
        this.context = context;
        this.offers = offers;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offers.get(position);

        holder.tvDescripcion.setText(offer.getDescripcion());
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "Original Price: €%.2f", offer.getPrecio()));
        holder.tvPrecioOferta.setText(String.format(Locale.getDefault(), "Offer Price: €%.2f", offer.getPrecioOferta()));
        holder.tvFechaInicioOferta.setText(String.format("Start Date: %s", dateFormat.format(offer.getFechaInicioOferta())));
        holder.tvFechaFinOferta.setText(String.format("End Date: %s", dateFormat.format(offer.getFechaFinOferta())));

        String imageUrl = offer.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivOfferImage)
                    .build()
                    .load();
        }

        holder.btnEditOffer.setOnClickListener(v -> editOffer(offer));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    private void editOffer(Offer offer) {
        Intent intent = new Intent(context, EditOfferView.class);
        intent.putExtra("offer_id", offer.getId());
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescripcion;
        public TextView tvPrecio;
        public TextView tvPrecioOferta;
        public TextView tvFechaInicioOferta;
        public TextView tvFechaFinOferta;
        public ImageView ivOfferImage;
        public Button btnEditOffer;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvPrecioOferta = itemView.findViewById(R.id.tvPrecioOferta);
            tvFechaInicioOferta = itemView.findViewById(R.id.tvFechaInicioOferta);
            tvFechaFinOferta = itemView.findViewById(R.id.tvFechaFinOferta);
            ivOfferImage = itemView.findViewById(R.id.ivOfferImage);
            btnEditOffer = itemView.findViewById(R.id.btnEditOffer);
        }
    }
}