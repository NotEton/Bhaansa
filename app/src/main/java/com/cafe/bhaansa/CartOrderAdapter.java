package com.cafe.bhaansa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CartOrderAdapter extends FirebaseRecyclerAdapter<ShowOrderModel, CartOrderAdapter.ViewHolder> {
    public CartOrderAdapter(@NonNull FirebaseRecyclerOptions<ShowOrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ShowOrderModel model) {
        holder.cartFoodName.setText(model.getOrdername());
        holder.cartFoodPrice.setText(model.getOrderprice());
        holder.cartFoodQuantity.setText(model.getOrderquantity());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cartFoodName, cartFoodPrice, cartFoodQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartFoodName = (TextView) itemView.findViewById(R.id.cartFoodName);
            cartFoodPrice = (TextView) itemView.findViewById(R.id.cartFoodPrice);
            cartFoodQuantity =(TextView) itemView.findViewById(R.id.cartFoodQuantity);

        }
    }
}
