package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cuahang.qly_vlxd.libs.Customer;

public class CustomerList extends AppCompatActivity {

    Customer customer;
    ListView listViewCus;
    boolean chonMua = false;
    Customer.DBAdapter adapter;
    EditText etFilter;
    private int cusCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        etFilter = (EditText) findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        if (customer == null) {
            listViewCus = (ListView) findViewById(R.id.lvCustomer);
            customer = new Customer(CustomerList.this);
            customer.setId(0);
            customer.find();
            if (customer.list != null && customer.list.size() > 0) {
                adapter = customer.new DBAdapter(customer.list, CustomerList.this);
                listViewCus.setAdapter(adapter);
            }

            registerForContextMenu(listViewCus);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == cusCode) {
            customer.setId(0);
            customer.find();
            if (customer.list != null && customer.list.size() > 0) {
                adapter = customer.new DBAdapter(customer.list, CustomerList.this);
                listViewCus.setAdapter(adapter);
            }
            registerForContextMenu(listViewCus);
        }
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
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnXoa) {
            AdapterView.AdapterContextMenuInfo inf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String strID = ((TextView) inf.targetView.findViewById(R.id.tvCusID)).getText().toString();
            customer.setId(Integer.parseInt(strID));
            if (customer.delete()) {
                if (customer.list.size() > 0) {
                    for (int i = 0; i < customer.list.size(); i++) {
                        if (customer.list.get(i).getId() == customer.getId()) {
                            customer.list.remove(i);
                            break;
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                customer.setId(0);
            }
            return true;
        }
        if (item.getItemId() == R.id.mnXem) {
            AdapterView.AdapterContextMenuInfo inf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String strID = ((TextView) inf.targetView.findViewById(R.id.tvCusID)).getText().toString();
            customer.setId(Integer.parseInt(strID));
            Intent cus = new Intent(CustomerList.this, NewCustomer.class);
            cus.putExtra("id", Integer.parseInt(strID));
            startActivityForResult(cus, cusCode);
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuAdd) {
            Intent cus = new Intent(CustomerList.this, NewCustomer.class);
            startActivityForResult(cus, cusCode);
            return true;
        }

        if (item.getItemId() == R.id.mnuExit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
