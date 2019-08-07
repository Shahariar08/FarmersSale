
package com.example.Restinpeace.farmerassistant;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.Arrays;
import java.util.Calendar;

public class Sell extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;

    private static final int PICK_IMAGE_REQUEST = 1;

    public static final int RC_SIGN_IN =1;
    private EditText name;
    private EditText state;
    private EditText district;
    private EditText pincode;
    private EditText commodity;
    private EditText price;
    private EditText weight;
    private EditText time;
    private TextView key;
    private Button submit;
    private FirebaseUser user;

    private Button mButtonChooseImage;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private ImageView mImageView;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        setTitle("Sell Your Seeds");
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mProgressBar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("farmers");



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        name=(EditText) findViewById(R.id.name);
        state=(EditText)findViewById(R.id.farmer_state);
        district = (EditText)findViewById(R.id.farmer_district);
        pincode=(EditText)findViewById(R.id.farmer_pincode);
        commodity = (EditText)findViewById(R.id.farmer_commodity);
        price = (EditText)findViewById(R.id.farmer_amount);
        weight = (EditText)findViewById(R.id.farmer_weight);
        time = (EditText) findViewById(R.id.time);
        key = (TextView) findViewById(R.id.key);

        submit=(Button)findViewById(R.id.submit);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("farmers");

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user!=null){
                    //Toast.makeText(Sell.this,"Welcome !",Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.PhoneBuilder().build())
                                    ).build(),RC_SIGN_IN);
                }
            }
        };
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
           /* public void onClick(View v) {
                final String fName=name.getText().toString();
                final String fState=state.getText().toString();
                final String fDistrict=district.getText().toString();
                final String fPincode=pincode.getText().toString();
                final String fCommodity=commodity.getText().toString();
                final String fPrice=price.getText().toString();
                final String fWeight=weight.getText().toString();
                final String fImageUrl = taskSnapshot.getDownloadUrl().toString();


                Product_detail product_detail = new Product_detail(fName,fState,fDistrict,fPincode,
                                                                        fCommodity,fPrice,fWeight,false,user.getPhoneNumber(),);
                mFirebaseDatabaseReference.push().setValue(product_detail);

                Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                name.setText("");
                state.setText("");
                district.setText("");
                pincode.setText("");
                commodity.setText("");
                price.setText("");
                weight.setText("");

            } //copy this */

            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Sell.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                Toast.makeText(Sell.this, "Signed In", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(Sell.this, "Signed In Failed", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
        else if(resultCode == RESULT_CANCELED){
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sell_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==R.id.Sign_Out){
            AuthUI.getInstance().signOut(this);
            finish();
            return true;
        }
        else if (id==R.id.sold_products){
            Intent intent = new Intent(Sell.this,FarmerSoldProduct.class);
            intent.putExtra("phone",user.getPhoneNumber());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN){
//            if (resultCode == RESULT_OK){
//                Toast.makeText(Sell.this, "Signed In", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                //Toast.makeText(Sell.this, "Signed In Failed", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//        }
//    }
    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);


                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoLink = uri.toString();

                                    final String fName=name.getText().toString();
                                    final String fState=state.getText().toString();
                                    final String fDistrict=district.getText().toString();
                                    final String fPincode=pincode.getText().toString();
                                    final String fCommodity=commodity.getText().toString();
                                    final String fPrice=price.getText().toString();
                                    final String fWeight=weight.getText().toString();
                                    String ftime = time.getText().toString();
                                    final String fkey = key.getText().toString();
                                    final int addhours = Integer.parseInt(ftime);

                                   // String strDate  = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                                    //Date date = new Date();
                                    Calendar rightNow = Calendar.getInstance();
                                    int cur = rightNow.get(Calendar.HOUR_OF_DAY);
                                    cur += addhours;
                                    final String fstr = Integer.toString(cur);
                                    final String fwprice = "0";
                                    final String fwname = "Empty";
                                    final String fwphone = "Empty";



                                    Product_detail product_detail = new Product_detail(fName,fState,fDistrict,fPincode,
                                            fCommodity,fPrice,fWeight,false,user.getPhoneNumber(),photoLink,fstr,
                                            fwprice,fwname,fwphone
                                            );
                                    mFirebaseDatabaseReference.push().setValue(product_detail);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sell.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
