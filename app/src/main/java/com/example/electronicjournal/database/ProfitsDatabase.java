package com.example.electronicjournal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.electronicjournal.database.dao.ProfitDao;
import com.example.electronicjournal.database.entity.Profit;

@Database(
        entities = {Profit.class},
        version = 1
)
abstract public class ProfitsDatabase extends RoomDatabase {
    ProfitDao profitDao;

    public abstract ProfitDao getProfitDao();
}
