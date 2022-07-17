package com.example.myshopee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.ProductDetail;
import com.example.myshopee.R;
import com.example.myshopee.adapters.ReceiptAdapter;
import com.example.myshopee.my_interface.IListenerActionsItemInReceiptView;

import java.util.List;

import DAO.CartDAO;
import DAO.CartDetailDAO;
import DAO.ProductDAO;
import Model.Cart;
import Model.CartDetails;
import Model.Product;
import Model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistoryFragment extends Fragment implements IListenerActionsItemInReceiptView {
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private CartDetailDAO cartDetailDAO;
    private User currentUser;
    private List<Cart> paidCartList;
    private List<CartDetails> getAllCartDetailsFromPaidCartList;
    private ReceiptAdapter receiptAdapter;

    private boolean shouldRefreshOnResume = false;
    private View view;
    private RecyclerView receiptsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHistoryFragment newInstance(String param1, String param2) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        productDAO = new ProductDAO(getActivity());
        cartDAO = new CartDAO(getActivity());
        cartDetailDAO = new CartDetailDAO(getActivity());

        currentUser = CommonUtils.getCurrentUser(getActivity());
        paidCartList = cartDAO.getPaidCartsByUserId(currentUser.getUserId());
        getAllCartDetailsFromPaidCartList = cartDetailDAO.getCartDetailsUnpaidByListCartsUnPaid(paidCartList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_receipt_history, container, false);
        this.receiptsRecyclerView = view.findViewById(R.id.rcv_list_receipts);

        receiptAdapter = new ReceiptAdapter(getActivity(), getAllCartDetailsFromPaidCartList, productDAO, this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        receiptsRecyclerView.setLayoutManager(layoutManager);
        receiptsRecyclerView.setAdapter(receiptAdapter);

        return view;
    }

    public void reloadData() {
        paidCartList = cartDAO.getPaidCartsByUserId(currentUser.getUserId());
        getAllCartDetailsFromPaidCartList = cartDetailDAO.getCartDetailsUnpaidByListCartsUnPaid(paidCartList);
        receiptAdapter.setData(getAllCartDetailsFromPaidCartList);
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRefreshOnResume = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(shouldRefreshOnResume) {
            reloadData();
            Toast.makeText(getActivity(), "On Resume in OrderHistoryFragment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clickToBuyAgain(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_product", product);
        bundle.putSerializable("current_user", currentUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}