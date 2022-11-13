package com.example.is_miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBHandler extends SQLiteOpenHelper {
    static final int DB_VERSION = 1;
    static final String DB_NAME = "studentDB.db";
    static final String TABLE_NAME = "student";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( ID INTEGER,"
                + "NAME TEXT,"
                + "PASSWORD TEXT,"
                + "APPLICATION TEXT,"
                + "NICKNAME TEXT,"
                + "PHONE TEXT,"
                + "EMAIL TEXT,"
                + "ADDRESS TEXT,"
                + "FEE INTEGER,"
                + "BIRTHDAY DATE, PRIMARY KEY(ID, NAME))";
        db.execSQL(SQL_CREATE_TABLE);
        addHandler(db,new Student(99999, "Admin", "admin", "0001",
                "Admin", "1000 1000", "admin@manipal.edu", "Manipal", 400000, "1990-01-01"));
        addHandler(db, new Student(1, "Shyam", "shyam", "0002",
                "shyam", "1234567890", "shyam@manipal.edu", "Manipal", 10000, "2000-01-01"));
        addHandler(db, new Student(2, "Lavnish", "lavnish", "0003",
                "lavnish", "9524073758", "lavnish@manipal.edu", "Manipal", 10000, "2000-01-01"));
        addHandler(db, new Student(3, "Mustafa", "mustafa", "0004",
                "mustafa", "4502801981", "ryan@hogwarts.edu", "Manipal", 10000, "2000-01-01"));
        addHandler(db, new Student(4, "Aditya", "aditya", "0005",
                "aditya", "1269011086", "aditya@manipal.edu", "Manipal", 10000, "2000-01-01"));
        addHandler(db, new Student(5, "Karthik", "karthik", "0006",
                "karthik", "3076478152", "karthik@manipal.edu", "Manipal", 10000, "2000-01-01"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor loadHandler() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void addHandler(SQLiteDatabase db, Student student) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        if (db != null) {
            values.put("ID", student.getId());
            values.put("NAME", student.getName());
            values.put("PASSWORD", student.getPassword());
            values.put("APPLICATION", student.getApplication());
            values.put("NICKNAME", student.getNickname());
            values.put("PHONE", student.getPhone());
            values.put("FEE", student.getFee());
            values.put("ADDRESS", student.getAddress());
            values.put("EMAIL", student.getEmail());
            values.put("BIRTHDAY", dateFormat.format(student.getBirthday()));
            db.insert(TABLE_NAME, null, values);
        }
    }

    public void addInterface(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        addHandler(db,student);
    }

    public Student findHandler(String username, String password, Boolean safe) {
        String query;
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        if(!safe) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE NAME='" + username + "' AND PASSWORD='" + password + "'";
            cursor = db.rawQuery(query, null);
        }
        else
        {
            query = "SELECT * FROM "+ TABLE_NAME + " WHERE NAME=? AND PASSWORD=?";
            cursor = db.rawQuery(query, new String[]{username,password});
        }
        Student student = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            student = new Student(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Integer.parseInt(cursor.getString(8)),
                    cursor.getString(9)
            );
            cursor.close();
        }
        db.close();
        return student;
    }

    public void partialUpdateHandler(Student student) {
        String UPDATE_SQL_COMMAND = String.format("UPDATE %s SET NICKNAME='%s', EMAIL='%s', ADDRESS='%s', PASSWORD='%s', PHONE='%s' WHERE ID=%s",
                TABLE_NAME,
                student.getNickname(),
                student.getEmail(),
                student.getAddress(),
                student.getPassword(),
                student.getPhone(),
                student.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(UPDATE_SQL_COMMAND);
    }

    public boolean fullUpdateHandler(Student student)
    {
//        invoked by admin, update all fields except ID
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", student.getName());
        values.put("PASSWORD", student.getPassword());
        values.put("APPLICATION", student.getApplication());
        values.put("NICKNAME", student.getNickname());
        values.put("PHONE", student.getPhone());
        values.put("FEE", student.getFee());
        values.put("ADDRESS", student.getAddress());
        values.put("EMAIL", student.getEmail());
        values.put("BIRTHDAY", dateFormat.format(student.getBirthday()));
        return -1!=db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(student.getId())});
    }

    public boolean deleteHandler(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(student.getId())}) > 0;
    }

    public boolean safePartialUpdateHandler(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASSWORD", student.getPassword());
        values.put("NICKNAME", student.getNickname());
        values.put("PHONE", student.getPhone());
        values.put("ADDRESS", student.getAddress());
        values.put("EMAIL", student.getEmail());
        return -1!=db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(student.getId())});
    }

}