package com.example.Restinpeace.farmerassistant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Buy_Seeds extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editText;
    private TextView searchButton;

    private SeedAdapter adapter;
    private List<Product_detail> product_list;
    private DatabaseReference productReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__seeds);
        setTitle("Buy Commodity");

        editText = (EditText)findViewById(R.id.editextSearch);
        searchButton = (TextView)findViewById(R.id.ButtonSearch);

        recyclerView = (RecyclerView) findViewById(R.id.Farmer_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        product_list = new ArrayList<>();
        adapter = new SeedAdapter(Buy_Seeds.this,product_list);
        recyclerView.setAdapter(adapter);


        productReference = FirebaseDatabase.getInstance().getReference().child("farmers");

        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Product_detail productDetail = dataSnapshot1.getValue(Product_detail.class);
                    if (!productDetail.isSold()) {
                        product_list.add(productDetail);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();

                List<Product_detail> list = new ArrayList<>();

                for (Product_detail d : product_list){
                    if (d.getCommodity().contains(name)){
                        list.add(d);
                    }
                }

                adapter = new SeedAdapter(Buy_Seeds.this,list);
                recyclerView.setAdapter(adapter);

            }
        });

    }
}
