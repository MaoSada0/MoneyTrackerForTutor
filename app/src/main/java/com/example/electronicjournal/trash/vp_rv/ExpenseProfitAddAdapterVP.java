package com.example.electronicjournal.trash.vp_rv;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.electronicjournal.Main.App;

public class ExpenseProfitAddAdapterVP extends FragmentStateAdapter {

    public ExpenseProfitAddAdapterVP(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return App.getInstance().getAddExpenseFragment();
            case 1:
                return App.getInstance().getAddProfitFragment();
            default:
                return App.getInstance().getAddExpenseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
