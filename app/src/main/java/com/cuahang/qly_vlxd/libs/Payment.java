package com.cuahang.qly_vlxd.libs;

import java.util.Date;

/**
 * Created by ntdan on 5/17/17.
 */

public class Payment {
    int invoiceID;
    float money;
    Date payDate;
    String note;

    public Payment() {
    }

    public Payment(int invoiceID, float money, Date payDate, String note) {
        this.invoiceID = invoiceID;
        this.money = money;
        this.payDate = payDate;
        this.note = note;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
