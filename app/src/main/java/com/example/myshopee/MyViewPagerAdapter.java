package com.example.myshopee;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myshopee.fragment.InfomationFragment;
import com.example.myshopee.fragment.HomeFragment;
import com.example.myshopee.fragment.OrderHistoryFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {


    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: {
                return new OrderHistoryFragment();
            }
            case 2: {
                return new InfomationFragment();
            }
            default: {
                return new HomeFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    
}
