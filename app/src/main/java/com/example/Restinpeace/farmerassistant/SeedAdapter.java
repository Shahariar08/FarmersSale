package com.example.Restinpeace.farmerassistant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.ViewHolder> {

    private Context context;
    private List<Product_detail> product_detailList;

    public SeedAdapter(Context context, List<Product_detail> product_detailList) {
        this.context = context;
        this.product_detailList = product_detailList;
    }

    @NonNull
    @Override
    public SeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.farmer_seed_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedAdapter.ViewHolder holder, int position) {
        Product_detail detail = product_detailList.get(position);
        holder.name.setText(detail.getName());
        holder.state.setText(detail.getState());
        holder.district.setText(detail.getDistrict());
        holder.pincode.setText(detail.getPincode());
        holder.commodity.setText(detail.getCommodity());
        holder.price.setText(detail.getPrice());
        holder.weight.setText(detail.getWeight());
        holder.phone.setText(detail.getPhone());
        holder.wprice.setText(detail.getwprice());
        holder.wname.setText(detail.getwname());
        holder.wphone.setText(detail.getwphone());

        Calendar rightNow = Calendar.getInstance();
        int cur = rightNow.get(Calendar.HOUR_OF_DAY);
        String now = detail.getTime();
        if(now == null) now = "50";
        int lol = Integer.parseInt(now);
        cur = lol - cur;
        now = Integer.toString(cur);

        holder.time.setText(now);



        Picasso.with(context)
                .load(detail.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return product_detailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView state;
        public TextView district;
        public TextView pincode;
        public TextView commodity;
        public TextView price;
        public TextView weight;
        public TextView phone;
        public TextView wprice;
        public TextView wname;
        public TextView wphone;
        public TextView time;

        public Button buy;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.Farmer_Name);
            state = (TextView)itemView.findViewById(R.id.Farmer_State);
            district = (TextView)itemView.findViewById(R.id.Farmer_District);
            pincode = (TextView)itemView.findViewById(R.id.Farmer_Pincode);
            commodity = (TextView)itemView.findViewById(R.id.Farmer_Commodity);
            price = (TextView)itemView.findViewById(R.id.Farmer_Price);
            weight =(TextView)itemView.findViewById(R.id.Product_Weight);
            phone = (TextView)itemView.findViewById(R.id.phone);
            wprice = (TextView) itemView.findViewById(R.id.wprice);
            wname = (TextView) itemView.findViewById(R.id.wname);
            time = (TextView) itemView.findViewById(R.id.time);


            wphone = (TextView) itemView.findViewById(R.id.wphone);
            imageView = (ImageView)itemView.findViewById(R.id.mImageUrl);
            buy = (Button)itemView.findViewById(R.id.Buy_Product);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start a new layout which buyer has to fill a form about his detail

                    Intent intent = new Intent(v.getContext(),BuyerDetail.class);


                    String phoneNumber = phone.getText().toString();
                    intent.putExtra("phone",phoneNumber);

                   String pinCode = pincode.getText().toString();
                   intent.putExtra("pinCode",pinCode);

                   String curPrice = wprice.getText().toString();
                   intent.putExtra("curPrice",curPrice);

                    v.getContext().startActivity(intent);

                }
            });
        }
    }
}
