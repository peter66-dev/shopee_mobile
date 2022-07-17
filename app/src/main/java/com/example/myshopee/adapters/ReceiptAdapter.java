package com.example.myshopee.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.R;
import com.example.myshopee.my_interface.IListenerActionsItemInReceiptView;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.DecimalFormat;
import java.util.List;

import DAO.ProductDAO;
import Model.CartDetails;
import Model.Product;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private Context context;
    private List<CartDetails> cartDetails;
    private ProductDAO productDAO;
    private IListenerActionsItemInReceiptView listenerActionsItemInReceiptView;

    public ReceiptAdapter(Context context, List<CartDetails> cartDetails, ProductDAO productDAO, IListenerActionsItemInReceiptView listenerActionsItemInReceiptView) {
        this.context = context;
        this.cartDetails = cartDetails;
        this.productDAO = productDAO;
        this.listenerActionsItemInReceiptView = listenerActionsItemInReceiptView;
    }

    public void setData(List<CartDetails> cartDetails) {
        this.cartDetails = cartDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_item_in_receipt, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        CartDetails cartDetail = cartDetails.get(position);
        Product product = productDAO.getProductById(cartDetail.getProductId());
        Log.d(String.valueOf(ReceiptAdapter.this), "onBindViewHolder - ReceiptAdapter: product - " + product.toString());
        Log.d(String.valueOf(ReceiptAdapter.this), "onBindViewHolder - ReceiptAdapter: cartDetail - " + cartDetail.toString());
        holder.setData(product, cartDetail, position);
    }

    @Override
    public int getItemCount() {
        if(cartDetails != null) {
            return cartDetails.size();
        }
        return 0;
    }

    public class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imageViewThumbnail;
        private TextView txvProductName;
        private TextView txvQuantityInReceipt;
        private TextView txvProductPrice;
        private TextView txvQuantity;
        private TextView txvTotalCostInReceipt;
        private Button btnBuyAgain;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            txvProductName = itemView.findViewById(R.id.txvProductName);
            txvQuantityInReceipt = itemView.findViewById(R.id.txvQuantityInReceipt);
            txvProductPrice = itemView.findViewById(R.id.txvProductPrice);
            txvQuantity = itemView.findViewById(R.id.txvQuantity);
            txvTotalCostInReceipt = itemView.findViewById(R.id.txvTotalCostInReceipt);
            btnBuyAgain = itemView.findViewById(R.id.btnBuyAgain);
        }

        public void setData(Product product, CartDetails cartDetails, int position) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            this.imageViewThumbnail.setImageResource(CommonUtils.getImageId(context, product.getImage()));
            this.txvProductName.setText(product.getProductName());
            this.txvProductPrice.setText(formatter.format(product.getPrice()));
            this.txvQuantityInReceipt.setText(String.valueOf(cartDetails.getQuantity()));
            this.txvQuantity.setText(String.valueOf(cartDetails.getQuantity()));
            double totalCostInReceipt = cartDetails.getQuantity() * product.getPrice();
            this.txvTotalCostInReceipt.setText(formatter.format(totalCostInReceipt));

            setListeners(product, cartDetails, position);
        }
        private void setListeners(Product product, CartDetails cartDetails, int position){
            btnBuyAgain.setOnClickListener(v-> {
                listenerActionsItemInReceiptView.clickToBuyAgain(product);
            });
        }
    }
}
