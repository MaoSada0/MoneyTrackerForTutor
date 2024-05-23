package com.example.electronicjournal.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> fromString(String value) {
        String[] daysArray = value.split(",");
        return Arrays.asList(daysArray);
    }

    @TypeConverter
    public static String fromList(List<String> daysOfWeek) {
        StringBuilder value = new StringBuilder();

        for (int i = 0; i < daysOfWeek.size(); i++) {
            value.append(daysOfWeek.get(i));

            if(i != daysOfWeek.size() - 1) value.append(",");

        }

        return value.toString();
    }

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap fromByteArray(byte[] arr){
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

}
