package com.cuahang.qly_vlxd.invoice;

/**
 * Created by ntdan on 5/17/17.
 */

public class InvoiceDetail {
    int invoiceID;
    int productID;
    double quantity;
    double price;

    public InvoiceDetail(int invoiceID, int productID, double quantity, double price) {
        this.invoiceID = invoiceID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public InvoiceDetail() {
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
