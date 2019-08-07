package com.example.Restinpeace.farmerassistant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyerDetail extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_detail);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        setTitle("Fill Up Your Detail");


        final String farmerPhone= getIntent().getStringExtra("phone");
        final String pinCode = getIntent().getStringExtra("pinCode");
        final String curPrice = getIntent().getStringExtra("curPrice");

        final EditText name = (EditText)findViewById(R.id.BuyerName);
        final EditText address=(EditText)findViewById(R.id.BuyerAddress);
        final EditText phoneNumber=(EditText)findViewById(R.id.Buyerphone);
        final EditText pincode = (EditText)findViewById(R.id.BuyerPincode);
        final EditText price = (EditText)findViewById(R.id.BuyerPrice);
        Button submit = (Button)findViewById(R.id.BuyerSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String BuyerName = name.getText().toString();
                String BuyerAddress=address.getText().toString();
                String BuyerPincode = pincode.getText().toString();
                final String BuyerPhone = phoneNumber.getText().toString();
                final String BuyerPrice = price.getText().toString();


                if (BuyerName.isEmpty() || BuyerAddress.isEmpty() || BuyerPhone.isEmpty() || BuyerPincode.isEmpty()) {
                    Toast.makeText(BuyerDetail.this,"Pleas fill your details",Toast.LENGTH_SHORT).show();
                }else{

                    final int cur1 = Integer.parseInt(BuyerPrice);
                    final int cur2 = Integer.parseInt(curPrice);




                    if(cur1 > cur2){

                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("farmers");
                        mDatabaseReference.orderByChild("pincode").equalTo(pinCode).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                        String key=datas.getKey();

                                                                               /* Log.d("FarmerPhone", farmerPhone);
                                        Toast.makeText(BuyerDetail.this, "Your details has been send, please wait for his/her reply. \n Thank you!", Toast.LENGTH_LONG).show();
                                        finish();*/
                                       /* String name=datas.child("name").getValue().toString();
                                        String priorities=datas.child("priority").getValue().toString();
                                        /*ref.child(key).child("done").setValue(isdone);
                                        ref.child(key).child("name").setValue(name);
                                        ref.child(key).child("priority").setValue(priorities);*/

                                       mDatabaseReference.child(key).child("wprice").setValue(BuyerPrice);
                                        mDatabaseReference.child(key).child("wphone").setValue(BuyerPhone);
                                        mDatabaseReference.child(key).child("wname").setValue(BuyerName);


                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                        Toast.makeText(v.getContext(), "Congratulations! You are the winner of this auction :)", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(v.getContext(), "Sorry", Toast.LENGTH_LONG).show();

                    }



                  /*  String message = " Hey!,  " + cur2 + " is interested in your product at price."+ lol + "/kg \nHis address " + BuyerAddress + " \npincode:- " + BuyerPincode +
                            "\nHis contact is " + BuyerPhone + "\n.";*/


                }
            }
        });




        /*setTitle("Fill Up Your Detail");

        final String farmerPhone= getIntent().getStringExtra("phone");
        //Toast.makeText(BuyerDetail.this,farmerPhone,Toast.LENGTH_LONG).show();

        final EditText name = (EditText)findViewById(R.id.BuyerName);
        final EditText address=(EditText)findViewById(R.id.BuyerAddress);
        final EditText phoneNumber=(EditText)findViewById(R.id.Buyerphone);
        final EditText pincode = (EditText)findViewById(R.id.BuyerPincode);
        Button submit = (Button)findViewById(R.id.BuyerSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BuyerName = name.getText().toString();
                String BuyerAddress=address.getText().toString();
                String BuyerPincode = pincode.getText().toString();
                String BuyerPhone = phoneNumber.getText().toString();
                if (BuyerName.isEmpty() || BuyerAddress.isEmpty() || BuyerPhone.isEmpty() || BuyerPincode.isEmpty()) {
                    Toast.makeText(BuyerDetail.this,"Pleas fill your details",Toast.LENGTH_SHORT).show();
                }else{
                    String message = " Hey!,  " + BuyerName + " is interested in your product. \nHere is his address " + BuyerAddress + " \npincode:- " + BuyerPincode +
                            "\nHis contact number is " + BuyerPhone + "\n Try to contact him as early as possible.";

                    SmsManager.getDefault().sendTextMessage(farmerPhone, null, message, null, null);

                    Log.d("FarmerPhone", farmerPhone);
                    Toast.makeText(BuyerDetail.this, "Your details has been send, please wait for his/her reply. \n Thank you!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permission [],int [] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Thanks for permitting",Toast.LENGTH_LONG).show();
                }
                else {


                    Toast.makeText(this,":( You did not permit!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
