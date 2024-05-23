package com.example.electronicjournal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.electronicjournal.database.entity.Profit;

import java.util.List;

@Dao
public interface ProfitDao {
    @Insert
    void addProfit(Profit profit);

    @Update
    void updateProfit(Profit profit);

    @Delete
    void deleteProfit(Profit profit);

    @Query("select * FROM Profits")
    List<Profit> getAllProfits();

    @Query("select * FROM Profits")
    LiveData<List<Profit>> getAllProfitsLD();

    @Query("SELECT SUM(amount) FROM Profits WHERE SUBSTR(date, 4, 7) = :monthYear")
    double getTotalProfitAmountForMonthYear(String monthYear);

    @Query("SELECT * FROM Profits WHERE date = :date")
    List<Profit> getProfitsOnDate(String date);

    @Query("SELECT * FROM Profits WHERE date = :date")
    LiveData<List<Profit>> getProfitsOnDateLD(String date);
}
