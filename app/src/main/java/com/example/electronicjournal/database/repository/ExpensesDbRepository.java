package com.example.electronicjournal.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.electronicjournal.database.ExpensesDatabase;
import com.example.electronicjournal.database.dao.ExpensesDao;
import com.example.electronicjournal.database.entity.Expense;

import java.util.List;

public class ExpensesDbRepository implements ExpensesDao {
    private ExpensesDao expensesDao;
    private ExpensesDatabase databaseExpenses;

    public ExpensesDbRepository(Context context) {
        databaseExpenses = Room.databaseBuilder(context, ExpensesDatabase.class, "Expenses")
                .allowMainThreadQueries()
                .build();
        expensesDao = databaseExpenses.getExpensesDao();
    }

    @Override
    public void addExpense(Expense expense) {
        expensesDao.addExpense(expense);
    }

    @Override
    public void updateExpense(Expense expense) {
        expensesDao.updateExpense(expense);
    }

    @Override
    public void deleteExpense(Expense expense) {
        expensesDao.deleteExpense(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expensesDao.getAllExpenses();
    }

    @Override
    public LiveData<List<Expense>> getAllExpensesLD() {
        return expensesDao.getAllExpensesLD();
    }

    @Override
    public double getTotalExpensesAmountForMonthYear(String monthYear) {
        return expensesDao.getTotalExpensesAmountForMonthYear(monthYear);
    }

    @Override
    public List<Expense> getExpensesOnDate(String date) {
        return expensesDao.getExpensesOnDate(date);
    }

    @Override
    public LiveData<List<Expense>> getExpensesOnDateLD(String date) {
        return expensesDao.getExpensesOnDateLD(date);
    }
}
