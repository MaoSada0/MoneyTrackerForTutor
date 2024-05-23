package com.example.electronicjournal.fragments.student_fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Student;
import com.example.electronicjournal.database.repository.StudentsDbRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AddStudentFragment extends BottomSheetDialogFragment {

    private EditText priceED;
    private EditText nameED;

    private View view;

    private Button confirmBtn;
    private Button getDaysBtn;
    private Button getPhotoBtn;

    private ImageView photoIV;

    private Bitmap photo;

    private TextView daysTv;

    private boolean[] selectedDays = new boolean[7];
    private String[] daysOfWeek = App.getInstance().getDaysOfWeek();

    private AlertDialog.Builder alBuilder;

    private static final int REQUEST_IMAGE_PICK = 1;

    private Animation scaleUp, scaleDown;

    private boolean isPhoto = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_student, container, false);

        priceED = view.findViewById(R.id.price_ED);
        nameED = view.findViewById(R.id.name_ED);

        confirmBtn = view.findViewById(R.id.confirm_BTN);
        getDaysBtn = view.findViewById(R.id.get_dates_BTN);
        getPhotoBtn = view.findViewById(R.id.get_photo_BTN);

        daysTv = view.findViewById(R.id.dates_TV);

        photoIV = view.findViewById(R.id.photo_IV);

        StudentsDbRepository dbStudentsRepository = App.getInstance().getStudentsDbRepository();

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canAdd()){
                    List<String> daysAl = getDaysFromSelectedDays();

                    Student studentAdd = new Student(
                            nameED.getText().toString(),
                            Integer.parseInt(priceED.getText().toString()),
                            daysAl,
                            photo
                    );

                    dbStudentsRepository.addStudent(studentAdd);

                    Arrays.fill(selectedDays, false);
                    isPhoto = false;
                    clearAll();
                    dismiss();
                }
            }

        });

        getDaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysOfWeekDialog();
            }
        });

        confirmBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
            }
        });

        getPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                photo = MediaStore.Images.Media.getBitmap(view.getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            isPhoto = true;
            photoIV.setImageURI(imageUri);
        }
    }

    private void showDaysOfWeekDialog() {
        alBuilder = new AlertDialog.Builder(view.getContext());
        alBuilder.setTitle("Выберите дни недели");
        alBuilder.setMultiChoiceItems(daysOfWeek, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedDays[which] = isChecked;
            }
        });

        alBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String ans = IntStream.range(0, daysOfWeek.length)
                        .filter(i -> selectedDays[i])
                        .mapToObj(i -> daysOfWeek[i])
                        .collect(Collectors.joining(", "));


                if(!ans.isEmpty()) daysTv.setText(ans);
            }
        });


        alBuilder.setNegativeButton("Отмена", null);

        alBuilder.show();
    }

    private void clearAll() {
        priceED.setText("");
        nameED.setText("");
    }

    private boolean canAdd(){
        return getFlag() && isPhoto && !(priceED.getText().toString().isEmpty() || nameED.getText().toString().isEmpty() || daysTv.getText().toString().isEmpty());
    }

    private boolean getFlag(){
        for (boolean selectedDay : selectedDays) {
            if (selectedDay) {
                return true;
            }
        }
        return false;
    }

    private List<String> getDaysFromSelectedDays(){
        List<String> daysAl = new ArrayList<>();
        for (int i = 0; i < selectedDays.length; i++) {
            if(selectedDays[i]) daysAl.add(daysOfWeek[i]);
        }

        return daysAl;
    }
}