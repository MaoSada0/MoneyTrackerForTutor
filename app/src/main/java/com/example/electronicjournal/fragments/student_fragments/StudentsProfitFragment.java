package com.example.electronicjournal.fragments.student_fragments;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.adapters.StudentsViewAdapterRV;
import com.example.electronicjournal.database.entity.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StudentsProfitFragment extends Fragment {

    private FloatingActionButton addStudentBtn;

    private AddStudentFragment addStudentFragment;

    private StudentsViewAdapterRV adapterStudents;
    private RecyclerView recyclerViewStudents;

    boolean isFirst = false;

    private SearchView searchView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.students_fragment, container, false);

        addStudentBtn = view.findViewById(R.id.add_student_BTN);

        searchView = view.findViewById(R.id.searchViewStudents);

        recyclerViewStudents = view.findViewById(R.id.view_students_RV);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudentFragment = App.getInstance().getAddStudentFragment();
                addStudentFragment.show(getParentFragmentManager(), addStudentFragment.getTag());
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initRecyclerView(newText);
                return false;
            }
        });

        if(isFirst) {
            initRecyclerView("");
            isFirst = false;
        }

        App.getInstance()
                .getStudentsDbRepository()
                .getAllStudentsLD()
                .observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
                    @Override
                    public void onChanged(List<Student> students) {
                        initRecyclerView("");
                    }
                });

        return view;
    }

    private void initItemTouchHelper(){

        ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Student ans = adapterStudents.removeItem(position);

                Snackbar.make(recyclerViewStudents, "Вы удалили Студента", Snackbar.LENGTH_LONG)
                        .setAction("Отменить", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                App.getInstance().getStudentsDbRepository().addStudent(ans);
                            }
                        }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.delete))
                        .addSwipeLeftActionIcon(R.drawable.trash)
                        .addCornerRadius(1, 20)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerViewStudents);
    }

    private void initRecyclerView(String s) {
        List<Student> items = App.getInstance()
                .getStudentsDbRepository()
                .getAllStudentsWithSubstrInName(s);

        initItemTouchHelper();
        adapterStudents = new StudentsViewAdapterRV(items);
        recyclerViewStudents.setAdapter(adapterStudents);

    }
}