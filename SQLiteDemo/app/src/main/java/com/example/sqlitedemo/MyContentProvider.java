package com.example.sqlitedemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final int TABLE_BOOK = 0;  //使用静态变量来代替两个表和表内数据
    public static final int ITEM_BOOK = 1;
    public static final int TABLE_PEOPLE = 2;
    public static final int ITEM_PEOPLE = 3;
    private static UriMatcher uriMatcher; //声明一个uriMathcher

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //addURI有三个参数，authority和path已经对应的值
        uriMatcher.addURI("com.example.sqlitedemo.provider", "book", TABLE_BOOK);
        uriMatcher.addURI("com.example.sqlitedemo.provider", "book/#", ITEM_BOOK);
        uriMatcher.addURI("com.example.sqlitedemo.provider", "people", TABLE_PEOPLE);
        uriMatcher.addURI("com.example.sqlitedemo.provider", "people/#", ITEM_PEOPLE);
    }

    @Override
    public boolean onCreate() {
        myDataBaseHelper = new MyDataBaseHelper(getContext(), "BookStore.db", null, 2);
        sqLiteDatabase = myDataBaseHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        //虽然加密过了，但是参数就是Resolver的那几个参数
        switch (uriMatcher.match(uri)){
            case TABLE_BOOK:
            case ITEM_BOOK: //为了方便就把查询某一条的数据合并了
                return sqLiteDatabase.query("book", strings, s, strings1, null, null, s1);
            case TABLE_PEOPLE:
            case ITEM_PEOPLE:
                return sqLiteDatabase.query("People", strings, s, strings1, null, null, s1);
        }
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //返回的是一个uri,通过这个uri告诉对方插入数据是否成功,返回为null就是失败了
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case TABLE_BOOK:
                long bookId = sqLiteDatabase.insert("book", null, contentValues);
                uriReturn = Uri.parse("content://com.example.sqlitedemo.provider/book/" + bookId);
                break;
            case TABLE_PEOPLE:
                long peopleId = sqLiteDatabase.insert("People", null, contentValues);
                uriReturn = Uri.parse("content://com.example.sqlitedemo.provider/book/" + peopleId);
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE_BOOK:
                return "vnd.android.cursor.dir/vnd.com.example.sqlitedemo.provider.book";
            case ITEM_BOOK:
                return "vnd.android.cursor.item/vnd.com.example.sqlitedemo.provider.book";
            case TABLE_PEOPLE:
                return "vnd.android.cursor.dir/vnd.com.example.sqlitedemo.provider.people";
            case ITEM_PEOPLE:
                return "vnd.android.cursor.item/vnd.com.example.sqlitedemo.provider.people";
        }
        return null;
    }
}
