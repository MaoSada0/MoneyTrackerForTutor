package com.example.electronicjournal.trash.another;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.Student;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};

        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

        return false;

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}

class MyJobScheduler {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(60 * 1000);
            builder.setOverrideDeadline(30 * 60 * 1000);
        } else {
            builder.setPeriodic( 60 * 1000);
        }


    }
}


class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            MyJobScheduler.scheduleJob(context);
        }
    }
}