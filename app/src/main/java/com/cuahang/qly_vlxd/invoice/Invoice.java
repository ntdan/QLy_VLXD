package com.cuahang.qly_vlxd.invoice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuahang.qly_vlxd.R;
import com.cuahang.qly_vlxd.sqlite.SQLite_DB;

import java.util.ArrayList;

/**
 * Created by ntdan on 5/17/17.
 */

public class Invoice {
    public ArrayList<Invoice> list;
    int id;
    int customerID;
    String customerName;
    String shipAddress;
    String buyDate;
    int payComplete;// 1= completed
    SQLite_DB db;

    public Invoice() {
    }

    public Invoice(Context context) {
        db = new SQLite_DB(context);
    }

    public Invoice(int id, int customerID, String customerName, String shipAddress, String buyDate, int payComplete) {
        this.id = id;
        this.customerID = customerID;
        this.customerName = customerName;
        this.shipAddress = shipAddress;
        this.buyDate = buyDate;
        this.payComplete = payComplete;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPayComplete() {
        return payComplete;
    }

    public void setPayComplete(int payComplete) {
        this.payComplete = payComplete;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public int isPayComplete() {
        return payComplete;
    }

    public boolean add() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerid", customerID);
        contentValues.put("customername", customerName);
        contentValues.put("shipaddess", shipAddress);
        contentValues.put("completed", payComplete);
        contentValues.put("paydate", buyDate);

        return db.add("invoice", contentValues) > 0;
    }

    public boolean update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerid", customerID);
        contentValues.put("customername", customerName);
        contentValues.put("shipaddess", shipAddress);
        contentValues.put("completed", payComplete);
        contentValues.put("paydate", buyDate);

        return db.update("invoice", contentValues, " id=" + id, null) > 0;
    }

    public boolean delete() {
        return db.delete("invoice", "id=" + id, null) > 0;
    }

    public void find() {
        String sql = "select * from invoice where id=" + id;
        if (id == 0) {
            sql = "select * from invoice";
        }
        Cursor cursor = db.find(sql + " order by id desc");

        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                //customername, customerid, paydate, completed, shipaddess
                Invoice invoice = new Invoice();
                invoice.setId(cursor.getInt(cursor.getColumnIndex("id")));
                invoice.setCustomerID(cursor.getInt(cursor.getColumnIndex("customerid")));
                invoice.setCustomerName(cursor.getString(cursor.getColumnIndex("customername")));
                invoice.setBuyDate(cursor.getString(cursor.getColumnIndex("paydate")));
                invoice.setPayComplete(cursor.getInt(cursor.getColumnIndex("completed")));
                invoice.setShipAddress(cursor.getString(cursor.getColumnIndex("shipaddess")));

                list.add(invoice);

            } while (cursor.moveToNext());
        } else {
            list = null;
        }
    }

    public class DBAdapter extends BaseAdapter {
        ArrayList<Invoice> list;
        Context context;

        public DBAdapter(ArrayList<Invoice> list, Context context) {
            this.list = list;
            this.context = context;
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
            Item item;
            if (convertView == null) {
                item = new Item();

                convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.invoice_item, parent, false);
                item.id = (TextView) convertView.findViewById(R.id.tvCusID);
                //item.name = (TextView)convertView.findViewById(R.id.tvCusName);
                //item.address = (TextView)convertView.findViewById(R.id.tvCusAddress);
                //item.mobile = (TextView)convertView.findViewById(R.id.tvCusMobile);

                convertView.setTag(item);

                convertView.setLongClickable(true);
            } else {
                item = (Item) convertView.getTag();
            }

            item.id.setText(list.get(position).getId() + "");
            //item.name.setText(list.get(position).getFullname());
            //item.address.setText(list.get(position).getFullname());
            //item.mobile.setText(list.get(position).getMobile());

            return convertView;
        }

        class Item {
            TextView id, customername, customerid, paydate, completed, shipaddess;
        }
    }
}
