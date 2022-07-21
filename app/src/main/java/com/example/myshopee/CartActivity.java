package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.adapters.CartAdapter;
import com.example.myshopee.my_interface.IListenerActionsItemInCartView;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.AccountDAO;
import DAO.CartDAO;
import DAO.CartDetailDAO;
import DAO.ProductDAO;
import Model.Cart;
import Model.CartDetails;
import Model.Product;
import Model.User;

public class CartActivity extends AppCompatActivity implements IListenerActionsItemInCartView {
    private ProductDAO productDAO;
    private CartDetailDAO cartDetailDAO;
    private AccountDAO accountDAO;
    private User currentUser;
    private CartDAO cartDAO;
    private List<CartDetails> getAllCartDetails;
    private List<Product> getAllProductsInCart;
    private List<Cart> allUnpaidCarts;

    private HashMap<Integer, Product> cartDetailsListChecked;
    private MaterialToolbar toolbar;
    private RecyclerView cartRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter cartAdapter;
    private CheckBox checkBoxAll;
    private TextView txvTotalCost;
    private Button btnBuy;
    private Double totalCostInCartChecked = 0.0;

    private boolean canClearAll = true;

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

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        cartDetailsListChecked = new HashMap<>();

        cartDAO = new CartDAO(this);
        cartDetailDAO = new CartDetailDAO(this);
        productDAO = new ProductDAO(this);
        accountDAO = new AccountDAO(this);

        currentUser = CommonUtils.getCurrentUser(CartActivity.this);
        allUnpaidCarts = cartDAO.getUnpaidCartsByUserId(currentUser.getUserId());
        getAllCartDetails = cartDetailDAO.getCartDetailsUnpaidByListCartsUnPaid(allUnpaidCarts);
        getAllProductsInCart = productDAO.loadAllProductsByListProductId(getAllCartDetails);

        cartAdapter = new CartAdapter(CartActivity.this, getAllCartDetails, cartDetailsListChecked, this);
        layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);

        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(cartAdapter);
//        cartAdapter.setData(getAllCartDetails, getAllProductsInCart);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setListeners() {

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartDetailsListChecked.size() == 0) {
                    Toast.makeText(CartActivity.this, "You must choose the products that need to be paid first", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Check budget
                    Log.d(String.valueOf(CartActivity.this), "The total amount of money need to pay: " + CommonUtils.getReadableCostFromDouble(totalCostInCartChecked));
                    Log.d(String.valueOf(CartActivity.this), "Your current budget: " + CommonUtils.getReadableCostFromDouble(currentUser.getBudget()));

                    if(accountDAO.checkBudget(currentUser.getUserId(), totalCostInCartChecked)) {
//                        Log for debug
                        Log.d(String.valueOf(CartActivity.this), "Your payment is being processed" );

                        Toast.makeText(CartActivity.this, "Your payment is being processed", Toast.LENGTH_SHORT).show();
//                        Change paid status for cart
                        boolean hasError = false;
                        for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
                            if(cartDAO.changeStatusIsPaid(getAllCartDetails.get(set.getKey()).getCartId())) {
                                Log.d(String.valueOf(CartActivity.this), String.format("Payment Processing: change payment status successfully for cartId %s",getAllCartDetails.get(set.getKey()).getCartId()));

                            } else {
                                hasError = true;
                                Log.d(String.valueOf(CartActivity.this), String.format("Payment Processing: failed when change payment status for cartId %s",getAllCartDetails.get(set.getKey()).getCartId()));
                            }
                        }
                        if(hasError) {
                            Toast.makeText(CartActivity.this, "An error occurred while paying for the order", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Sub budget
                        accountDAO.subBudget(currentUser.getUserId(), totalCostInCartChecked);
                        for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
                            int productId = set.getValue().getProductId();
                            productDAO.buyMethod(productId, getAllCartDetails.get(set.getKey()).getQuantity());
                        }

                        // Redirect to Payment Activity
                        Bundle bundle = new Bundle();
                        bundle.putString("username", currentUser.getUserName());
                        bundle.putDouble("totalCostPaid", totalCostInCartChecked);
                        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
//                        Log for debug
                        Log.d(String.valueOf(CartActivity.this), "Your current budget is not enough to pay" );

                        Toast.makeText(CartActivity.this, "Your current budget is not enough to pay", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                for (int x = cartRecyclerView.getChildCount(), i = 0; i < x; ++i) {
                    CartAdapter.CartViewHolder holder = (CartAdapter.CartViewHolder) cartRecyclerView.getChildViewHolder(cartRecyclerView.getChildAt(i));
                    CheckBox checkBoxAtCurrentItem = holder.itemView.findViewById(R.id.checkBoxItem);
                    if(isChecked) {
//                        cartDetailsListChecked.put(getAllCartDetails.get(i).getCartId(), true);
                        checkBoxAtCurrentItem.setChecked(true);
                        cartDetailsListChecked.put(i, holder.product);
                    }
                    else {
                        if(canClearAll){
                            checkBoxAtCurrentItem.setChecked(isChecked);
                            cartDetailsListChecked.clear();
                        }
                    }
                }

                for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
                    Log.d(String.valueOf(CartActivity.this), "Update cartDetailsListCheck in onCheckedChanged func: " + getAllCartDetails.get(set.getKey()).toString() + " - " + set.getValue().toString());
                }
                Log.d(String.valueOf(CartActivity.this), "End onCheckedChanged func" );

            }
        });
    }

    private void updateQuantityInCartDetails(int currentQuantity, int position) {
        CartDetails target = getAllCartDetails.get(position);
        target.setQuantity(currentQuantity);
        cartDetailDAO.updateCartDetail(target);
        getAllCartDetails.set(position, target);
        cartAdapter.notifyItemChanged(position);
    }

    @Override
    public void increaseBtn(int currentQuantity, int position, boolean isChecked) {
        updateQuantityInCartDetails(currentQuantity, position);
        if(isChecked) {
            changeViewWhenActions();
        }

    }

    @Override
    public void decreaseBtn(int currentQuantity, int position, boolean isChecked) {
        CartDetails target = getAllCartDetails.get(position);
        if(currentQuantity == 0) {
            if(cartDetailDAO.deleteCartDetails(target.getCartDetailId())){
                if(cartDAO.deleteCart(target.getCartId())) {
                    getAllCartDetails.remove(position);
                    getAllProductsInCart.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    cartAdapter.notifyItemRangeChanged(position, getAllCartDetails.size());
                }
                else {
                    Toast.makeText(this, "Failed to delete cart record when user click decreaseBtn", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            updateQuantityInCartDetails(currentQuantity, position);
        }
        if(isChecked) {
            changeViewWhenActions();
        }

    }

    private void changeViewWhenActions() {
        double totalNeedToPay = 0;
        DecimalFormat formatter = new DecimalFormat("#,###");
        int totalQuantityBuy = 0;
        for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
            totalNeedToPay += (getAllCartDetails.get(set.getKey()).getQuantity() * set.getValue().getPrice());
            totalQuantityBuy += getAllCartDetails.get(set.getKey()).getQuantity();
        }
        totalCostInCartChecked = totalNeedToPay;
        this.txvTotalCost.setText(formatter.format(totalNeedToPay));
        this.btnBuy.setText(String.format("Mua hàng (%s)", String.valueOf(totalQuantityBuy)));
    }


    @Override
    public void unchecked(Product productChecked, int position) {
        cartDetailsListChecked.remove(position);
        changeViewWhenActions();
        if(checkBoxAll.isChecked()) {
            canClearAll = false;
            checkBoxAll.setChecked(false);
        }
        for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
            Log.d(String.valueOf(CartActivity.this), "Update cartDetailsListCheck in unChecked func: " + getAllCartDetails.get(set.getKey()).toString() + " - " + set.getValue().toString());
        }
    }

    @Override
    public void checked(Product productChecked, int position) {
        cartDetailsListChecked.put(position, productChecked);
        changeViewWhenActions();
        if(cartDetailsListChecked.size() == getAllCartDetails.size()) {
            canClearAll = true;
            checkBoxAll.setChecked(true);
        }
        for(Map.Entry<Integer, Product> set: cartDetailsListChecked.entrySet()) {
            Log.d(String.valueOf(CartActivity.this), "Update cartDetailsListCheck in checked func: " + getAllCartDetails.get(set.getKey()).toString() + " - " + set.getValue());
        }
    }


}