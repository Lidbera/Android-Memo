package com.lectinua.memo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("prac", "?");
        sqLiteDatabase.execSQL("CREATE TABLE memo (\n" +
                "                ind INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                title TEXT,\n" +
                "                content TEXT,\n" +
                "                time TEXT,\n" +
                "                date TEXT,\n" +
                "                place TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String title, String content, String time, String date, String place){
        SQLiteDatabase db = getWritableDatabase();

        String query = String.format("INSERT INTO memo VALUES(null, '%s', '%s', '%s', '%s', '%s');",
                title, content, time, date, place);

        db.execSQL(query);

        db.close();
    }

    public void update(int index, String title, String content, String time, String date, String place){
        SQLiteDatabase db = getWritableDatabase();

        String query = String.format("UPDATE memo SET title='%s', content='%s', time='%s'," +
                        "date='%s', place='%s' WHERE ind='%d';",
                title, content, time, date, place, index);

        db.execSQL(query);

        db.close();
    }

    public void delete(int index){
        SQLiteDatabase db = getWritableDatabase();

        String query = String.format("DELETE FROM memo WHERE ind='%d'", index);

        db.execSQL(query);

        db.close();
    }

    public ArrayList<MemoItem> findAllSimpleMemos(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<MemoItem> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT ind, title, content FROM memo", null);

        while(cursor.moveToNext()){
            items.add(new MemoItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }

        db.close();

        return items;
    }

    public MemoItem getMemo(int index){

        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM memo WHERE ind='%s';", index), null);

        cursor.moveToNext();
        MemoItem item = new MemoItem(
                cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5));

        db.close();
        return item;
    }

    public ArrayList<MemoItem> findSimpleMemos(String search){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<MemoItem> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM memo WHERE title LIKE '%" + search +
                "%' OR content LIKE '%" + search + "%';", null);

        while(cursor.moveToNext()){
            items.add(new MemoItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }

        db.close();

        return items;
    }
}
