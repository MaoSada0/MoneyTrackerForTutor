package com.example.electronicjournal.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.electronicjournal.database.entity.transaction.TransactionForExpenseProfit;

@Entity(tableName = "Expenses")
public class Expense extends TransactionForExpenseProfit {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    private Integer id;

    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "name")
    private String name;

    public Expense(String categoryName, int amount, String date, String name) {
        this.categoryName = categoryName;
        this.amount = amount;
        this.date = date;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}