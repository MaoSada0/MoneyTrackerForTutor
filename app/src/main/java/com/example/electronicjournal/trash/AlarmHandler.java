package com.example.electronicjournal.trash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {
    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }

    public void setAlarmManager(){
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long triggerAfter = 1000;
        long triggerInterval = 24 * 60 * 60 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAfter, triggerInterval, sender);


    }
}
