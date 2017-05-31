package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class InvoiceDetail extends AppCompatActivity {

    InvoiceDetail invoiceDetail;
    ListView listViewInvoiceDetail;
    com.cuahang.qly_vlxd.invoice.InvoiceDetail.DBAdapter adapter;
    private int cusCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        if (invoiceDetail == null) {
            /*listViewInvoiceDetail = (ListView) findViewById(R.id.lvCustomer);
            invoiceDetail = new InvoiceDetail(InvoiceDetail.this);
            invoiceDetail.setId(0);
            invoiceDetail.find();
            if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
                adapter = invoiceDetail.new DBAdapter(invoiceDetail.list, CustomerList.this);
                listViewInvoiceDetail.setAdapter(adapter);
            }*/

            registerForContextMenu(listViewInvoiceDetail);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == cusCode) {
            /*invoiceDetail.find();
            if (invoiceDetail.list != null && invoiceDetail.list.size() > 0) {
                adapter = invoiceDetail.new DBAdapter(invoiceDetail.list, CustomerList.this);
                listViewInvoiceDetail.setAdapter(adapter);
            }
            registerForContextMenu(listViewInvoiceDetail);*/
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
           /* customer.setId(Integer.parseInt(strID));
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
            }*/
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuAdd) {
            /*Intent cus = new Intent(CustomerList.this, NewCustomer.class);
            startActivityForResult(cus, cusCode);*/
            return true;
        }

        if (item.getItemId() == R.id.mnuExit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
