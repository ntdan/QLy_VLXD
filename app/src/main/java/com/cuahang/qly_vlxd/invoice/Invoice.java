package com.cuahang.qly_vlxd.invoice;

import java.util.Date;

/**
 * Created by ntdan on 5/17/17.
 */

public class Invoice {
    int id;
    int customerID;
    Date buyDate;
    boolean payComplete;

    public Invoice() {
    }

    public Invoice(int id, int customerID, Date buyDate, boolean payComplete) {
        this.id = id;
        this.customerID = customerID;
        this.buyDate = buyDate;
        this.payComplete = payComplete;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public boolean isPayComplete() {
        return payComplete;
    }

    public void setPayComplete(boolean payComplete) {
        this.payComplete = payComplete;
    }
}
