package com.example.myshopee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.my_interface.IClickProductItemListener;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    ArrayList<Product> data;
    IClickProductItemListener iClickProductItemListener;

    public AdapterProduct(ArrayList<Product> data, IClickProductItemListener listener) {
        this.data = data;
        this.iClickProductItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product currentPro = data.get(position);
        //        holder.productID.setText(data.get(position).getId());
        holder.productContent.setText(data.get(position).getName());
        holder.productPrice.setText(data.get(position).getPrice() + "");
        holder.productImg.setImageResource(data.get(position).getImgSrc());

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickProductItemListener.onClickProductItem(currentPro);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        TextView productID;
        TextView productContent;
        TextView productPrice;
        ImageView productImg;
        CardView layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            productID = itemView.findViewById(R.id.productID);
            layoutItem = itemView.findViewById(R.id.layout_item);
            productContent = itemView.findViewById(R.id.productContent);
            productImg = itemView.findViewById(R.id.productImg);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
