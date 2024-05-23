package com.example.electronicjournal.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.electronicjournal.database.StudentsDatabase;
import com.example.electronicjournal.database.dao.StudentDao;
import com.example.electronicjournal.database.entity.Student;

import java.util.List;

public class StudentsDbRepository implements StudentDao {

    private StudentDao studentDao;
    private StudentsDatabase studentsDatabase;

    public StudentsDbRepository(Context context) {
        studentsDatabase = Room.databaseBuilder(context, StudentsDatabase.class, "Students")
                .allowMainThreadQueries()
                .build();

        studentDao = studentsDatabase.getStudentDao();

    }

    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentDao.deleteStudent(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public LiveData<List<Student>> getAllStudentsLD() {
        return studentDao.getAllStudentsLD();
    }

    @Override
    public List<Student> getAllStudentsOnDayOfWeek(String dayOfWeek) {
        return studentDao.getAllStudentsOnDayOfWeek(dayOfWeek);
    }

    @Override
    public List<Student> getAllStudentsWithSubstrInName(String s) {
        return studentDao.getAllStudentsWithSubstrInName(s);
    }

    @Override
    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }
}
