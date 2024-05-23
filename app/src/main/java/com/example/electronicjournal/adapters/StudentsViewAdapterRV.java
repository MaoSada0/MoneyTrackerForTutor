package com.example.electronicjournal.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Student;
import com.example.electronicjournal.fragments.student_fragments.ChangeStudentFragment;

import java.util.List;

public class StudentsViewAdapterRV extends RecyclerView.Adapter<StudentsViewAdapterRV.viewholder> {
    List<Student> items;

    public StudentsViewAdapterRV(List<Student> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_students, parent, false);

        return new StudentsViewAdapterRV.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        Student studentTemp = items.get(position);

        holder.wrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ChangeStudentFragment changeStudentFragment = ChangeStudentFragment.newInstance(studentTemp.getId());
                changeStudentFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), changeStudentFragment.getTag());
                return true;
            }
        });

        holder.nameOfStudentTV.setText(studentTemp.getName());
        holder.priceOfLessonsTV.setText(studentTemp.getPriceOfLesson().toString());

        List<String> daysTemp = studentTemp.getDatesOfLesson();
        String ans = "";
        for (int i = 0; i < daysTemp.size(); i++) {
            ans = ans + daysTemp.get(i);
            if(i != daysTemp.size() - 1) ans += ", ";
        }

        holder.datesOfLessonsTV.setText(ans);


        holder.studentPhotoIV.setImageBitmap(studentTemp.getPhoto());

    }

    public Student removeItem(int position) {
        Student studentToDelete = items.get(position);
        App.getInstance().getStudentsDbRepository().deleteStudent(studentToDelete);
        items.remove(studentToDelete);
        notifyItemRemoved(position);
        return studentToDelete;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView studentPhotoIV;

        TextView nameOfStudentTV;
        TextView priceOfLessonsTV;
        TextView datesOfLessonsTV;

        ConstraintLayout wrapper;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            wrapper = itemView.findViewById(R.id.wrapper_students);

            studentPhotoIV = itemView.findViewById(R.id.student_photo_IV);

            nameOfStudentTV = itemView.findViewById(R.id.name_of_student_TV);
            priceOfLessonsTV = itemView.findViewById(R.id.price_of_lesson_TV);
            datesOfLessonsTV = itemView.findViewById(R.id.dates_of_classes_TV);

        }
    }
}
