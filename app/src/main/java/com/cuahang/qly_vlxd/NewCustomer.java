package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cuahang.qly_vlxd.libs.Customer;

public class NewCustomer extends AppCompatActivity {
    int id;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        customer = new Customer(NewCustomer.this);

        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("id", 0) != 0) {
            id = intent.getIntExtra("id", 0);
            customer.setId(id);
            customer.find();
            ((EditText) findViewById(R.id.etCustomerFullname)).setText(customer.list.get(0).getFullname());
            ((EditText) findViewById(R.id.etCustomerAddress)).setText(customer.list.get(0).getAddress());
            ((EditText) findViewById(R.id.etCustomerMobile)).setText(customer.list.get(0).getMobile());
            ((EditText) findViewById(R.id.etCustomerNotes)).setText(customer.list.get(0).getNote());
        }
    }

    public void click(View view) {
        if (view.getId() == R.id.btnSave) {
            customer.setFullname(((EditText) findViewById(R.id.etCustomerFullname)).getText().toString());
            customer.setAddress(((EditText) findViewById(R.id.etCustomerAddress)).getText().toString());
            customer.setMobile(((EditText) findViewById(R.id.etCustomerMobile)).getText().toString());
            customer.setNote(((EditText) findViewById(R.id.etCustomerNotes)).getText().toString());

            boolean ok = false;
            try {
                if (id == 0) {
                    ok = customer.add();
                } else {
                    ok = customer.update();
                }

                Intent cusList = new Intent();
                setResult(ok ? RESULT_OK : RESULT_CANCELED, cusList);
                finish();
            } catch (Exception ex) {
                Log.d("Loi: ", ex.toString());
            }
        }

        if (view.getId() == R.id.btnList) {
            Intent proList = new Intent();
            setResult(RESULT_CANCELED, proList);
            finish();
        }
    }
}
