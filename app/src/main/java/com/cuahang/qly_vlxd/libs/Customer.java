package com.cuahang.qly_vlxd.libs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuahang.qly_vlxd.sqlite.SQLite_DB;

import java.util.ArrayList;

/**
 * Created by ntdan on 5/17/17.
 */

public class Customer {
    int id;
    String fullname;
    String mobile;
    String address;
    String image;
    String note;

    ArrayList<Product> list;

    Context context;
    SQLite_DB db;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Customer(Context context) {
        this.context = context;
    }

    public Customer(int id, String fullname, String mobile, String address, String image, String note) {
        this.id = id;
        this.fullname = fullname;
        this.mobile = mobile;
        this.address = address;
        this.image = image;
        this.note = note;
    }

    public boolean add()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname",fullname);
        contentValues.put("mobile",mobile);
        contentValues.put("address",address);
        contentValues.put("note",note);
        contentValues.put("image",image);

        return db.add("customer", contentValues) > 0 ? true : false;
    }

    public boolean update()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname",fullname);
        contentValues.put("mobile",mobile);
        contentValues.put("address",address);
        contentValues.put("note",note);
        contentValues.put("image",image);

        return db.update("customer", contentValues, "where id="+id, null) > 0 ? true : false;
    }

    public boolean delete()
    {
        return db.delete("customer", "where id="+id, null) > 0 ? true : false;
    }

    public void find()
    {
        String sql = "select * from customer where id="+ id;
        if(id == 0)
        {
            sql = "select * from customer";
        }
        Cursor cursor = db.find(sql);

        if (cursor.moveToFirst())
        {
            list = new ArrayList<>();
        }else
        {
            list = null;
        }
    }

    class Apdater extends BaseAdapter
    {
        int layout;

        public Apdater(int layout) {
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        class item {
            TextView name, price, unit, code;
        }
    }
}
