package com.cuahang.qly_vlxd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cuahang.qly_vlxd.libs.InvoiceDetail;
import com.cuahang.qly_vlxd.libs.Product;


public class Activity_InvoiceDetail extends AppCompatActivity {

    InvoiceDetail invoiceDetail;
    ListView lvProduct;
    InvoiceDetail.DBAdapter adapter;
    int invoiceID = 0;

    Product product;


    EditText etUnit, etPrice, etQuantity;
    AutoCompleteTextView etProductName;
    private int productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);


        etPrice = (EditText) findViewById(R.id.etPrice);
        etUnit = (EditText) findViewById(R.id.etUnit);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etProductName = (AutoCompleteTextView) findViewById(R.id.etProductName);

        product = new Product(Activity_InvoiceDetail.this);

        if (getIntent() != null)
            invoiceID = getIntent().getIntExtra("orderid", 0);

        lvProduct = (ListView) findViewById(R.id.lvProduct);
        invoiceDetail = new InvoiceDetail(Activity_InvoiceDetail.this);
        invoiceDetail.setInvoiceID(invoiceID);
        invoiceDetail.find();
        if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
            adapter = invoiceDetail.new DBAdapter(invoiceDetail.list, Activity_InvoiceDetail.this);
            lvProduct.setAdapter(adapter);
        }

        registerForContextMenu(lvProduct);


        etProductName.setAdapter(new ArrayAdapter<String>(Activity_InvoiceDetail.this,
                android.R.layout.simple_list_item_1, getProduct()));
        etProductName.setThreshold(1);

        etProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etProductName.setText(((TextView) view).getText().toString().split("-")[0]);
                etUnit.setText(((TextView) view).getText().toString().split("-")[2]);
                etPrice.setText(((TextView) view).getText().toString().split("-")[1]);
                productID = Integer.parseInt(((TextView) view).getText().toString().split("-")[3]);
                etQuantity.requestFocus();
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(etQuantity, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == RESULT_OK && requestCode == cusCode) {
            invoiceDetail.find();
            if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
                adapter = invoiceDetail.new DBAdapter(invoiceDetail.list, Activity_InvoiceDetail.this);
                lvProduct.setAdapter(adapter);
            }
            registerForContextMenu(lvProduct);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.index_menu, menu);
        menu.findItem(R.id.mnuAdd).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuExit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String[] getProduct() {
        product.find();
        String[] str = new String[product.list.size()];
        for (int i = 0; i < product.list.size(); i++) {
            str[i] = product.list.get(i).getName() + "-"
                    + product.list.get(i).getPrice() + "-"
                    + product.list.get(i).getUnit() + "-"
                    + product.list.get(i).getId();
        }

        return str;
    }

    public void mua(View view) {
        boolean newPro = true;
        int price = Integer.parseInt(etPrice.getText().toString());
        int quantity = Integer.parseInt(etQuantity.getText().toString());

        invoiceDetail.find();
        if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
            for (int i = 0; i < invoiceDetail.list.size(); i++) {
                if (invoiceDetail.list.get(i).getProductID() == productID) {
                    invoiceDetail.setInvoiceID(invoiceID);
                    invoiceDetail.setProductID(productID);
                    invoiceDetail.setPrice(price);
                    quantity = (int) (invoiceDetail.list.get(i).getQuantity() + quantity);
                    invoiceDetail.setQuantity(quantity);

                    invoiceDetail.update();
                    invoiceDetail.list.get(i).setQuantity(quantity);
                    newPro = false;

                    break;
                }
            }
        }
        if (newPro) {
            invoiceDetail.setInvoiceID(invoiceID);
            invoiceDetail.setProductID(productID);
            invoiceDetail.setPrice(price);
            invoiceDetail.setQuantity(quantity);

            if (invoiceDetail.add()) {
                invoiceDetail.find();
            }
        }
        if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
            adapter = invoiceDetail.new DBAdapter(invoiceDetail.list, Activity_InvoiceDetail.this);
            lvProduct.setAdapter(adapter);
        }

    }

    public void exit(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
