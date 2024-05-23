package com.example.electronicjournal.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.electronicjournal.Main.DailyCheck.DailyCheckWorker;
import com.example.electronicjournal.R;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.AddExpenseFragment;
import com.example.electronicjournal.fragments.ViewExpensesProfitsFragment;
import com.example.electronicjournal.fragments.ExpensesSummaryFragment;
import com.example.electronicjournal.fragments.student_fragments.StudentsProfitFragment;

import com.example.electronicjournal.trash.fragment.LauncherForProfitExpanseAdd;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    FragmentManager fragmentManager;
    AddExpenseFragment addExpenseFragment;
    ViewExpensesProfitsFragment viewExpensesProfitsFragment;
    ExpensesSummaryFragment expensesSummaryFragment;
    StudentsProfitFragment studentsProfitFragment;

    LauncherForProfitExpanseAdd launcherForProfitExpanseAdd;



    TextView labelOfFragmentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(DailyCheckWorker.class, 1, TimeUnit.DAYS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "DailyTask",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
        );

        addExpenseFragment = new AddExpenseFragment();
        viewExpensesProfitsFragment = new ViewExpensesProfitsFragment();
        expensesSummaryFragment = new ExpensesSummaryFragment();
        studentsProfitFragment = new StudentsProfitFragment();
        launcherForProfitExpanseAdd = new LauncherForProfitExpanseAdd();


        bottomNavigationView = findViewById(R.id.BNV);
        frameLayout = findViewById(R.id.FL);

        labelOfFragmentTV = findViewById(R.id.label_of_fragment_TV);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FL, studentsProfitFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.students_menu){
                    loadFragment(studentsProfitFragment, fragmentManager);
                    labelOfFragmentTV.setText("Ученики");
                    Log.d("fragmentChange", "studentsProfitFragment");
                } else if(itemID == R.id.view_expenses){
                    loadFragment(viewExpensesProfitsFragment, fragmentManager);
                    labelOfFragmentTV.setText("История");
                    Log.d("fragmentChange", "viewExpensesFragment");
                } else if(itemID == R.id.expenses_summary){
                    loadFragment(expensesSummaryFragment, fragmentManager);
                    labelOfFragmentTV.setText("Статистика");
                    Log.d("fragmentChange", "expensesSummaryFragment");
                }
                return true;
            }
        });

    }

    private void loadFragment(Fragment fragment, FragmentManager fragmentManager){
        fragmentManager.beginTransaction().replace(R.id.FL, fragment).commit();
    }
}