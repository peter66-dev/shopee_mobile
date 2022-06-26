package com.example.myshopee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText username;
    TextInputEditText password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data.hasExtra("logoutMsg")) {
            Toast.makeText(this, data.getStringExtra("logoutMsg"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "I am onCreate", Toast.LENGTH_LONG).show();

        //Ánh xạ
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // Gắn sự kiên onClick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String user = username.toString();
//                String pwd = password.toString();
//                if (user.equals("ahihi") && pwd.equals("ahihi")) {
////                    Toast.makeText(MainActivity.this, "Username: " + user + ", password: " + pwd, Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(MainActivity.this, Home.class);
//                    intent.putExtra("studentName", "Phuong");
//                    startActivityForResult(intent, 2);
//                } else {
//                    Toast.makeText(MainActivity.this, "Không thành công!", Toast.LENGTH_LONG).show();
//                }
                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putExtra("studentName", "Phuong");
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(MainActivity.this, "I am onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this, "I am onDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "I am onPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(MainActivity.this, "I am onResume", Toast.LENGTH_LONG).show();
    }
}