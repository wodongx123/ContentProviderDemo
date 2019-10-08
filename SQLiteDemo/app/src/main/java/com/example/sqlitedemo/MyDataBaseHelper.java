package com.example.sqlitedemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    //将建表的SQL语句写成String的常量
    public static final String createBookTable = "\tcreate table book(\n" +
            "id integer primary key autoincrement,\n" +
            "name text,\n" +
            "author text,\n" +
            "price integer\n" +
            ")";


    public static final String createPeopleTable = "create table People(\n" +
            "id integer primary key autoincrement,\n" +
            "name text,\n" +
            "age integer\n" +
            ")";


    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行SQL语句
        sqLiteDatabase.execSQL(createBookTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(createPeopleTable);
    }

}
