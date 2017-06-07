package com.cuahang.qly_vlxd.libs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cuahang.qly_vlxd.R;
import com.cuahang.qly_vlxd.sqlite.SQLite_DB;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by ntdan on 5/17/17.
 */

public class Invoice {
    public ArrayList<Invoice> list;
    int id;
    int customerID;
    String customerNane;
    String customerMobile;
    String shipAddress;
    String buyDate;
    float total;
    int currentPay;
    int payComplete;// 1= completed
    SQLite_DB db;
    Context c;

    public Invoice() {
    }

    public Invoice(Context context) {
        db = new SQLite_DB(context);
        c = context;
    }

    public int getCurrentPay() {
        return currentPay;
    }

    public void setCurrentPay(int currentPay) {
        this.currentPay = currentPay;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getCustomerNane() {
        return customerNane;
    }

    public void setCustomerNane(String customerNane) {
        this.customerNane = customerNane;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
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
        contentValues.put("shipaddress", shipAddress);
        contentValues.put("completed", payComplete);
        contentValues.put("paydate", buyDate);
        contentValues.put("currentpay", currentPay);

        id = (int) db.add("orders", contentValues);

        return id > 0;
    }

    public boolean update(Invoice invoice) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerid", invoice.customerID);
        contentValues.put("shipaddress", invoice.shipAddress);
        contentValues.put("completed", invoice.payComplete);
        contentValues.put("currentpay", invoice.currentPay);
        contentValues.put("paydate", invoice.buyDate);

        return db.update("orders", contentValues, " id=" + invoice.id, null) > 0;
    }

    public boolean delete() {
        return db.delete("invoice", "id=" + id, null) > 0;
    }

    public void find() {
        String sql = "SELECT orders.id, orders.customerid, orders.paydate, orders.completed,orders.shipaddress, fullname, mobile, sum(price*quantity) total\n" +
                ", currentpay FROM orders, customer, orderdetail \n" +
                "where completed =0 and customer.id = orders.customerid and orders.id = orderdetail.orderid and orderid=" + id + " group by orders.id";
        if (id == 0) {
            sql = "SELECT orders.id, orders.customerid, orders.paydate, orders.completed,orders.shipaddress, fullname, mobile, sum(price*quantity) total\n" +
                    ", currentpay FROM orders, customer, orderdetail \n" +
                    "where  completed =0 and customer.id = orders.customerid and orders.id = orderdetail.orderid group by orders.id";
        }
        Cursor cursor = db.find(sql + " order by orders.customerid desc");

        if (cursor != null && cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                //customername, customerid, paydate, completed, shipaddess
                Invoice invoice = new Invoice();
                invoice.setId(cursor.getInt(cursor.getColumnIndex("id")));
                invoice.setCustomerID(cursor.getInt(cursor.getColumnIndex("customerid")));
                invoice.setCustomerNane(cursor.getString(cursor.getColumnIndex("fullname")));
                invoice.setCustomerMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                invoice.setBuyDate(cursor.getString(cursor.getColumnIndex("paydate")));
                invoice.setPayComplete(cursor.getInt(cursor.getColumnIndex("completed")));
                invoice.setCurrentPay(cursor.getInt(cursor.getColumnIndex("currentpay")));
                invoice.setShipAddress(cursor.getString(cursor.getColumnIndex("shipaddress")));
                invoice.setTotal(cursor.getInt(cursor.getColumnIndex("total")));

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Item item;
            if (convertView == null) {
                item = new Item();

                convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.invoice_item, parent, false);
                item.id = (TextView) convertView.findViewById(R.id.tvID);
                item.customername = (TextView) convertView.findViewById(R.id.tvCustomerName);
                item.total = (TextView) convertView.findViewById(R.id.tvTotal);
                item.mobile = (TextView) convertView.findViewById(R.id.tvMobile);
                item.pay = (TextView) convertView.findViewById(R.id.tvPay);
                item.etPay = (EditText) convertView.findViewById(R.id.etPay);
                item.btnPay = (Button) convertView.findViewById(R.id.btnPay);
                item.btnCall = (ImageButton) convertView.findViewById(R.id.btnCall);
                convertView.setTag(item);

                convertView.setLongClickable(true);
            } else {
                item = (Item) convertView.getTag();
            }

            item.btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(position).getCustomerMobile()));
                    c.startActivity(intent);
                }
            });

            item.btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int t = Integer.parseInt(item.etPay.getText().toString()) + list.get(position).getCurrentPay();

                    if (t <= list.get(position).getTotal()) {
                        list.get(position).setCurrentPay(list.get(position).getCurrentPay()
                                + Integer.parseInt(item.etPay.getText().toString()));
                        notifyDataSetChanged();

                        Invoice invoice = new Invoice(context);
                        if (t == list.get(position).getTotal())
                            list.get(position).setPayComplete(1);
                        invoice.update(list.get(position));
                    } else {
                        Toast.makeText(context, "Số tiền nhiều hơn tiền cần trả.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            item.id.setText(list.get(position).getId() + "");
            item.customername.setText(list.get(position).getCustomerNane());
            NumberFormat nf = NumberFormat.getIntegerInstance();
            item.total.setText(nf.format(list.get(position).getTotal()));
            item.pay.setText(NumberFormat.getIntegerInstance().format(list.get(position).getCurrentPay()));
            item.mobile.setText(list.get(position).getCustomerMobile());
            item.etPay.setText("0");

            return convertView;
        }

        class Item {
            TextView id, customername, total, mobile, pay;
            EditText etPay;
            ImageButton btnCall;
            Button btnPay;
        }
    }
}
