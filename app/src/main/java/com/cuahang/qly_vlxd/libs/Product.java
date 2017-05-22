package com.cuahang.qly_vlxd.libs;

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

public class Product {
    int id;
    String code;
    String name;
    String price;
    String quantity;
    String image;
    String unit;

   public ArrayList<Product> list;

    SQLite_DB db;

    public Product(Context context) {
        db = new SQLite_DB(context);
    }

    public Product(int id, String code, String name, String price, String quantity, String image, String unit) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean add()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("code",code);
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("quantity",quantity);
        contentValues.put("unit",unit);
        contentValues.put("image","");

       return db.add("product", contentValues) > 0 ? true : false;
    }

    public boolean update()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("code",code);
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("quantity",quantity);
        contentValues.put("unit",unit);
        contentValues.put("image","");

        return db.update("product", contentValues, "where id="+id, null) > 0 ? true : false;
    }

    public boolean delete()
    {
        return db.delete("product", "where id="+id, null) > 0 ? true : false;
    }

    public void find()
    {
        String sql = "select * from product where id="+ id;
        if(id == 0)
        {
            sql = "select * from product";
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

    public class DBAdapter extends BaseAdapter
    {
        ArrayList<Product> list;
        Context context;

        public DBAdapter(ArrayList<Product> list, Context context) {
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
            if(convertView == null)
            {
                item = new Item();

                convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.product_layout, parent, false);
                item.code = (TextView)convertView.findViewById(R.id.tvProCode);
                item.name = (TextView)convertView.findViewById(R.id.tvProName);
                item.price = (TextView)convertView.findViewById(R.id.tvProPrice);
                item.unit = (TextView)convertView.findViewById(R.id.tvProUnit);

                convertView.setTag(item);
            }else
            {
                item = (Item)convertView.getTag();
            }

            item.code.setText(list.get(position).getId()+"");
            item.name.setText(list.get(position).getName());
            item.price.setText(list.get(position).getPrice());
            item.unit.setText(list.get(position).getUnit());

            return convertView;
        }

        class Item {
            TextView name, price, unit, code;
        }
    }
}
