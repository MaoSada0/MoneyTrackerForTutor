package com.example.electronicjournal.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Expense;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.transaction.TransactionForExpenseProfit;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.ChangeProfitExpanseFragment;

import java.util.List;

public class ExpenseProfitsViewAdapterRV extends RecyclerView.Adapter<ExpenseProfitsViewAdapterRV.viewholder> {

    private List<TransactionForExpenseProfit> items;
    private Context context;



    public ExpenseProfitsViewAdapterRV(List<TransactionForExpenseProfit> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_expenses_profits, parent, false);

        return new viewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        TransactionForExpenseProfit transaction = items.get(position);

        if(transaction instanceof Expense) {

            Expense expenseTemp = (Expense) transaction;
            holder.amountOfExpenseTV.setText("-" + expenseTemp.getAmount());
            holder.amountOfExpenseTV.setTextColor(Color.parseColor("#FF0000"));
            holder.dateOfExpenseTV.setText("Дата: " + expenseTemp.getDate());
            holder.nameOfExpenseTV.setText(expenseTemp.getName());

        } else {
            Profit profitTemp = (Profit) transaction;

            holder.amountOfExpenseTV.setText("+" + profitTemp.getAmount());
            holder.amountOfExpenseTV.setTextColor(Color.parseColor("#26FF00"));

            holder.dateOfExpenseTV.setText("Дата: " + profitTemp.getDate());
            holder.nameOfExpenseTV.setText(profitTemp.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.extraInfoLayout.getVisibility() == View.VISIBLE) {
                    holder.extraInfoLayout.setVisibility(View.GONE);
                    holder.wrapper.setBackgroundResource(R.drawable.item_rv_background);
                } else {
                    holder.wrapper.setBackgroundResource(R.drawable.item_selected_rv_background);
                    holder.extraInfoLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeProfitExpanseFragment changeProfitExpanseFragment = ChangeProfitExpanseFragment.newInstance(transaction);
                changeProfitExpanseFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), changeProfitExpanseFragment.getTag());

            }
        });

        Glide.with(context);

    }

    public TransactionForExpenseProfit removeItem(int position) {

        TransactionForExpenseProfit transaction = items.get(position);

        if(transaction instanceof Profit) {
            Profit profitToDelete = (Profit) transaction;
            App.getInstance()
                    .getProfitsDbRepository()
                    .deleteProfit(profitToDelete);
            items.remove(profitToDelete);
        } else {
            Expense expenseToDelete = (Expense) transaction;
            App.getInstance().getExpensesDbRepository().deleteExpense(expenseToDelete);
            items.remove(expenseToDelete);

        }
        notifyItemRemoved(position);
        return transaction;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView dateOfExpenseTV;
        TextView amountOfExpenseTV;
        TextView nameOfExpenseTV;
        TextView dayWordTV;

        ImageButton changeBtn;

        View view;

        ConstraintLayout extraInfoLayout;

        LinearLayout wrapper;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            dateOfExpenseTV = itemView.findViewById(R.id.date_of_expense_TV);
            amountOfExpenseTV = itemView.findViewById(R.id.amount_of_expense_TV);
            nameOfExpenseTV = itemView.findViewById(R.id.name_of_expense_TV);
            dayWordTV  = itemView.findViewById(R.id.day_word_TV);

            changeBtn = itemView.findViewById(R.id.change_expense_profit_btn);

            extraInfoLayout = itemView.findViewById(R.id.extraInfoLayout);


            wrapper = itemView.findViewById(R.id.wrapperCL);
        }

    }
}
