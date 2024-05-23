package com.example.electronicjournal.trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.electronicjournal.Main.MainActivity;

public class ActivityLauncher extends AppCompatActivity {

    static final String LOGINSTRING = "login";
    static final String PASSWORDSTRING = "password";
    static SharedPreferences pref;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getPreferences(MODE_PRIVATE);

        String lgTemp = pref.getString(LOGINSTRING, "none");
        String psTemp = pref.getString(PASSWORDSTRING, "none");

        if(flag){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}