package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.MyUtils.PreferenceManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import Model.User;

public class PaymentActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextView txvTotalCost;
    private TextView txvTotalCostInBill;
    private TextView txvUsername;
    private Button btnDone;
    private Bundle bundle = new Bundle();

    private void mapping() {
        this.toolbar = findViewById(R.id.payment_activity_toolbar);
        this.txvTotalCost = findViewById(R.id.txvTotalCost);
        this.txvUsername = findViewById(R.id.txvUsername);
        this.txvTotalCostInBill = findViewById(R.id.txvtotalCostInBill);
        this.btnDone = findViewById(R.id.btnDone);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bundle = getIntent().getExtras();
        if(bundle != null) {
            txvUsername.setText(bundle.getString("username"));
            double totalCostPaid = bundle.getDouble("totalCostPaid");
            txvTotalCost.setText(CommonUtils.getReadableCostFromDouble(totalCostPaid));
            txvTotalCostInBill.setText(CommonUtils.getReadableCostFromDouble(totalCostPaid));
        }

        btnDone.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        Gson gson = new Gson();
        User currenUser = CommonUtils.getCurrentUser(this);
        String objectToJson = gson.toJson(currenUser);
        preferenceManager.putString("current_user", objectToJson);

        Intent intent = new Intent(PaymentActivity.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("welcomeMessage", "Xin chào, " + currenUser.getUserName() + "!");
        Bundle bundle = new Bundle();
        bundle.putSerializable("current_user", currenUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}