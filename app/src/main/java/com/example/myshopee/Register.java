package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import DAO.AccountDAO;

public class Register extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText phone;
    TextInputEditText address;
    Button btnRegister;
    AccountDAO accountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        btnRegister = findViewById(R.id.btnRegister);
        accountDAO = new AccountDAO(Register.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountDAO.create(username.getText().toString(), password.getText().toString(),
                        phone.getText().toString(), address.getText().toString())) {
                    Toast.makeText(Register.this, "Created user successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}