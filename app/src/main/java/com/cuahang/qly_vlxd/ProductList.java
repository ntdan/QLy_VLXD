package com.cuahang.qly_vlxd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.cuahang.qly_vlxd.libs.Product;

public class ProductList extends AppCompatActivity {

    Product product;
    ListView listViewPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listViewPro = (ListView)findViewById(R.id.lvProduct);

        product = new Product(ProductList.this);
        product.setId(0); ;
        product.find();
        if(product.list != null && product.list.size() >0) {
            Product.DBAdapter apdater = product.new DBAdapter(product.list, ProductList.this);
            listViewPro.setAdapter(apdater);
        }
    }
}
