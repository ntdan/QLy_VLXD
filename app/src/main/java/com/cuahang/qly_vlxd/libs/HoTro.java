package com.cuahang.qly_vlxd.libs;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ntdan-lt on 2/25/2017.
 */

public class HoTro {
    public static void ThongBao(Context c, String thongbao)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(thongbao);
        builder.setTitle("Thông báo");
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("đồng ý", okListener)
                .setNegativeButton("bỏ qua", null)
                .create()
                .show();
    }
}
