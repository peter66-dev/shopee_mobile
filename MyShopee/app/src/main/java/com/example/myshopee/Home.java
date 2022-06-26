package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    Button btnLogout;

    @Override
    public void finish() {
        Intent data = new Intent(Home.this, MainActivity.class);
        data.putExtra("logoutMsg", "Bạn đã logout!");
        setResult(2, data);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.btnLogout);

        Toast.makeText(Home.this, getIntent().getStringExtra("studentName"), Toast.LENGTH_SHORT).show();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}