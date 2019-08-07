package com.example.Restinpeace.farmerassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder>{

    private List<Price_list> list;
    private Context context;

    public PriceAdapter(List<Price_list> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.commodity_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Price_list price_list = list.get(position);
        holder.state.setText(price_list.getState());
        holder.district.setText(price_list.getDistrict());
        holder.max_price.setText(price_list.getMax_price());
        holder.min_price.setText(price_list.getMin_price());
        holder.variety.setText(price_list.getVariety());
        holder.market.setText(price_list.getMarket());
        holder.commodity.setText(price_list.getCommodity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView state;
        public TextView district;
        public TextView market;
        public TextView commodity;
        public TextView variety;
        public TextView min_price;
        public TextView max_price;
        public ViewHolder(View itemView) {
            super(itemView);

            state= (TextView)itemView.findViewById(R.id.state);
            district = (TextView)itemView.findViewById(R.id.district);
            market = (TextView)itemView.findViewById(R.id.market);
            commodity = (TextView)itemView.findViewById(R.id.commodity);
            variety = (TextView)itemView.findViewById(R.id.variety);
            min_price = (TextView)itemView.findViewById(R.id.min_price);
            max_price = (TextView)itemView.findViewById(R.id.max_price);
        }
    }
}
