package com.example.myshopee.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.R;

import java.text.DecimalFormat;
import java.util.List;

import Model.CartDetails;
import Model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartDetails> cartDetails;
    private List<Product> productList;

    public CartAdapter(Context context, List<CartDetails> cartDetails, List<Product> productList) {
        this.context = context;
        this.cartDetails = cartDetails;
        this.productList = productList;
    }

    public void setData(List<CartDetails> cartDetails, List<Product> productList) {
        this.cartDetails = cartDetails;
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_item_in_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);
        CartDetails cartDetail = cartDetails.get(position);
        Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder: product - " + product.toString());
        Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder: cartDetail - " + cartDetail.toString());
        holder.setData(product, cartDetail);

    }

    @Override
    public int getItemCount() {
        if(productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxItem;
        private ImageView imageViewThumbnail;
        private TextView txvProductName;
        private TextView txvProductPrice;
        private Button btnDecreaseQuantity;
        private Button btnIncreaseQuantity;
        private TextView txvQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkBoxItem);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            txvProductName = itemView.findViewById(R.id.txvProductName);
            txvProductPrice = itemView.findViewById(R.id.txvProductPrice);
            btnDecreaseQuantity = itemView.findViewById(R.id.btnDecreaseQuantity);
            btnIncreaseQuantity = itemView.findViewById(R.id.btnIncreaseQuantity);
            txvQuantity = itemView.findViewById(R.id.txvQuantity);
        }

        public void setData(Product product, CartDetails cartDetails) {
            DecimalFormat formatter = new DecimalFormat("#,###");

            this.imageViewThumbnail.setImageResource(CommonUtils.getImageId(context, product.getImage()));
            this.txvProductName.setText(product.getProductName());
            this.txvProductPrice.setText(formatter.format(product.getPrice()));
            this.txvQuantity.setText(String.valueOf(cartDetails.getQuantity()));
        }
    }
}
