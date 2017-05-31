package com.cuahang.qly_vlxd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.cuahang.qly_vlxd.invoice.Invoice;

import java.util.Calendar;

public class CreateInvoice extends AppCompatActivity {

    Invoice invoice;
    AutoCompleteTextView etName;
    EditText etShipAddress, etMobile;
    TextView tvTotal;
    int invoiceID, customerID;
    Invoice db;
    private int invCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        if (db == null) {
            db = new Invoice(CreateInvoice.this);

            etShipAddress = (EditText) findViewById(R.id.etShipAddress);
            etMobile = (EditText) findViewById(R.id.etMobie);
            tvTotal = (TextView) findViewById(R.id.tvTotal);

            etName = (AutoCompleteTextView) findViewById(R.id.etCustomer);
            etName.setAdapter(new ArrayAdapter<String>(
                    CreateInvoice.this, android.R.layout.simple_list_item_1, getCustomer()));
            etName.setThreshold(1);

            etName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    etName.setText(((TextView) view).getText().toString().split("-")[0]);
                    etMobile.setText(((TextView) view).getText().toString().split("-")[1]);
                    customerID = Integer.parseInt(((TextView) view).getText().toString().split("-")[2]);
                    etShipAddress.requestFocus();
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.showSoftInput(etShipAddress, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        }

    }


    String[] getCustomer() {
        db.find();
        String[] str = new String[db.list.size()];
        for (int i = 0; i < db.list.size(); i++) {
            str[i] = db.list.get(i).getCustomerNane() + "-" + db.list.get(i).getCustomerMobile() + "-" + db.list.get(i).getId();
        }

        return str;
    }

    public void create(View view) {
        invoice = new Invoice(CreateInvoice.this);
        invoice.setId(0);
        invoice.setBuyDate(Calendar.getInstance().getTime().toString());
        invoice.setPayComplete(0);
        invoice.setShipAddress(etShipAddress.getText().toString());
        invoice.setCustomerID(customerID);
        boolean ok = invoice.add();
        Intent cusList = new Intent();
        setResult(ok ? RESULT_OK : RESULT_CANCELED, cusList);
        if (ok) finish();
    }

    public void addProduct(View view) {

    }
}
