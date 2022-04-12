package com.cafe.bhaansa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class FoodMenuActivity extends AppCompatActivity implements FoodMenuInterface {

    RecyclerView recycler;
    FoodMenuAdapter foodMenuAdapter;
    TextView foodname, foodprice, foodcount;
    //    Button adminLoginButton;
    ExtendedFloatingActionButton fabOrder, placeOrderButton;
    AppCompatEditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu);

        recycler = findViewById(R.id.recycler);

        foodname = findViewById(R.id.foodname);
        foodprice = findViewById(R.id.foodprice);
        foodcount = findViewById(R.id.foodcount);
        fabOrder = findViewById(R.id.fabOrder);
        search = findViewById(R.id.search);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                processSearch(s.toString().toLowerCase(Locale.ROOT));
            }
        });


//        //checking permisiion to open camera
//        if (ContextCompat.checkSelfPermission(FoodMenuActivity.this, Manifest.permission.CAMERA)
//        != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(FoodMenuActivity.this,
//                    new String[]{Manifest.permission.CAMERA}, 101
//                    );
//        }


        recycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FoodMenuModel> options =
                new FirebaseRecyclerOptions.Builder<FoodMenuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foodlist"), FoodMenuModel.class)
                        .build();

        foodMenuAdapter = new FoodMenuAdapter(options, this);
        recycler.setAdapter(foodMenuAdapter);

//        adminLoginButton.setOnClickListener(view -> {
//            Intent intent = new Intent(FoodMenuActivity.this, LoginActivity.class);
//            startActivity(intent);
//        });

        //TO ORDER CART
        fabOrder.setOnClickListener(view -> {
            try {

                Intent intentRecycler = new Intent(FoodMenuActivity.this, OrderCartActivity.class);
                startActivity(intentRecycler);
            } catch (Exception e) {

            }
        });

    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<FoodMenuModel> options =
                new FirebaseRecyclerOptions.Builder<FoodMenuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foodlist").orderByChild("foodname").startAt(s).endAt(s + "\uf8ff"), FoodMenuModel.class)
                        .build();


        foodMenuAdapter = new FoodMenuAdapter(options, this);
        foodMenuAdapter.startListening();
        recycler.setAdapter(foodMenuAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        foodMenuAdapter.startListening();
    }


    @Override
    public void onAdd(OrderModel orderModel) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Task<Void> node = firebaseDatabase.getReference("OrderedItems").push().setValue(orderModel);
        Toast.makeText(this, orderModel.orderquantity + " " + orderModel.ordername + "  added to cart", Toast.LENGTH_SHORT).show();
    }

//    public void callAPI() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://financecrm.susankya.com.np/api/manager/for_bhansa?search=Veg+Burger";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.d("Response is: ", response.substring(0, 95));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("error: ", String.valueOf(error));
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }


//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//        if (requestCode == 101){
//
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
//    }


    @Override
    protected void onStart() {
        super.onStart();
        initPython();
    }

    private void initPython(){
        if (!Python.isStarted()){
            Python.start(new AndroidPlatform (this));
        }
    }
}