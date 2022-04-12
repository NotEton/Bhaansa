package com.cafe.bhaansa;

public class ShowOrderModel {
    String ordername, orderprice, orderquantity;

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public String getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(String orderquantity) {
        this.orderquantity = orderquantity;
    }

    public ShowOrderModel(String ordername, String orderprice, String orderquantity) {
        this.ordername = ordername;
        this.orderprice = orderprice;
        this.orderquantity = orderquantity;
    }

    public ShowOrderModel() {

    }

}
