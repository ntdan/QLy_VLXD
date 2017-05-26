package com.cuahang.qly_vlxd.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ntdan on 5/20/17.
 */

public class SQLite_DB extends SQLiteOpenHelper {
    public SQLite_DB(Context context) {
        super(context, "vlxd.sqlite", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS orderdetail");
            db.execSQL("DROP TABLE IF EXISTS orders");
            db.execSQL("DROP TABLE IF EXISTS product");
            db.execSQL("DROP TABLE IF EXISTS customer");

            db.execSQL("CREATE TABLE customer (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, fullname VARCHAR,mobile VARCHAR,address VARCHAR,note VARCHAR,code VARCHAR)");
            db.execSQL("INSERT INTO customer(fullname, mobile, address, note, code) VALUES('Nguyễn Thanh Long','0919410361','Cần Thơ','Mua nhiều','KH001')");
            db.execSQL("CREATE TABLE product (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, code VARCHAR, name VARCHAR, unit VARCHAR, price FLOAT, image VARCHAR, quantity INTEGER)");
            db.execSQL("INSERT INTO product (code, name, unit, price, image, quantity) VALUES('C001','Cát xây','m3',10000,'',10000)");
            db.execSQL("CREATE TABLE orders (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, customerid INTEGER, paydate DATETIME, completed BOOL,ship_address VARCHAR)");
            db.execSQL("INSERT INTO orders (customerid, customer, paydate, completed, shipaddess) VALUES(1name,'Nguyen Van An','2017/01/01',0,'01 Chau Van Liem')");
            db.execSQL("CREATE TABLE orderdetail (orderid INTEGER NOT NULL, productid INTEGER NOT NULL , price DOUBLE NOT NULL , quantity FLOAT, PRIMARY KEY (orderid, productid, price))");
            db.execSQL("INSERT INTO orderdetail VALUES(1,1,20000,12)");
        } catch (Exception e) {
            Log.d("Loi: ", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long add(String table, ContentValues newContent){
        return getWritableDatabase().insert(table, null, newContent);
    }

    public long update(String table, ContentValues newContent,String where,  String[] valuesArg){
        return getWritableDatabase().update(table, newContent, where, valuesArg);
    }

    public int delete(String table, String where,  String[] valuesArg){
        return getWritableDatabase().delete(table, where, valuesArg);
    }

    public Cursor find(String sql)
    {
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            return cursor;
        }
        else
            return null;
    }
}
