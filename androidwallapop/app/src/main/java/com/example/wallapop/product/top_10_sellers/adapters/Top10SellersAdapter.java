package com.example.wallapop.product.top_10_sellers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.User;
import com.example.wallapop.utils.UserSession;

import java.util.ArrayList;

public class Top10SellersAdapter extends RecyclerView.Adapter<Top10SellersAdapter.ViewHolder> {
    private final ArrayList<User> topSellers;
    private final Context context;
    private final UserSession userSession;

    public Top10SellersAdapter(Context context, ArrayList<User> topSellers) {
        this.context = context;
        this.topSellers = topSellers;
        this.userSession = UserSession.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_seller, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User seller = topSellers.get(position);
        holder.tvSellerEmail.setText((position + 1) + ". " + seller.getEmail());
        holder.tvProductCount.setText("Total productos: " + seller.getTotal_productos());
    }

    @Override
    public int getItemCount() {
        return topSellers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSellerEmail;
        public TextView tvProductCount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSellerEmail = itemView.findViewById(R.id.tvSellerEmail);
            tvProductCount = itemView.findViewById(R.id.tvProductCount);
        }
    }
}