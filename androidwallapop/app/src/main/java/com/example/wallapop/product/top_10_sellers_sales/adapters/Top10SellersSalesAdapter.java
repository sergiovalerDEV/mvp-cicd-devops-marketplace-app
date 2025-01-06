package com.example.wallapop.product.top_10_sellers_sales.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.User;

import java.util.ArrayList;

public class Top10SellersSalesAdapter extends RecyclerView.Adapter<Top10SellersSalesAdapter.ViewHolder> {
    private final ArrayList<User> topSellers;
    private final Context context;

    public Top10SellersSalesAdapter(Context context, ArrayList<User> topSellers) {
        this.context = context;
        this.topSellers = topSellers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_seller_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User seller = topSellers.get(position);
        holder.tvSellerEmail.setText((position + 1) + ". " + seller.getEmail());
        holder.tvTotalSales.setText("Ventas totales: " + seller.getTotal_ventas());
    }

    @Override
    public int getItemCount() {
        return topSellers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSellerEmail;
        public TextView tvTotalSales;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSellerEmail = itemView.findViewById(R.id.tvSellerEmail);
            tvTotalSales = itemView.findViewById(R.id.tvTotalSales);
        }
    }
}