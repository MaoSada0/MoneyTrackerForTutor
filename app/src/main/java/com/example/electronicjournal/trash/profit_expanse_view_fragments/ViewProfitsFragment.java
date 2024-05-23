package com.example.electronicjournal.trash.profit_expanse_view_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.trash.vp_rv.ProfitAdapterRV;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.decorations.DividerItemDecoration;

import java.util.List;


public class ViewProfitsFragment extends Fragment {

    private RecyclerView.Adapter adapterProfits;
    private RecyclerView recyclerViewProfits;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_profits, container, false);
        initRecyclerView();

        App.getInstance()
                .getProfitsDbRepository()
                .getAllProfitsLD()
                .observe(getViewLifecycleOwner(), new Observer<List<Profit>>() {
            @Override
            public void onChanged(List<Profit> profits) {
                initRecyclerView();
            }
        });

        return view;
    }

    private void initRecyclerView() {
        List<Profit> items = App.getInstance()
                .getProfitsDbRepository()
                .getAllProfits();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), R.drawable.divider);

        recyclerViewProfits = view.findViewById(R.id.view_profits_RV);
        recyclerViewProfits.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewProfits.addItemDecoration(itemDecoration);

        adapterProfits = new ProfitAdapterRV(items);
        recyclerViewProfits.setAdapter(adapterProfits);

    }
}