package com.cuahang.qly_vlxd.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by ntdan on 5/20/17.
 */

public class SQLite_DB extends SQLiteOpenHelper {
    public SQLite_DB(Context context) {
        super(context, "vlxd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS \"customer\";\n" +
                "CREATE TABLE \"customer\" (\"id\" INTEGER PRIMARY KEY NOT NULL UNIQUE, \"fullname\" VARCHAR,\"mobile\" VARCHAR,\"address\" VARCHAR,\"note\" VARCHAR,\"code\" VARCHAR);\n" +
                "INSERT INTO \"customer\" VALUES(1,'Nguyễn Thanh Long','0919410361','Cần Thơ','Mua nhiều','KH001');\n" +
                "DROP TABLE IF EXISTS \"order\";\n" +
                "CREATE TABLE \"order\" (\"id\" INTEGER PRIMARY KEY  NOT NULL UNIQUE, \"customerid\" INTEGER, \"paydate\" DATETIME, \"completed\" BOOL);\n" +
                "INSERT INTO \"order\" VALUES(1,1,'2017/01/01',0);\n" +
                "DROP TABLE IF EXISTS \"orderdetail\";\n" +
                "CREATE TABLE \"orderdetail\" (\"orderid\" INTEGER NOT NULL, \"productid\" INTEGER NOT NULL , \"price\" DOUBLE NOT NULL , \"quantity\" FLOAT, PRIMARY KEY (\"orderid\", \"productid\", \"price\"));\n" +
                "INSERT INTO \"orderdetail\" VALUES(1,1,20000,12);\n" +
                "DROP TABLE IF EXISTS \"product\";\n" +
                "CREATE TABLE \"product\" (\"id\" INTEGER PRIMARY KEY  NOT NULL UNIQUE, \"code\" VARCHAR, \"name\" VARCHAR, \"unit\" VARCHAR, \"price\" FLOAT, \"image\" VARCHAR, \"quantity\" INTEGER);\n" +
                "INSERT INTO \"product\" VALUES(1,'C001','Cát xây','m3',10000,NULL,10000);";

        db.execSQL(sql);
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
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor.moveToFirst())
        {
            return cursor;
        }
        else
            return null;
    }
}
