package com.example.electronicjournal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.electronicjournal.database.entity.Expense;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Insert
    void addExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("select * FROM Expenses")
    List<Expense> getAllExpenses();

    @Query("select * FROM Expenses")
    LiveData<List<Expense>> getAllExpensesLD();

    @Query("SELECT SUM(amount) FROM Expenses WHERE SUBSTR(date, 4, 7) = :monthYear")
    double getTotalExpensesAmountForMonthYear(String monthYear);

    @Query("SELECT * FROM Expenses WHERE date = :date")
    List<Expense> getExpensesOnDate(String date);

    @Query("SELECT * FROM Expenses WHERE date = :date")
    LiveData<List<Expense>> getExpensesOnDateLD(String date);

}
