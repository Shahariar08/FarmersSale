package com.example.Restinpeace.farmerassistant;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommodityActivity extends AppCompatActivity {

    private String Url="https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset=20&limit=50";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Price_list> price_lists;
    private PriceAdapter priceAdapter;
    private int offset=10;

    private boolean isScrolling = false;
    int currentItems, totalItems, scrolloutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        setTitle("Latest Commodity Price");

        mRecyclerView = (RecyclerView) findViewById(R.id.RecylerView);
        mRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        price_lists = new ArrayList<>();
        priceAdapter = new PriceAdapter(price_lists,this);
        mRecyclerView.setAdapter(priceAdapter);

        getPriceData(Url);
        priceAdapter.notifyDataSetChanged();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)(findViewById(R.id.SwipeRefreshLayout));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                price_lists.clear();
                Url=GetUrl(offset);
                getPriceData(Url);
                priceAdapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });

        //fetching remaining price list of commodities using onScrollListener
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrolloutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (scrolloutItems+currentItems==totalItems)) {

                    isScrolling = false;
                    offset+=10;
                    Url = GetUrl(offset);
                    getPriceData(Url);

                }
            }
        });

    }
    /*public String getLol(){
        String[] a;
        a = new String[7];
        a[0] = "Dhaka";
        a[1] = "Chottogram";
        a[2] = "Barisal";
        a[3] = "Sylhet" ;
        a[4] = "Khulna" ;
        a[5] = "Mymenshingh";
        a[6] = "Rangpur";
        int now = (int) Math.random();
        now = now % 7;
        return a[now];
    }*/

    private String GetUrl(int offset){

        String off=Integer.toString(offset);
        String Url="https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset="+off+"&limit=50";
        return Url ;
    }

    private void getPriceData(String url) {

        //Initialising dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("records");

                    Log.e("inside try block","reached");
                    System. out. print(jsonArray.length());

                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String division = "Dhaka";
                        String Market = "Karwan Bazar";
                        double dnum = Double.parseDouble(object.getString("min_price"));
                        dnum = 1.01*dnum;
                        String mini = Double.toString(dnum);

                        dnum = Double.parseDouble(object.getString("max_price"));
                        dnum = 1.23*dnum;
                        String maxi = Double.toString(dnum);


                        Price_list item = new Price_list(division,division
                                ,Market,object.getString("commodity"),object.getString("variety")
                                ,mini,maxi);
                        //item.getLol()
                        price_lists.add(item);
                        priceAdapter.notifyDataSetChanged();
                    }

                }catch(JSONException e){
                    //progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Check your Internet", Toast.LENGTH_SHORT).show();
            }
        });

        //Requesting using volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
