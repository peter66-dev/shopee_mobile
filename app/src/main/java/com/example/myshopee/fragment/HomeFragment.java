package com.example.myshopee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.ProductAdapter;
import com.example.myshopee.Home;
import com.example.myshopee.ProductDetail;
import com.example.myshopee.R;
import com.example.myshopee.my_interface.IClickProductItemListener;

import java.util.ArrayList;

import DAO.ProductDAO;
import Model.Product;
import Model.User;

public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ProductAdapter adapterProduct;
    private ProductDAO productDAO = new ProductDAO(this.getContext());
    private Home homeActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        homeActivity = (Home) getActivity();
        productDAO = new ProductDAO(this.getContext());

        recyclerView = view.findViewById(R.id.rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(homeActivity, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapterProduct = new ProductAdapter(homeActivity, new IClickProductItemListener(){
            @Override
            public void onClickProductItem(Product pro) {
                showProductDetail(pro);
            }
        });
        adapterProduct.setData(productDAO.loadAllProducts());
        recyclerView.setAdapter(adapterProduct);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.rec);

        // Show sản phẩm trong app
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        productDAO = new ProductDAO(this.getContext());
        ArrayList<Product> list = productDAO.loadAllProducts();
        adapterProduct = new ProductAdapter(this.getContext(), new IClickProductItemListener() {
            @Override
            public void onClickProductItem(Product pro) {
                showProductDetail(pro);
            }
        });

        adapterProduct.setData(list);
        recyclerView.setAdapter(adapterProduct);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showProductDetail(Product pro) {
        Intent intent = new Intent(getActivity(), ProductDetail.class);
        Bundle bundle = new Bundle();
        User user = (User)bundle.get("current_user");
        bundle.putSerializable("obj_product", pro);
        bundle.putSerializable("current_user", user);
//        Log.i("[PETER_MESSAGE]", "User name: " + user.getUserName());
//        Log.i("[PETER_MESSAGE]", "Product name: " + pro.getProductName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
