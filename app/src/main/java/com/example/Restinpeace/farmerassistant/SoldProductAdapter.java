package com.example.Restinpeace.farmerassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class SoldProductAdapter extends RecyclerView.Adapter<SoldProductAdapter.ViewHolder> {

    private Context context;
    private List<SoldProductList> lists;

    public SoldProductAdapter(Context context, List<SoldProductList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public SoldProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.farmer_sold_product_detail,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoldProductList list = lists.get(position);
        holder.name.setText(list.getName());
        holder.state.setText(list.getState());
        holder.district.setText(list.getDistrict());
        holder.pincode.setText(list.getPincode());
        holder.commodity.setText(list.getCommodity());
        holder.price.setText(list.getPrice());
        holder.weight.setText(list.getWeight());
        holder.key.setText(list.getKey());
        holder.phone.setText(list.getPhone());
        holder.wprice.setText(list.getwprice());
        holder.wname.setText(list.getwname());
        holder.wphone.setText(list.getwphone());

        Calendar rightNow = Calendar.getInstance();
        int cur = rightNow.get(Calendar.HOUR_OF_DAY);
        String now = list.getTime();
        if(now == null) now = "50";
        int lol = Integer.parseInt(now);
        cur = lol - cur;
        now = Integer.toString(cur);

        holder.time.setText(now);


        Picasso.with(context)
                .load(list.getUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        if (list.isSold()){
            String purchased = "Purchased";
            holder.status.setText(purchased);
        }
        else{
            String notSold = "Not Sold";
            holder.status.setText(notSold);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SeedAdapter adapter;
        public ImageView imageView;
        DatabaseReference mDatabaseReference;
        public TextView name;
        public TextView state;
        public TextView district;
        public TextView pincode;
        public TextView commodity;
        public TextView price;
        public TextView weight;
        public TextView key;
        public TextView phone;
        public TextView status;
        public TextView time;
        public TextView wprice;
        public TextView wname;
        public TextView wphone;

        public Button SoldProduct;
        public Button DeleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.FarmerName);
            state = (TextView)itemView.findViewById(R.id.FarmerState);
            district = (TextView)itemView.findViewById(R.id.FarmerDistrict);
            pincode = (TextView)itemView.findViewById(R.id.FarmerPincode);
            commodity = (TextView)itemView.findViewById(R.id.FarmerCommodity);
            price = (TextView)itemView.findViewById(R.id.FarmerPrice);
            weight =(TextView)itemView.findViewById(R.id.ProductWeight);
            key = (TextView)itemView.findViewById(R.id.key);
            phone = (TextView)itemView.findViewById(R.id.FarmerPhone);
            status = (TextView)itemView.findViewById(R.id.status);
            wprice = itemView.findViewById(R.id.wprice);
            wname = itemView.findViewById(R.id.wname);
            wphone = itemView.findViewById(R.id.wphone);

            time = itemView.findViewById(R.id.FarmerTime);

            SoldProduct = (Button)itemView.findViewById(R.id.SoldProduct);
            DeleteProduct = (Button)itemView.findViewById(R.id.DeleteProduct);

            imageView = (ImageView)itemView.findViewById(R.id.mImageUrl);


            DeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(),key.getText().toString(),Toast.LENGTH_LONG).show();
                    String ProductKey = key.getText().toString();
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    mDatabaseReference.child("farmers").child(ProductKey).setValue(null);
                    Toast.makeText(v.getContext(), "Product is Deleted.\nRefresh the page! :)", Toast.LENGTH_LONG).show();
                    // getListView().invalidate();

                }
            });

            if(status.getText().toString().contains("Purchased")){
                SoldProduct.setEnabled(false);
            }

            else {
                SoldProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(),key.getText().toString(),Toast.LENGTH_LONG).show();
                        String ProductKey = key.getText().toString();
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                        mDatabaseReference.child("farmers").child(ProductKey).child("sold").setValue(true);
                        Toast.makeText(v.getContext(), "Product is updated.\nThank you! :)", Toast.LENGTH_LONG).show();
                        SoldProduct.setEnabled(false);
                        status.setText("Purchased");
                    }
                });
            }

        }
    }
}
