package com.example.myshopee.my_interface;

import android.widget.ImageView;

import Model.Product;
import Model.User;

public interface IClickAđToCartListener {
    void onClickAddToCart(User user, Product product);
}
