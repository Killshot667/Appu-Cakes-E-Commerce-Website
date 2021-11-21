package com.appu.appuscake1.model;

public class Cart {
    private int productid;
    private int customerid;
    private int quantity;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productid=" + productid +
                ", customerid=" + customerid +
                ", quantity=" + quantity +
                '}';
    }
}
