package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private MyDataBaseHelper myDataBaseHelper;
    SQLiteDatabase writableDatabase;
    SQLiteDatabase readableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBaseHelper = new MyDataBaseHelper(this, "BookStore.db", null, 2);
        //获取一个可写的数据库，当管理类发现没有这个数据库的时候，就会新建一个，顺便就执行了oncreate的建表
        writableDatabase = myDataBaseHelper.getWritableDatabase();

        insert();
//        update();
//        delete();
        query();
    }


    public void insert(){
        //获取一个可写的数据库，这样才能写入数据
        writableDatabase = myDataBaseHelper.getWritableDatabase();

        //对数据库进行增改需要使用ContentValues，它是一个存储类，可以存储大部分基本类型的数据
        ContentValues values = new ContentValues();
        values.put("name", "hello world");
        values.put("author", "aaa");
        values.put("price", 100);

        //添加数据，
        writableDatabase.insert("Book", null, values);

        //清空内容，然后写入第二段数据
        values.clear();
        values.put("name", "hello android");
        values.put("price", 200);

        //再次添加数据，aaaaaaaaa
        writableDatabase.insert("Book", null, values);
    }


    public void update(){
        //获取一个可写的数据库，这样才能更新数据
        writableDatabase = myDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("author", "bbb");
        values.put("price", 150);
        writableDatabase.update("Book", values, "name = ?", new String[]{"hello android"});
    }


    public void delete(){
        writableDatabase = myDataBaseHelper.getWritableDatabase();
        writableDatabase.delete("Book", "author = ?", new String[]{"bbb"});
    }

    public void query(){
        //由于不用对数据做改动，所以只要获取只读的数据库即可
        readableDatabase = myDataBaseHelper.getReadableDatabase();
        //查询语句，要注意返回的是一个Cursor对象
        Cursor cursor = readableDatabase.query("Book", null, null, null, null, null, null);

        //如果查询的数据为空就返回
        if (!cursor.moveToFirst())
            return;

        //当游标没到末尾时就一直遍历内部的数据
        do{
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));

            //为了方便，获得的数据就用log打出
            Log.i("MainActivity", "query: " + name + "  " + price);
        }while(cursor.moveToNext());

        //用完之后要记得关掉
        cursor.close();
    }


}
