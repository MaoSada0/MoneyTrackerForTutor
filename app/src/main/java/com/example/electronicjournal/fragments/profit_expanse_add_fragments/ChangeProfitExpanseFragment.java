package com.example.electronicjournal.fragments.profit_expanse_add_fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Expense;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.Student;
import com.example.electronicjournal.database.entity.transaction.TransactionForExpenseProfit;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ChangeProfitExpanseFragment extends BottomSheetDialogFragment {

    private TransactionForExpenseProfit transaction;

    private boolean isProfit;

    private EditText amountED;
    private EditText nameED;

    private Button confirmBtn;
    private Button getDateBtn;

    private Animation scaleUp, scaleDown;

    private Calendar myCalendar;

    private String datePattern = App.getInstance().getDateFormat();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_profit_expanse, container, false);

        if (getArguments() != null) {
            transaction = (TransactionForExpenseProfit) getArguments().getSerializable("TransactionForExpenseProfit");
        }

        isProfit = transaction instanceof Profit;

        amountED = view.findViewById(R.id.amount_ED);

        nameED = view.findViewById(R.id.name_ED);

        confirmBtn = view.findViewById(R.id.confirm_BTN);
        getDateBtn = view.findViewById(R.id.get_date_BTN);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        myCalendar = Calendar.getInstance();
        setED();
        updateTVdate(myCalendar);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(!(amountED.getText().toString().isEmpty() || nameED.getText().toString().isEmpty())){

                    if(isProfit){
                        ((Profit) transaction).setAmount(Integer.parseInt(amountED.getText().toString()));
                        ((Profit) transaction).setName(nameED.getText().toString());
                        ((Profit) transaction).setDate(getDateBtn.getText().toString());
                        App.getInstance().getProfitsDbRepository().updateProfit((Profit) transaction);
                    } else {
                        ((Expense) transaction).setAmount(Integer.parseInt(amountED.getText().toString()));
                        ((Expense) transaction).setName(nameED.getText().toString());
                        ((Expense) transaction).setDate(getDateBtn.getText().toString());
                        App.getInstance().getExpensesDbRepository().updateExpense((Expense) transaction);
                    }
                    amountED.setText("");
                    nameED.setText("");
                    dismiss();
                }
            }
        });

        confirmBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    confirmBtn.startAnimation(scaleDown);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    confirmBtn.startAnimation(scaleUp);
                    break;
            }
            return false;
        });

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTVdate(myCalendar);

            }
        };

        getDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(),
                        dateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    private void setED(){
        if(isProfit){
            nameED.setText(((Profit) transaction).getName());
            amountED.setText(((Profit) transaction).getAmount() + "");
            getDateBtn.setText(((Profit) transaction).getDate());

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            try {
                myCalendar.setTime(sdf.parse(((Profit) transaction).getDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            nameED.setText(((Expense) transaction).getName());
            amountED.setText(((Expense) transaction).getAmount() + "");
            getDateBtn.setText(((Expense) transaction).getDate());

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            try {
                myCalendar.setTime(sdf.parse(((Expense) transaction).getDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ChangeProfitExpanseFragment newInstance(TransactionForExpenseProfit transaction){
        ChangeProfitExpanseFragment fragment = new ChangeProfitExpanseFragment();
        Bundle args = new Bundle();
        args.putSerializable("TransactionForExpenseProfit", transaction);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateTVdate(Calendar myCalendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.UK);
        getDateBtn.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}