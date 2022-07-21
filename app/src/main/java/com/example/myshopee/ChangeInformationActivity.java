package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopee.MyUtils.CommonUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import DAO.AccountDAO;
import Model.User;

public class ChangeInformationActivity extends AppCompatActivity {
    private AccountDAO accountDAO;
    private User currentUser;

    private MaterialToolbar toolbar;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputAddress;
    private TextInputLayout textInputPhone;
    private Button btnChangeInfoButton;
    private Button btnReset;

    private void mapping() {
        toolbar = findViewById(R.id.change_information_activity_toolbar);
        textInputUsername = findViewById(R.id.textInputUsername);
        textInputAddress = findViewById(R.id.textInputAddress);
        textInputPhone = findViewById(R.id.textInputPhone);
        btnChangeInfoButton = findViewById(R.id.btnChangeInfoButton);
        btnReset = findViewById(R.id.btnReset);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        mapping();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
        accountDAO = new AccountDAO(this);
        currentUser = CommonUtils.getCurrentUser(this);

        textInputUsername.getEditText().setText(currentUser.getUserName());
        textInputAddress.getEditText().setText(currentUser.getAddress());
        textInputPhone.getEditText().setText(currentUser.getPhone());

        setListeners();
    }

    private void reset() {
        textInputUsername.getEditText().setText(currentUser.getUserName());
        textInputAddress.getEditText().setText(currentUser.getAddress());
        textInputPhone.getEditText().setText(currentUser.getPhone());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setListeners() {
        btnReset.setOnClickListener(v -> {
            reset();
        });

        btnChangeInfoButton.setOnClickListener(v ->  {
            // hide keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            // validate
            if(validate()) {
                User user = currentUser;
                user.setUserName(textInputUsername.getEditText().getText().toString().trim());
                user.setAddress(textInputAddress.getEditText().getText().toString().trim());
                user.setPhone(textInputPhone.getEditText().getText().toString().trim());
                user = accountDAO.updateInfo(user);
                if(user != null) {
                    currentUser = user;
                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();

                }
            }
            else {
                Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();

            }
        });

        textInputUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textInputUsername.isErrorEnabled()) {
                    textInputUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textInputAddress.isErrorEnabled()) {
                    textInputAddress.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        textInputPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textInputPhone.isErrorEnabled()) {
                    textInputPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private boolean validate() {
        String newUsername = textInputUsername.getEditText().getText().toString().trim();
        String newAddress = textInputAddress.getEditText().getText().toString().trim();
        String newPhone = textInputPhone.getEditText().getText().toString().trim();
        boolean validate = true;
        if(newUsername.isEmpty()) {
            textInputUsername.setErrorEnabled(true);
            textInputUsername.setError("Trường này không được để trống!");
            validate = false;
        }
        if(newUsername.isEmpty()) {
            textInputAddress.setErrorEnabled(true);
            textInputAddress.setError("Trường này không được để trống!");
            validate = false;
        }
        if(newUsername.isEmpty()) {
            textInputPhone.setErrorEnabled(true);
            textInputPhone.setError("Trường này không được để trống!");
            validate = false;
        }

        return validate;

    }


}