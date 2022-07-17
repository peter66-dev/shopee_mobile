package com.example.myshopee.my_interface;

import Model.Product;

public interface IListenerActionsItemInCartView {
    public void increaseBtn(int currentQuantity, int position, boolean isChecked);
    public void decreaseBtn(int currentQuantity, int position, boolean isChecked);
    public void unchecked(Product productChecked, int position);
    public void checked(Product productChecked, int position);
}
