package com.example.westoncampbellinventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.westoncampbellinventoryapp.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "data.db";

    private static DatabaseManager instance;

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    private static final class InventoryTable {
        private static final String TABLE = "inventory";
        private static final String COL_ID = "_invid";
        private static final String COL_NAME = "name";
        private static final String COL_DESCRIPTION = "description";
    }

    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_userid";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + InventoryTable.TABLE + "( " +
                InventoryTable.COL_ID + " integer primary key autoincrement, " +
                InventoryTable.COL_NAME + ", " +
                InventoryTable.COL_DESCRIPTION + ")");

        db.execSQL("create table " + UserTable.TABLE + "( " +
                UserTable.COL_ID + " integer primary key autoincrement, " +
                UserTable.COL_USERNAME + " text, " +
                UserTable.COL_PASSWORD + " text, " +
                "constraint username_unique unique (" + UserTable.COL_USERNAME + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + InventoryTable.TABLE);
        db.execSQL("drop table if exists " + UserTable.TABLE);
        onCreate(db);
    }

    public ArrayList<InventoryItem> getItems() {
        ArrayList<InventoryItem> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + InventoryTable.TABLE;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                InventoryItem item = new InventoryItem(id, name, description);
                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return items;
    }

    public InventoryItem getItem(long itemId) {
        InventoryItem item = null;

        SQLiteDatabase db = getReadableDatabase();
        String eqString = " = ?";
        String sql = "SELECT * FROM " + InventoryTable.TABLE + "WHERE " + InventoryTable.COL_ID + eqString;
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(itemId)});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            item = new InventoryItem(itemId, name, description);
        }

        cursor.close();
        return item;
    }

    public long addItem(InventoryItem item) {
        long newId;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_NAME, item.getName());
        values.put(InventoryTable.COL_DESCRIPTION, item.getDescription());
        newId = db.insert(InventoryTable.TABLE, null, values);

        return newId;
    }

    public boolean editItem(long id, InventoryItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_ID, id);
        values.put(InventoryTable.COL_NAME, item.getName());
        values.put(InventoryTable.COL_DESCRIPTION, item.getDescription());

        int result = db.update(InventoryTable.TABLE, values, InventoryTable.COL_ID + " = " + id, null);

        return result == 1;
    }

    public boolean deleteItem(long id) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(InventoryTable.TABLE, InventoryTable.COL_ID + " = " + id, null);

        return result == 1;
    }

    public boolean authenticate(String username, String password) {
        boolean isAuthenticated = false;
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select * from " + UserTable.TABLE +
                " WHERE " + UserTable.COL_USERNAME + " = ? AND " +
                UserTable.COL_PASSWORD + " = ? ";

        Cursor cursor = db.rawQuery(sql, new String[]{username, password});

        if (cursor.moveToFirst()) {
            isAuthenticated = true;
        }
        cursor.close();

        return isAuthenticated;
    }

    public long addUser(String username, String password) {
        long newId;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.COL_USERNAME, username);
        values.put(UserTable.COL_PASSWORD, password);
        newId = db.insert(UserTable.TABLE, null, values);

        return newId;
    }
}
