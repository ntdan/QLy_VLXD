package com.cuahang.qly_vlxd.libs;

import android.content.Context;

/**
 * Created by ntdan on 5/20/17.
 */

public class Provider extends Customer{
    public Provider(Context context) {
        super(context);
    }

    public Provider(int id, String fullname, String mobile, String address, String image, String note) {
        super(id, fullname, mobile, address, image, note);
    }
}
