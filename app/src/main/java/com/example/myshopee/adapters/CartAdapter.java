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
import com.example.myshopee.my_interface.IListenerActionsItemInCartView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import DAO.ProductDAO;
import Model.CartDetails;
import Model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartDetails> cartDetails;
    private List<Product> productList;
    private ProductDAO productDAO;
    private HashMap<Integer, Product> cartDetailsListChecked;
    public IListenerActionsItemInCartView actionsItemInCartView;

    public CartAdapter(Context context, List<CartDetails> cartDetails, List<Product> productList, IListenerActionsItemInCartView actionsItemInCartView) {
        this.context = context;
        this.cartDetails = cartDetails;
        this.productList = productList;
        this.actionsItemInCartView = actionsItemInCartView;
        this.productDAO = new ProductDAO(context);
    }
    public CartAdapter(Context context, List<CartDetails> cartDetails, HashMap<Integer, Product> cartDetailsListChecked, IListenerActionsItemInCartView actionsItemInCartView) {
            this.context = context;
            this.cartDetails = cartDetails;
            this.cartDetailsListChecked = cartDetailsListChecked;
            this.actionsItemInCartView = actionsItemInCartView;
            this.productDAO = new ProductDAO(context);
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
        CartDetails cartDetail = cartDetails.get(position);
        Product product = productDAO.getProductById(cartDetail.getProductId());
        Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder - CartAdapter: product - " + product.toString());
        Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder - CartAdapter: cartDetail - " + cartDetail.toString());
        if(cartDetailsListChecked.containsKey(position)) {
            Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder: status - is being checked");
            holder.setData(product, cartDetail, position, true);
        } else {
            Log.d(String.valueOf(CartAdapter.this), "onBindViewHolder: status - is being not checked");
            holder.setData(product, cartDetail, position, false);
        }
    }

    @Override
    public int getItemCount() {
        if(cartDetails != null) {
            return cartDetails.size();
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

        public double price;
        public int quantityInCart;
        public Product product;

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

        public void setData(Product product, CartDetails cartDetails, int position, boolean isChecked) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            checkBoxItem.setChecked(isChecked);
            this.imageViewThumbnail.setImageResource(CommonUtils.getImageId(context, product.getImage()));
            this.txvProductName.setText(product.getProductName());
            this.txvProductPrice.setText(formatter.format(product.getPrice()));
            this.txvQuantity.setText(String.valueOf(cartDetails.getQuantity()));
            this.btnDecreaseQuantity.setEnabled(true);
            if(cartDetails.getQuantity() == product.getQuantity()) {
                btnIncreaseQuantity.setEnabled(false);
            }
            this.price = product.getPrice();
            this.quantityInCart = cartDetails.getQuantity();
            this.product = product;
            setListeners(product, cartDetails, position);
        }

        private void setListeners(Product product, CartDetails cartDetails, int position) {
            btnIncreaseQuantity.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(txvQuantity.getText().toString().trim());
                currentQuantity += 1;
                actionsItemInCartView.increaseBtn(currentQuantity, position, checkBoxItem.isChecked());
            });

            btnDecreaseQuantity.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(txvQuantity.getText().toString().trim());
                currentQuantity -= 1;
                actionsItemInCartView.decreaseBtn(currentQuantity, position, checkBoxItem.isChecked());
            });

            checkBoxItem.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if(isChecked) {
                    actionsItemInCartView.checked(product, position);
                } else {
                    actionsItemInCartView.unchecked(product, position);
                }
            });
        }
    }
}
