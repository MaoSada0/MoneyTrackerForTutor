package com.example.electronicjournal.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.electronicjournal.database.ProfitsDatabase;
import com.example.electronicjournal.database.dao.ProfitDao;
import com.example.electronicjournal.database.entity.Profit;

import java.util.List;

public class ProfitsDbRepository implements ProfitDao {
    private ProfitDao profitDao;
    private ProfitsDatabase profitsDatabase;

    public ProfitsDbRepository(Context context) {
        profitsDatabase = Room.databaseBuilder(context, ProfitsDatabase.class, "Profits")
                .allowMainThreadQueries()
                .build();
        profitDao = profitsDatabase.getProfitDao();
    }

    @Override
    public void addProfit(Profit profit) {
        profitDao.addProfit(profit);
    }

    @Override
    public void updateProfit(Profit profit) {
        profitDao.updateProfit(profit);
    }

    @Override
    public void deleteProfit(Profit profit) {
        profitDao.deleteProfit(profit);
    }

    @Override
    public List<Profit> getAllProfits() {
        return profitDao.getAllProfits();
    }

    @Override
    public LiveData<List<Profit>> getAllProfitsLD() {
        return profitDao.getAllProfitsLD();
    }

    @Override
    public double getTotalProfitAmountForMonthYear(String monthYear) {
        return profitDao.getTotalProfitAmountForMonthYear(monthYear);
    }

    @Override
    public List<Profit> getProfitsOnDate(String date) {
        return profitDao.getProfitsOnDate(date);
    }

    @Override
    public LiveData<List<Profit>> getProfitsOnDateLD(String date) {
        return profitDao.getProfitsOnDateLD(date);
    }

}
