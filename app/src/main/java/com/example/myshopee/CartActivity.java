package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.adapters.CartAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CartDAO;
import DAO.CartDetailDAO;
import DAO.ProductDAO;
import Model.Cart;
import Model.CartDetails;
import Model.Product;
import Model.User;

public class CartActivity extends AppCompatActivity {
    private ProductDAO productDAO;
    private CartDetailDAO cartDetailDAO;
    private User currentUser;
    private CartDAO cartDAO;
    private List<CartDetails> getAllCartDetails;
    private List<Product> getAllProductsInCart;
    private List<Cart> allUnpaidCarts;

    private HashMap<Integer, Boolean> cartDetailsListChecked;
    private MaterialToolbar toolbar;
    private RecyclerView cartRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter cartAdapter;
    private CheckBox checkBoxAll;
    private TextView txvTotalCost;
    private Button btnBuy;

    private void mapping() {
        this.toolbar = findViewById(R.id.cart_activity_toolbar);
        this.cartRecyclerView = findViewById(R.id.rcv_list_carts);
        this.checkBoxAll = findViewById(R.id.checkBoxAll);
        this.txvTotalCost = findViewById(R.id.txvTotalCost);
        this.btnBuy = findViewById(R.id.btnBuy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mapping();
        setListeners();
        cartDetailsListChecked = new HashMap<>();

        cartDAO = new CartDAO(this);
        cartDetailDAO = new CartDetailDAO(this);
        productDAO = new ProductDAO(this);

        currentUser = CommonUtils.getCurrentUser(CartActivity.this);
        allUnpaidCarts = cartDAO.getUnpaidCartsByUserId(currentUser.getUserId());
        getAllCartDetails = cartDetailDAO.getCartDetailsUnpaidByListCartsUnPaid(allUnpaidCarts);
        getAllProductsInCart = productDAO.loadAllProductsByListProductId(getAllCartDetails);

        cartAdapter = new CartAdapter(CartActivity.this, getAllCartDetails, getAllProductsInCart);
        layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);

        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(cartAdapter);
//        cartAdapter.setData(getAllCartDetails, getAllProductsInCart);

    }

    private void setListeners() {
        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                for (int x = cartRecyclerView.getChildCount(), i = 0; i < x; ++i) {
                    CartAdapter.CartViewHolder holder = (CartAdapter.CartViewHolder) cartRecyclerView.getChildViewHolder(cartRecyclerView.getChildAt(i));
                    CheckBox checkBoxAtCurrentItem = holder.itemView.findViewById(R.id.checkBoxItem);
                    checkBoxAtCurrentItem.setChecked(isChecked);
                    if(isChecked) {
                        cartDetailsListChecked.put(getAllCartDetails.get(i).getCartId(), true);
                    }
                    else {
                        cartDetailsListChecked.clear();
                    }
                }

                for(Map.Entry<Integer, Boolean> set: cartDetailsListChecked.entrySet()) {
                    Log.d(String.valueOf(CartActivity.this), "Update cartDetailsListCheck in onCheckedChanged func: " + set.getKey() + " - " + set.getValue());
                }

            }
        });
    }
}