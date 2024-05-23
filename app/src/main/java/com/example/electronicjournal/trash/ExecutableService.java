package com.example.electronicjournal.trash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.Student;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExecutableService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String[] daysOfWeek = App.getInstance().getDaysOfWeek();

        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String formattedDate = currentDate.format(formatter);

        List<Student> stToday = App.getInstance()
                .getStudentsDbRepository()
                .getAllStudentsOnDayOfWeek(daysOfWeek[dayOfWeek.getValue() - 1]);


        Log.d("tt",stToday.toString() );
        Log.d("ttt"," fd" );
        for (Student st: stToday) {
            App.getInstance()
                    .getProfitsDbRepository()
                    .addProfit(new Profit("Ученик",
                            st.getPriceOfLesson(),
                            formattedDate,
                            "Занятие с " + st.getName()));
        }
    }
}
