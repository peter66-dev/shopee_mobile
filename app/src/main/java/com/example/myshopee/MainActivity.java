package com.example.myshopee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import DAO.UserDAO;
import Model.User;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText username;
    TextInputEditText password;
    CheckBox rememberMe;
    UserDAO userDao;
    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "I am onCreate", Toast.LENGTH_LONG).show();

        //Ánh xạ
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rememberMe = findViewById(R.id.rememberMe);

        userDao = new UserDAO(MainActivity.this);
        loadData();


        // Gắn sự kiên onClick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String userString = username.getText().toString();
                    String pwd = password.getText().toString();
                    boolean check = rememberMe.isChecked();
                    user = new User(userString, pwd);
                    if (userDao.checkLogin(user)) {
                        saveUser(userString, pwd, check);
                        Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.putExtra("welcomeMessage", "Xin chào, " + userString + "!");
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login fail!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Log.e("TAG", "Error detail: "+ ex.getMessage());
                }
            }
        });
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("information.dat", MODE_PRIVATE);
        boolean check = pref.getBoolean("check", false);
        if (check) {
            username.setText(pref.getString("username", ""));
            password.setText(pref.getString("password", ""));
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
        Toast.makeText(MainActivity.this, "I am onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "I am onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(MainActivity.this, "I am onResume", Toast.LENGTH_SHORT).show();
    }
}