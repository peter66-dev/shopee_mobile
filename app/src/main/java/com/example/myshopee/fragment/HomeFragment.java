package com.example.myshopee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopee.AdapterProduct;
import com.example.myshopee.Home;
import com.example.myshopee.Product;
import com.example.myshopee.ProductDetail;
import com.example.myshopee.R;
import com.example.myshopee.my_interface.IClickProductItemListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdapterProduct adapterProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.rec);

        // Show sản phẩm trong app
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Product> list = getProductList();
        adapterProduct = new AdapterProduct(list, new IClickProductItemListener() {
            @Override
            public void onClickProductItem(Product pro) {
                showProductDetail(pro);
            }
        });
        recyclerView.setAdapter(adapterProduct);
    }

    private ArrayList<Product> getProductList() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product(1, "Trân trọng mời bạn dành thời gian quý báu của mình và bắt đầu một cuộc sống mới với đôi tay của bạn", R.drawable.product1, 32000));
        list.add(new Product(2, "Product 2", R.drawable.product1, 32000));
        list.add(new Product(3, "Product 3", R.drawable.product1, 32000));
        list.add(new Product(4, "Product 4", R.drawable.product1, 32000));
        list.add(new Product(5, "Product 5", R.drawable.product1, 32000));
        list.add(new Product(6, "Product 6", R.drawable.product1, 32000));
        list.add(new Product(7, "Product 7", R.drawable.product1, 32000));
        list.add(new Product(8, "Product 8", R.drawable.product1, 32000));
        list.add(new Product(9, "Product 9", R.drawable.product1, 32000));
        list.add(new Product(10, "Product 10", R.drawable.product1, 32000));
        list.add(new Product(11, "Product 11", R.drawable.product1, 32000));
        list.add(new Product(12, "Product 12", R.drawable.product1, 32000));
        list.add(new Product(13, "Product 12", R.drawable.product1, 32000));
        list.add(new Product(14, "Product 12", R.drawable.product1, 32000));
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showProductDetail(Product pro) {
        Intent intent = new Intent(getActivity(), ProductDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_product", pro);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
