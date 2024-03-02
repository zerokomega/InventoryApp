package com.example.westoncampbellinventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.westoncampbellinventoryapp.InventoryItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int VERSION = 3;
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
        private static final String COL_COUNT = "count";
        private static final String COL_IMAGE = "image";
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
                InventoryTable.COL_NAME + " text, " +
                InventoryTable.COL_DESCRIPTION + " text, " +
                InventoryTable.COL_COUNT + " integer, " +
                InventoryTable.COL_IMAGE + " blob)");

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
                int count = Integer.parseInt(cursor.getString(3));
                Bitmap image = getImage(cursor.getBlob(4));
                InventoryItem item = new InventoryItem(id, name, description, count, image);
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
            int count = Integer.parseInt(cursor.getString(3));
            Bitmap image = getImage(cursor.getBlob(4));

            item = new InventoryItem(itemId, name, description, count, image);
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
        values.put(InventoryTable.COL_COUNT, item.getCount());
        values.put(InventoryTable.COL_IMAGE, getImageBytes(item.getImage()));
        newId = db.insert(InventoryTable.TABLE, null, values);

        return newId;
    }

    public boolean editItem(long id, InventoryItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_ID, id);
        values.put(InventoryTable.COL_NAME, item.getName());
        values.put(InventoryTable.COL_DESCRIPTION, item.getDescription());
        values.put(InventoryTable.COL_COUNT, item.getCount());
        values.put(InventoryTable.COL_IMAGE, getImageBytes(item.getImage()));

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

    // Convert image from bitmap to byte array
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // Convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
