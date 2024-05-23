package com.example.electronicjournal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.electronicjournal.database.dao.ExpensesDao;
import com.example.electronicjournal.database.entity.Expense;

@Database(
        entities = {Expense.class},
        version = 1
)
abstract public class ExpensesDatabase extends RoomDatabase {
    ExpensesDao expensesDao;

    public abstract ExpensesDao getExpensesDao();
}
