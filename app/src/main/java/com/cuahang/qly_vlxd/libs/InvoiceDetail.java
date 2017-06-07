package com.cuahang.qly_vlxd.libs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cuahang.qly_vlxd.R;
import com.cuahang.qly_vlxd.sqlite.SQLite_DB;

import java.util.ArrayList;

/**
 * Created by ntdan on 5/17/17.
 */

public class InvoiceDetail {
    public ArrayList<InvoiceDetail> list;
    int invoiceID;
    int productID;
    double quantity;
    double price;
    SQLite_DB db;
    Context c;

    public InvoiceDetail(Context context) {
        db = new SQLite_DB(context);
        c = context;
    }

    public InvoiceDetail(int invoiceID, int productID, double quantity, double price) {
        this.invoiceID = invoiceID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public InvoiceDetail() {
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean add() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderid", invoiceID);
        contentValues.put("productid", productID);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);

        return db.add("orderdetail", contentValues) > 0;
    }

    public boolean update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("invoiceID", invoiceID);
        contentValues.put("productID", productID);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);

        return db.update("orderdetail", contentValues, " invoiceid=? and productid=? and price=?",
                new String[]{invoiceID + "", productID + "", price + ""}) > 0;
    }

    public boolean delete() {
        return db.delete("orderdetail", " orderid=? and productid=? and price=?",
                new String[]{invoiceID + "", productID + "", price + ""}) > 0;
    }

    public void find() {
        String sql = "SELECT orderid, productid, orderdetail.price, orderdetail.quantity " +
                "FROM orderdetail, product " +
                "where orderdetail.productid = product.id and orderid=" + invoiceID;
        if (invoiceID == 0) {
            sql = "SELECT orderid, productid, orderdetail.price, orderdetail.quantity " +
                    "FROM orderdetail, product " +
                    "where orderdetail.productid = product.id";
        }
        Cursor cursor = db.find(sql + " order by orderid desc");

        if (cursor != null && cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                InvoiceDetail invoiceDetail = new InvoiceDetail();
                invoiceDetail.setProductID(cursor.getInt(cursor.getColumnIndex("productid")));
                invoiceDetail.setInvoiceID(cursor.getInt(cursor.getColumnIndex("orderid")));
                invoiceDetail.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                invoiceDetail.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));

                list.add(invoiceDetail);

            } while (cursor.moveToNext());
        } else {
            list = null;
        }
    }

    public class DBAdapter extends BaseAdapter {
        ArrayList<InvoiceDetail> list;
        Context context;

        public DBAdapter(ArrayList<InvoiceDetail> list, Context context) {
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
            Item item;
            if (convertView == null) {
                item = new Item();

                convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.invoice_detail_item, parent, false);
                item.productid = (TextView) convertView.findViewById(R.id.tvID);
                item.productName = (TextView) convertView.findViewById(R.id.tvProductName);
                item.price = (TextView) convertView.findViewById(R.id.tvPrice);
                item.quantity = (TextView) convertView.findViewById(R.id.tvQuantity);
                item.btnDelete = (ImageButton) convertView.findViewById(R.id.btnCall);
                convertView.setTag(item);

                convertView.setLongClickable(true);
            } else {
                item = (Item) convertView.getTag();
            }

            item.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InvoiceDetail invoiceDetail = new InvoiceDetail(context);
                    invoiceDetail.setProductID(list.get(position).getProductID());
                    invoiceDetail.setInvoiceID(list.get(position).getInvoiceID());
                    invoiceDetail.setPrice(list.get(position).getPrice());
                    invoiceDetail.delete();

                    notifyDataSetChanged();
                    ((Activity) context).finish();
                }
            });

            item.productid.setText(list.get(position).getProductID() + "");
            item.productName.setText(list.get(position).getProductID() + "");
            item.price.setText(list.get(position).getPrice() + "");
            item.quantity.setText(list.get(position).getQuantity() + "");

            return convertView;
        }

        class Item {
            TextView productid, productName, price, quantity;
            ImageButton btnDelete;
        }
    }
}
