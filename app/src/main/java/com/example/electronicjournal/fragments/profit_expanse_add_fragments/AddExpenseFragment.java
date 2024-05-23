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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Expense;
import com.example.electronicjournal.database.repository.ExpensesDbRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class AddExpenseFragment extends BottomSheetDialogFragment {

    EditText amountED;
    EditText nameED;

    Button confirmBtn;
    Button getDateBtn;

    SeekBar seekBar;

    Animation scaleUp, scaleDown;

    TextView dateTv;

    Calendar myCalendar;

    private static final long START_DATE_MILLIS;
    private static final long END_DATE_MILLIS;
    private static final long DAYS_RANGE;

    static {
        Calendar startCal = Calendar.getInstance();
        startCal.set(0000, Calendar.JANUARY, 1);
        START_DATE_MILLIS = startCal.getTimeInMillis();

        Calendar endCal = Calendar.getInstance();
        endCal.set(10000, Calendar.DECEMBER, 30);
        END_DATE_MILLIS = endCal.getTimeInMillis();

        long diffInMillis = END_DATE_MILLIS - START_DATE_MILLIS;
        DAYS_RANGE = TimeUnit.MILLISECONDS.toDays(diffInMillis);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.add_expense_fragment, container, false);

        amountED = view.findViewById(R.id.amount_ED);
        nameED = view.findViewById(R.id.name_ED);

        confirmBtn = view.findViewById(R.id.confirm_BTN);
        getDateBtn = view.findViewById(R.id.get_date_BTN);

        seekBar = view.findViewById(R.id.seekBar);

        seekBar.setMax((int) DAYS_RANGE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long selectedDateMillis = START_DATE_MILLIS + TimeUnit.DAYS.toMillis(progress);
                String formattedDate = formatMillisToDate(selectedDateMillis);

                getDateBtn.setText(formattedDate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       // dateTv = view.findViewById(R.id.date_TV);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        ExpensesDbRepository dbExpensesRepository = App.getInstance().getExpensesDbRepository();

        myCalendar = Calendar.getInstance();
        updateTVdate(myCalendar);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(!(amountED.getText().toString().isEmpty() || getDateBtn.getText().toString().isEmpty())){
                    dbExpensesRepository.addExpense(new Expense("expenses",
                            Integer.parseInt(amountED.getText().toString()),
                            getDateBtn.getText().toString(),
                            nameED.getText().toString()));

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

    private String formatMillisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(millis);
    }

    private void updateTVdate(Calendar myCalendar) {
        String format = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.UK);

        //dateTv.setText(simpleDateFormat.format(myCalendar.getTime()));
        getDateBtn.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

}