package com.example.electronicjournal.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesSummaryFragment extends Fragment {

    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expense_summary_fragment, container, false);

        int greenColor = Color.GREEN;
        int redColor = Color.RED;

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(redColor);
        colors.add(greenColor);

        pieChart = view.findViewById(R.id.profits_expenses_PCh);

        List<PieEntry> pieEntries = new ArrayList<>();

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM.yyyy");

        String currentMonth = formatter.format(currentDate);

        pieEntries.add(new PieEntry(
                (float) App.getInstance()
                .getExpensesDbRepository()
                .getTotalExpensesAmountForMonthYear(currentMonth),
                "Расходы"));
        pieEntries.add(new PieEntry(
                (float) App.getInstance()
                .getProfitsDbRepository()
                .getTotalProfitAmountForMonthYear(currentMonth),
                "Доходы"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Этот месяц");

        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(24f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        pieChart.getLegend().setEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(40f);

        pieChart.setCenterText("Этот месяц");
        pieChart.setCenterTextSize(24f);
        pieChart.setCenterTextColor(Color.BLACK);


        pieChart.getDescription().setEnabled(false);
        pieChart.animate();

        return view;
    }


}