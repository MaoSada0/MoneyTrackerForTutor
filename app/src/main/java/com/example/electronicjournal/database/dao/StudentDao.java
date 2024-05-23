package com.example.electronicjournal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.electronicjournal.database.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void addStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("select * FROM Students")
    List<Student> getAllStudents();

    @Query("select * FROM Students")
    LiveData<List<Student>> getAllStudentsLD();

    @Query("SELECT * FROM Students WHERE INSTR(date_of_lesson, :dayOfWeek) > 0")
    List<Student> getAllStudentsOnDayOfWeek(String dayOfWeek);

    @Query("SELECT * FROM Students WHERE INSTR(student_name, :s) > 0")
    List<Student> getAllStudentsWithSubstrInName(String s);

    @Query("select * FROM Students WHERE student_id = :id")
    Student getStudentById(int id);
}
