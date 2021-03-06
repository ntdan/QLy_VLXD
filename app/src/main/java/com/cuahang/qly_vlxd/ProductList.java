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

import com.cuahang.qly_vlxd.libs.Product;

public class ProductList extends AppCompatActivity {

    Product product;
    ListView listViewPro;
    Product.DBAdapter adapter;
    private int proCode = 1000;
    private boolean chonMua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        if (product == null) {

            if (getIntent() != null) {
                chonMua = getIntent().getBooleanExtra("mua", false);
            }

            listViewPro = (ListView) findViewById(R.id.lvProduct);
            product = new Product(ProductList.this);
            product.setId(0);
            product.find();
            if (product.list != null && product.list.size() > 0) {
                adapter = product.new DBAdapter(product.list, ProductList.this);
                listViewPro.setAdapter(adapter);
            }

            registerForContextMenu(listViewPro);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == proCode) {
            product.find();
            if (product.list != null && product.list.size() > 0) {
                adapter = product.new DBAdapter(product.list, ProductList.this);
                listViewPro.setAdapter(adapter);
            }

            registerForContextMenu(listViewPro);
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

        if (!chonMua)
            menu.findItem(R.id.mnMua).setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnXoa) {
            AdapterView.AdapterContextMenuInfo inf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String strID = ((TextView) inf.targetView.findViewById(R.id.tvProID)).getText().toString();
            product.setId(Integer.parseInt(strID));
            if (product.delete()) {
                if (product.list.size() > 0) {
                    for (int i = 0; i < product.list.size(); i++) {
                        if (product.list.get(i).getId() == product.getId()) {
                            product.list.remove(i);
                            break;
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                product.setId(0);
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mnuAdd) {
            Intent pro = new Intent(ProductList.this, NewProduct.class);
            startActivityForResult(pro, proCode);
            return true;
        }

        if (item.getItemId() == R.id.mnuExit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
