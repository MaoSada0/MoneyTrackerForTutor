package com.example.electronicjournal.trash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.electronicjournal.R;
import com.example.electronicjournal.trash.vp_rv.ExpenseProfitAddAdapterVP;
import com.google.android.material.tabs.TabLayout;

public class LauncherForProfitExpanseAdd extends Fragment {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_launcher_for_profit_expanse_add, container, false);

        tabLayout = view.findViewById(R.id.change_add_TL);
        viewPager = view.findViewById(R.id.what_add_VP);


        ExpenseProfitAddAdapterVP adapterVP = new ExpenseProfitAddAdapterVP(getActivity());
        viewPager.setAdapter(adapterVP);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });



        return view;
    }

}