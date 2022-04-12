package com.cafe.bhaansa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.Random;

public class FoodMenuAdapter extends FirebaseRecyclerAdapter <FoodMenuModel, FoodMenuAdapter.FoodMenuViewHolder>{
    private final FoodMenuInterface callback;

    public FoodMenuAdapter(@NonNull FirebaseRecyclerOptions<FoodMenuModel> options, FoodMenuInterface callback) {
        super(options);
        this.callback = callback;
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodMenuViewHolder holder, int position, @NonNull FoodMenuModel model) {
        holder.foodname.setText(model.getFoodname());
        holder.foodprice.setText(model.getFoodprice());
        Glide.with(holder.foodimage.getContext()).load(model.getFoodimg()).into(holder.foodimage);

        holder.addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // int qty = Integer.parseInt(qtyField.getText().toString());
                callback.onAdd(new OrderModel(holder.foodname.getText().toString(),
                        holder.foodprice.getText().toString(),
                        String.valueOf(holder.itemCount))
                );

                holder.foodcount.setText("0");
                holder.addOrderBtn.setEnabled(false);


            }
        });
    }

    @NonNull
    @Override
    public FoodMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
        return new FoodMenuViewHolder(view);
    }


    class FoodMenuViewHolder extends RecyclerView.ViewHolder
    {
        ImageView foodimage;
        TextView foodname, foodprice, foodcount;;
        Button addOrderBtn;
        ImageButton add_item, subtract_item;
        int itemCount=0;

        public FoodMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.foodimage);
            foodname = itemView.findViewById(R.id.foodname);
            foodprice = itemView.findViewById(R.id.foodprice);
            addOrderBtn = itemView.findViewById(R.id.addOrderBtn);
            add_item = itemView.findViewById(R.id.add_item);
            subtract_item = itemView.findViewById(R.id.subtract_item);
            foodcount = itemView.findViewById(R.id.foodcount);

            if (foodcount.getText().toString().equals("0")){
                addOrderBtn.setEnabled(false);
            }
            add_item.setOnClickListener(view -> {
                if(Integer.parseInt(foodcount.getText().toString())>=0){
                    itemCount += 1;
                    foodcount.setText(String.valueOf(itemCount));
                    addOrderBtn.setEnabled(true);

                }
            });
            subtract_item.setOnClickListener(view -> {
                if (Integer.parseInt(foodcount.getText().toString())>0){
                    itemCount -= 1;
                    foodcount.setText(String.valueOf(itemCount));
                    if (foodcount.getText().toString().equals("0")){
                        addOrderBtn.setEnabled(false);
                    }
                }
            });
        }

    }

}
