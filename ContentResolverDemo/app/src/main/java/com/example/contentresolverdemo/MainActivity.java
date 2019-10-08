package com.example.contentresolverdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取contentResolver的实例
        contentResolver = this.getContentResolver();

        Uri uri = Uri.parse("content://com.example.sqlitedemo.provider/book");

        Cursor cursor = contentResolver.query(
                uri,
                null,
                null,
                null,
                null);

        if (cursor != null){ //确认是否查询成功

            while(cursor.moveToNext()){ //遍历整个查询结果

                String column1 = cursor.getString(cursor.getColumnIndex("name"));  //获取查询结果内的数据
                int column2 = cursor.getInt(cursor.getColumnIndex("price"));
                Log.i("MainActivity", "onCreate: " + column1 + "   " + column2);
            }
        }

 /*       ContentValues values = new ContentValues();
        values.put("column1", "String");
        values.put("column2", 1);
        contentResolver.insert(uri, values);

        ContentValues values1 = new ContentValues();
        values1.put("column1", "text");
        values1.put("column2", 2);
        contentResolver.update(uri, values, "column1 = ? and column2 = ?", new String[]{"String", "1"});

        contentResolver.update(
                uri,
                contentValues,
                where,
                selectionArgs
        );

        contentResolver.delete(uri, "column1 = ? and column2 = ?", new String[]{"text", "2"});

        //下面是delete方法具体参数
        contentResolver.delete(
                uri,
                where,
                selectionArgs
        );*/
    }
}
