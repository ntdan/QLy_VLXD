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
import android.widget.Toast;

import com.cuahang.qly_vlxd.libs.Customer;
import com.cuahang.qly_vlxd.libs.Invoice;

import java.text.NumberFormat;
import java.util.Calendar;

public class CreateInvoice extends AppCompatActivity {

    Invoice invoice;
    AutoCompleteTextView etName;
    EditText etShipAddress, etMobile;
    TextView tvTotal;
    int invoiceID, customerID;
    Customer db;
    private int proList = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        db = new Customer(CreateInvoice.this);

        invoice = new Invoice(CreateInvoice.this);
        if (getIntent() != null)
            invoice.setId(getIntent().getIntExtra("id", 0));


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


        if (invoice.getId() != 0) {
            invoice.find();
            etName.setText(invoice.list.get(0).getCustomerNane());
            etMobile.setText(invoice.list.get(0).getCustomerMobile());
            etShipAddress.setText(invoice.list.get(0).getShipAddress());
            tvTotal.setText(NumberFormat.getIntegerInstance().format(invoice.list.get(0).getTotal()));
            findViewById(R.id.btnCreateInvoice).setVisibility(View.GONE);
        }
    }


    String[] getCustomer() {
        db.find();
        String[] str = new String[db.list.size()];
        for (int i = 0; i < db.list.size(); i++) {
            str[i] = db.list.get(i).getFullname() + "-" + db.list.get(i).getMobile() + "-" + db.list.get(i).getId();
        }

        return str;
    }


    public void create(View view) {
        invoice.setId(0);
        invoice.setBuyDate(Calendar.getInstance().getTime().toString());
        invoice.setPayComplete(0);
        invoice.setShipAddress(etShipAddress.getText().toString());
        invoice.setCustomerID(customerID);
        boolean ok = invoice.add();
        Intent cusList = new Intent();
        setResult(ok ? RESULT_OK : RESULT_CANCELED, cusList);

        if (invoice.getId() != 0) {
            Intent pro = new Intent(getApplicationContext(), Activity_InvoiceDetail.class);
            pro.putExtra("orderid", invoice.getId());
            startActivityForResult(pro, proList);
        } else {
            Toast.makeText(this, "Nhập thông tin hóa đơn trước", Toast.LENGTH_SHORT).show();
        }
    }

    public void addProduct(View view) {
        if (invoice.getId() != 0) {
            Intent pro = new Intent(getApplicationContext(), Activity_InvoiceDetail.class);
            pro.putExtra("orderid", invoice.getId());
            startActivityForResult(pro, proList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == proList) {
            if (invoice.getId() != 0) {
                invoice.find();
                etName.setText(invoice.list.get(0).getCustomerNane());
                etMobile.setText(invoice.list.get(0).getCustomerMobile());
                etShipAddress.setText(invoice.list.get(0).getShipAddress());
                tvTotal.setText(invoice.list.get(0).getTotal() + "");
            }
        }
    }

    public void exit(View view) {
        finish();
    }
}
