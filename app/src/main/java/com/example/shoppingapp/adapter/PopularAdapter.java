package com.example.shoppingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.PopularModel;
import com.example.shoppingapp.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>  {

    private Context context;
    private List<PopularModel> popularModelsList;

    public PopularAdapter(Context context, List<PopularModel> popularModelsList) {
        this.context = context;
        this.popularModelsList = popularModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(popularModelsList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularModelsList.get(position).getName());
        holder.rating.setText(popularModelsList.get(position).getRating());
        holder.description.setText(popularModelsList.get(position).getDescription());
        holder.discount.setText(popularModelsList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return popularModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,description, rating, discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg = itemView.findViewById(R.id.popimage);
            name = itemView.findViewById(R.id.popname);
            description = itemView.findViewById(R.id.popdesc);
            rating = itemView.findViewById(R.id.poprating);
            discount = itemView.findViewById(R.id.popdescount);
        }
    }
}
