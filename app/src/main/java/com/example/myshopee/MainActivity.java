package com.example.myshopee;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import DAO.AccountDAO;
import Model.User;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegister;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;
    CheckBox rememberMe;
    AccountDAO accountDAO;
    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "I am onCreate", Toast.LENGTH_LONG).show();

        //Ánh xạ
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        rememberMe = findViewById(R.id.rememberMe);

        accountDAO = new AccountDAO(MainActivity.this);
        loadData();


        // Gắn sự kiên onClick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();
                    boolean check = rememberMe.isChecked();
                    User user = accountDAO.checkLogin(username, password);
                    if (user != null) {
                        if (user.getRoleId() == 1) { // bug here
                            saveUser(username, password, check);
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.putExtra("welcomeMessage", "Xin chào, " + username + "!");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("current_user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (user.getRoleId() == 2) {
                            Toast.makeText(MainActivity.this, "OK! ADMIN!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("TAG", "Error at MainActivity! Detail: " + ex.getMessage());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("information.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check", false);
        if (check) {
            txtUsername.setText(pref.getString("username", ""));
            txtPassword.setText(pref.getString("password", ""));
            rememberMe.setChecked(check);
        }
    }

    private void saveUser(String user, String pwd, boolean check) {
        SharedPreferences pref = getSharedPreferences("information.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (check) {
            editor.putString("username", user);
            editor.putString("password", pwd);
            editor.putBoolean("check", check);
        } else {
            editor.clear();
        }
        editor.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(MainActivity.this, "I am onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(MainActivity.this, "I am onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(MainActivity.this, "I am onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(MainActivity.this, "I am onResume", Toast.LENGTH_SHORT).show();
    }
}