package com.example.electronicjournal.database.entity;
import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Students")
public class Student implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    private Integer id;

    @ColumnInfo(name = "student_name")
    private String name;

    @ColumnInfo(name = "price_of_lesson")
    private Integer priceOfLesson;

    @ColumnInfo(name = "date_of_lesson")
    private List<String> datesOfLesson;

    @ColumnInfo(name = "avatar")
    private Bitmap photo;

    public Student(String name, Integer priceOfLesson, List<String> datesOfLesson, Bitmap photo) {
        this.name = name;
        this.priceOfLesson = priceOfLesson;
        this.datesOfLesson = datesOfLesson;
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public List<String> getDatesOfLesson() {
        return datesOfLesson;
    }

    public void setDatesOfLesson(List<String> datesOfLesson) {
        this.datesOfLesson = datesOfLesson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceOfLesson() {
        return priceOfLesson;
    }

    public void setPriceOfLesson(Integer priceOfLesson) {
        this.priceOfLesson = priceOfLesson;
    }

}


