package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cuahang.qly_vlxd.libs.Invoice;

public class Customer_UnCompletedPayment extends AppCompatActivity {

    Invoice invoice;
    ListView listViewInv;
    boolean chonMua = false;
    Invoice.DBAdapter adapter;
    private int invCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__un_completed_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewInv = (ListView) findViewById(R.id.lvInvoice);
        invoice = new Invoice(Customer_UnCompletedPayment.this);
        invoice.setId(0);
        invoice.find();
        if (invoice.list != null && invoice.list.size() > 0) {
            adapter = invoice.new DBAdapter(invoice.list, Customer_UnCompletedPayment.this);
            listViewInv.setAdapter(adapter);
        }

        registerForContextMenu(listViewInv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.index_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_list, menu);

        menu.findItem(R.id.mnMua).setVisible(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == invCode) {
            invoice.find();
            if (invoice.list != null && invoice.list.size() > 0) {
                adapter = invoice.new DBAdapter(invoice.list, Customer_UnCompletedPayment.this);
                listViewInv.setAdapter(adapter);
            }
            registerForContextMenu(listViewInv);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuAdd) {
            Intent cus = new Intent(Customer_UnCompletedPayment.this, CreateInvoice.class);
            startActivityForResult(cus, invCode);
            return true;
        }

        if (item.getItemId() == R.id.mnuExit) {
            finish();
            return true;
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnXem) {
            Intent invoice = new Intent(Customer_UnCompletedPayment.this, CreateInvoice.class);

            int id = 0;
            AdapterView.AdapterContextMenuInfo adp = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            TextView tv = (TextView) adp.targetView.findViewById(R.id.tvID);
            id = Integer.parseInt(tv.getText().toString());
            invoice.putExtra("id", id);
            startActivityForResult(invoice, invCode);
            return true;
        }
        return true;
    }
}
