package com.cuahang.qly_vlxd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.cuahang.qly_vlxd.invoice.Invoice;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (invoice == null) {
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
    }

}
