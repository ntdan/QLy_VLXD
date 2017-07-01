package com.cuahang.qly_vlxd.libs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cuahang.qly_vlxd.R;
import com.cuahang.qly_vlxd.sqlite.SQLite_DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntdan on 5/17/17.
 */

public class Customer {
    public ArrayList<Customer> list;
    int id;
    String fullname;
    String mobile;
    String address;
    String code;
    String note;
    SQLite_DB db;

    public Customer() {

    }

    public Customer(Context context) {
        db = new SQLite_DB(context);
    }

    public Customer(int id, String fullname, String mobile, String address, String image, String note) {
        this.id = id;
        this.fullname = fullname;
        this.mobile = mobile;
        this.address = address;
        this.code = image;
        this.note = note;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean add()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname",fullname);
        contentValues.put("mobile",mobile);
        contentValues.put("address",address);
        contentValues.put("note",note);
        contentValues.put("code", code);

        return db.add("customer", contentValues) > 0;
    }

    public boolean update()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname",fullname);
        contentValues.put("mobile",mobile);
        contentValues.put("address",address);
        contentValues.put("note",note);
        contentValues.put("code", code);

        return db.update("customer", contentValues, " id=" + id, null) > 0;
    }

    public boolean delete()
    {
        return db.delete("customer", "id=" + id, null) > 0;
    }

    public void find()
    {
        String sql = "select id, fullname, mobile, address, note, code from customer where id=" + id;
        if(id == 0)
        {
            sql = "select id, fullname, mobile, address, note, code from customer";
        }
        Cursor cursor = db.find(sql + " order by id desc");

        if (cursor.moveToFirst())
        {
            list = new ArrayList<>();
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex("id")));
                customer.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
                customer.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                customer.setCode(cursor.getString(cursor.getColumnIndex("code")));
                customer.setNote(cursor.getString(cursor.getColumnIndex("note")));
                customer.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));

                list.add(customer);

            } while (cursor.moveToNext());
        }else
        {
            list = null;
        }
    }

    public class DBAdapter extends BaseAdapter implements Filterable
    {
        ArrayList<Customer> alllist;
        List<Customer> list;
        Context context;
        private Filter myFilter;

        public DBAdapter(ArrayList<Customer> list, Context context) {
            this.list = list;
            alllist = list;
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

                convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.customer_layout, parent, false);
                item.id = (TextView) convertView.findViewById(R.id.tvCusID);
                item.name = (TextView) convertView.findViewById(R.id.tvCusName);
                item.address = (TextView) convertView.findViewById(R.id.tvCusAddress);
                item.mobile = (TextView) convertView.findViewById(R.id.tvCusMobile);

                convertView.setTag(item);

                convertView.setLongClickable(true);
            } else {
                item = (Item) convertView.getTag();
            }

            item.id.setText(list.get(position).getId() + "");
            item.name.setText(list.get(position).getFullname());
            item.address.setText(list.get(position).getAddress());
            item.mobile.setText(list.get(position).getMobile());

            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (myFilter == null)
                myFilter = new MyFilter();
            return myFilter;
        }

        // mo ta bo loc
        class MyFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // cai dat noi dung bo loc
                if (constraint == null || constraint.equals("")) {
                    // khong loc
                    results.values = alllist;
                    results.count = alllist.size();
                } else {
                    // tim
                    List<Customer> customers = new ArrayList<>();

                    for (Customer p : alllist) {
                        if (p.getFullname().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            customers.add(p);
                    }

                    results.values = customers;
                    results.count = customers.size();

                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else {
                    list = (List<Customer>) results.values;
                    notifyDataSetChanged();
                }
            }
        }


        class Item {
            TextView name, address, mobile, id;
        }
    }
}
