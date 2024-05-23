package com.example.electronicjournal.Main.DailyCheck;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.database.entity.Expense;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.Student;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DailyCheckWorker extends Worker {


    public DailyCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(App.getInstance().getDateFormat());
        String todayStr = currentDate.format(formatter);


        String[] daysOfWeek = App.getInstance().getDaysOfWeek();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        List<Student> studentsToday = App.getInstance()
                .getStudentsDbRepository()
                .getAllStudentsOnDayOfWeek(daysOfWeek[dayOfWeek.getValue() - 1]);

        for (Student st: studentsToday){
            App.getInstance()
                    .getProfitsDbRepository()
                    .addProfit(new Profit(
                            "lesson",
                            st.getPriceOfLesson(),
                            todayStr,
                            "Занятие с " + st.getName()
                    ));
        }

        return Result.success();
    }


}
