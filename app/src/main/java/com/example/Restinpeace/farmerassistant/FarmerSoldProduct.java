package com.example.Restinpeace.farmerassistant;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FarmerSoldProduct extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<SoldProductList> productList;
    private SoldProductAdapter mAdapter;
    private DatabaseReference productReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_sold_product);
        setTitle("Your sold products");

        final String farmerPhone = getIntent().getStringExtra("phone");

        Log.d("phone",farmerPhone);

        productList = new ArrayList<>();

        productReference = FirebaseDatabase.getInstance().getReference().child("farmers");

        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Product_detail productDetail = dataSnapshot1.getValue(Product_detail.class);
                    if (productDetail.getPhone().contains(farmerPhone)) {
                        SoldProductList product_Detail = new SoldProductList(productDetail.getName(), productDetail.getState(),
                                productDetail.getDistrict(), productDetail.getPincode(),
                                productDetail.getCommodity(), productDetail.getPrice(),
                                productDetail.getWeight(), productDetail.getPhone(),
                                productDetail.isSold(), dataSnapshot1.getKey() ,
                                productDetail.getmImageUrl(),productDetail.getTime(),productDetail.getwprice(),

                                productDetail.getName(),productDetail.getPhone());
                        productList.add(product_Detail);
                    }

                }

                Log.d("Product list",productList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAdapter = new SoldProductAdapter(this,productList);
        recyclerView = (RecyclerView)findViewById(R.id.Product_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                productList.clear();
                mAdapter.notifyDataSetChanged();

                productReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            Product_detail productDetail = dataSnapshot1.getValue(Product_detail.class);
                            if (productDetail.getPhone().contains(farmerPhone)) {
                                SoldProductList product_Detail = new SoldProductList(productDetail.getName(), productDetail.getState(),
                                        productDetail.getDistrict(), productDetail.getPincode(),
                                        productDetail.getCommodity(), productDetail.getPrice(),
                                        productDetail.getWeight(), productDetail.getPhone(),
                                        productDetail.isSold(), dataSnapshot1.getKey() , productDetail.getmImageUrl(),productDetail.getTime(),
                                        productDetail.getwprice(),productDetail.getwname(),productDetail.getwphone()
                                        );
                                productList.add(product_Detail);
                            }

                        }
                        mAdapter.notifyDataSetChanged();
                        Log.d("Product list",productList.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);

            }
        });


    }
}
