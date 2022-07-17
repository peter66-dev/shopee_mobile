package com.example.myshopee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myshopee.fragment.transformer.ZoomOutPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private TextView welcomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager2 = findViewById(R.id.view_page_2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        welcomeUser = findViewById(R.id.welcomeUser);

        // Get username in intent
        Intent intent = getIntent();
        welcomeUser.setText(intent.getStringExtra("welcomeMessage"));

        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
//        viewPager2.setPageTransformer(new DepthPageTransformer());

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    viewPager2.setCurrentItem(0);
                    welcomeUser.setVisibility(View.VISIBLE);
                } else if (id == R.id.bottom_history_receipt) {
                    viewPager2.setCurrentItem(1);
                    welcomeUser.setVisibility(View.GONE);
                } else if (id == R.id.bottom_profile) {
                    viewPager2.setCurrentItem(2);
                    welcomeUser.setVisibility(View.VISIBLE);

                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 1: {
                        bottomNavigationView.getMenu().findItem(R.id.bottom_history_receipt).setChecked(true);
                        break;
                    }
                    case 2: {
                        bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                    }
                    default: {
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    }
                }
            }
        });
    }
}