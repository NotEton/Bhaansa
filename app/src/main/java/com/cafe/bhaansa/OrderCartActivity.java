package com.cafe.bhaansa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderCartActivity extends AppCompatActivity {
    CartOrderAdapter cartOrderAdapter;
    RecyclerView orderRecycler;
//    ExtendedFloatingActionButton paynowBtn;

    KhaltiButton Khalti_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);

        KhaltiButton khalti_btn = (KhaltiButton) findViewById(R.id.khalti_btn);


        orderRecycler = findViewById(R.id.orderRecycler);

        orderRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ShowOrderModel> options =
                new FirebaseRecyclerOptions.Builder<ShowOrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderedItems"), ShowOrderModel.class)
                        .build();

        cartOrderAdapter = new CartOrderAdapter(options);
        orderRecycler.setAdapter(cartOrderAdapter);
        float total = 0;
        for (int i = 0; i < options.getSnapshots().size(); i++) {
            ShowOrderModel model = options.getSnapshots().get(i);
            total = Float.parseFloat(model.getOrderprice())+total;
        }
        // totalText.setText(String.valueOf(total));
        //ADD ORDER TO DATABASE
//        paynowBtn = findViewById(R.id.paynowBtn);

//        paynowBtn.setOnClickListener(view -> {
//            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//            for (int i = 0; i < options.getSnapshots().size(); i++) {
//                ShowOrderModel model = options.getSnapshots().get(i);
//                Task<Void> node = firebaseDatabase.getReference("FinalOrder").push().setValue(model);
//
//            }
//
//
//        });

        //khalti

        khalti_btn = (KhaltiButton) findViewById(R.id.khalti_btn);



        Config.Builder builder = new Config.Builder(Constant.pub, "Product ID", "Main", 100L, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                Toast.makeText(OrderCartActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(OrderCartActivity.this, FoodMenuActivity.class);
//                startActivity(i);
                FirebaseDatabase.getInstance().getReference().child("OrderedItems").removeValue();
                finish();

            }
        });

//        Config config = builder.build();
//        khalti_btn.setCheckOutConfig(config);

        Config config = builder.build();
        khalti_btn.setCheckOutConfig(config);
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, config);

        khalti_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khaltiCheckOut.show();
                saveData();
            }

             void saveData(){
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                for (int i = 0; i < options.getSnapshots().size(); i++) {
                    ShowOrderModel model = options.getSnapshots().get(i);
                    Task<Void> node = firebaseDatabase.getReference("FinalOrder").push().setValue(model);
                }
            }

        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        cartOrderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartOrderAdapter.stopListening();
    }


}