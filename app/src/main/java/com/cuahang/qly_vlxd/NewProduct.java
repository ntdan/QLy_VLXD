package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.cuahang.qly_vlxd.libs.HoTro;
import com.cuahang.qly_vlxd.libs.Product;

public class NewProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_new_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void click(View view) {
        if (view.getId() == R.id.btnSave) {
            String m = ((EditText) findViewById(R.id.etProductUnit)).getText().toString();
            if (!(m.length() <= 7 && Character.isLetter(m.charAt(0)))) {
                HoTro.ThongBao(NewProduct.this, "Mã sản phẩm phải bắt đầu là chữ và độ dài tối đa là 7.");
                return;
            }


            Product pro = new Product(NewProduct.this);
            pro.setName(((EditText) findViewById(R.id.etProductName)).getText().toString());
            pro.setCode(((EditText) findViewById(R.id.etProductCode)).getText().toString());
            pro.setUnit(((EditText) findViewById(R.id.etProductUnit)).getText().toString());
            pro.setPrice(((EditText) findViewById(R.id.etProductPrice)).getText().toString());

            boolean ok = pro.add();
            Intent proList = new Intent();
            setResult(ok ? RESULT_OK : RESULT_CANCELED, proList);
            finish();
            return;
        }

        if (view.getId() == R.id.btnProductList) {
            Intent proList = new Intent();
            setResult(RESULT_CANCELED, proList);
            finish();
            return;
        }
    }
}
