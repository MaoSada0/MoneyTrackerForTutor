package com.example.electronicjournal.Main;

import android.app.Application;

import com.example.electronicjournal.database.repository.ExpensesDbRepository;
import com.example.electronicjournal.database.repository.ProfitsDbRepository;
import com.example.electronicjournal.database.repository.StudentsDbRepository;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.ChangeProfitExpanseFragment;
import com.example.electronicjournal.fragments.student_fragments.AddStudentFragment;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.AddExpenseFragment;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.AddProfitFragment;
import com.example.electronicjournal.fragments.ViewExpensesProfitsFragment;
import com.example.electronicjournal.fragments.student_fragments.ChangeStudentFragment;
import com.example.electronicjournal.trash.profit_expanse_view_fragments.ViewProfitsFragment;

public class App extends Application {

    private static App instance;

    private ExpensesDbRepository expensesDbRepository;
    private ProfitsDbRepository profitsDbRepository;
    private StudentsDbRepository studentsDbRepository;

    private AddExpenseFragment addExpenseFragment;
    private AddProfitFragment addProfitFragment;
    private ViewExpensesProfitsFragment viewExpensesProfitsFragment;
    private ViewProfitsFragment viewProfitsFragment;
    private AddStudentFragment addStudentFragment;
    private ChangeStudentFragment changeStudentFragment;
    private ChangeProfitExpanseFragment changeProfitExpanseFragment;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        expensesDbRepository = new ExpensesDbRepository(this);
        profitsDbRepository = new ProfitsDbRepository(this);
        studentsDbRepository = new StudentsDbRepository(this);

        addStudentFragment = new AddStudentFragment();
        addExpenseFragment = new AddExpenseFragment();
        addProfitFragment = new AddProfitFragment();
        viewExpensesProfitsFragment = new ViewExpensesProfitsFragment();
        viewProfitsFragment = new ViewProfitsFragment();
        changeStudentFragment = new ChangeStudentFragment();
        changeProfitExpanseFragment = new ChangeProfitExpanseFragment();
    }

    public String getDateFormat(){
        return "dd.MM.yyyy";
    }

    public String[] getDaysOfWeek(){
        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        return daysOfWeek;
    }

    public static App getInstance() {
        return instance;
    }

    public ChangeStudentFragment getChangeStudentFragment() {
        return changeStudentFragment;
    }

    public ChangeProfitExpanseFragment getChangeProfitExpanseFragment() {
        return changeProfitExpanseFragment;
    }

    public StudentsDbRepository getStudentsDbRepository() {
        return studentsDbRepository;
    }

    public ExpensesDbRepository getExpensesDbRepository() {
        return expensesDbRepository;
    }

    public ProfitsDbRepository getProfitsDbRepository() {
        return profitsDbRepository;
    }


    public AddStudentFragment getAddStudentFragment() {
        return addStudentFragment;
    }

    public ViewExpensesProfitsFragment getViewExpensesFragment() {
        return viewExpensesProfitsFragment;
    }

    public ViewProfitsFragment getViewProfitsFragment() {
        return viewProfitsFragment;
    }


    public AddExpenseFragment getAddExpenseFragment() {
        return addExpenseFragment;
    }

    public AddProfitFragment getAddProfitFragment() {
        return addProfitFragment;
    }
}
