package com.cuahang.qly_vlxd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v)
    {
        Intent intent = new Intent(MainActivity.this, ProductList.class);
        if(v.getId() == R.id.btnProduct){
            intent = new Intent(MainActivity.this, ProductList.class);
        }

        if (v.getId() == R.id.btnCustomer) {
            intent = new Intent(MainActivity.this, CustomerList.class);
        }


        if(v.getId() == R.id.btnExit){
            finish();
        }

        startActivity(intent);
    }
}
