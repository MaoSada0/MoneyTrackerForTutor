package com.example.electronicjournal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.electronicjournal.database.dao.StudentDao;
import com.example.electronicjournal.database.entity.Student;


@Database(
        entities = {Student.class},
        version = 1
)
@TypeConverters(Converters.class)
abstract public class StudentsDatabase extends RoomDatabase {
    StudentDao studentDao;

    public abstract StudentDao getStudentDao();

}
