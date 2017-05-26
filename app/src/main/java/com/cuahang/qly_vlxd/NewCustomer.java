package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cuahang.qly_vlxd.libs.Customer;

public class NewCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
    }

    public void click(View view) {
        if (view.getId() == R.id.btnSave) {
            Customer customer = new Customer(NewCustomer.this);
            customer.setFullname(((EditText) findViewById(R.id.etCustomerFullname)).getText().toString());
            customer.setAddress(((EditText) findViewById(R.id.etCustomerAddress)).getText().toString());
            customer.setMobile(((EditText) findViewById(R.id.etCustomerMobile)).getText().toString());
            customer.setNote(((EditText) findViewById(R.id.etCustomerNotes)).getText().toString());

            boolean ok = customer.add();
            Intent cusList = new Intent();
            setResult(ok ? RESULT_OK : RESULT_CANCELED, cusList);
            finish();
            return;
        }

        if (view.getId() == R.id.btnList) {
            Intent proList = new Intent();
            setResult(RESULT_CANCELED, proList);
            finish();
            return;
        }
    }
}
