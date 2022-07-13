package com.example.myshopee;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.my_interface.IClickAđToCartListener;
import com.example.myshopee.my_interface.IClickProductItemListener;
import java.util.List;
import Model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> data;
    IClickProductItemListener iClickProductItemListener;

    public static int getImageId(Context context, String imageName) {
        Log.i("[PETER MESSAGE]", "Image source:  " + imageName);
        Log.i("[PETER MESSAGE]", "Image index:  " + context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public void setData(List<Product> list) {
        this.data = list;
        notifyDataSetChanged();
    }


    public ProductAdapter(Context context, IClickProductItemListener listener) {
        this.context = context;
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

        holder.productName.setText(currentPro.getProductName());
        holder.productPrice.setText(currentPro.getPrice() + "");
        holder.productSold.setText(currentPro.getSoldQuantity() + "");
        holder.productImg.setImageResource(getImageId(context.getApplicationContext(), currentPro.getImage()));

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickProductItemListener.onClickProductItem(currentPro);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productPrice;
        TextView productSold;
        ImageView productImg;
        CardView layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            productName = itemView.findViewById(R.id.productContent);
            productImg = itemView.findViewById(R.id.productImg);
            productPrice = itemView.findViewById(R.id.productPrice);
            productSold = itemView.findViewById(R.id.productSold);
        }
    }
}
