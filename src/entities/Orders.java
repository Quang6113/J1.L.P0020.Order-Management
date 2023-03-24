/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDate;
import java.util.HashMap;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class Orders {

    private String orderID,
            customerID,
            productID;
    private boolean status;
    private LocalDate orderDate;
            
    private int orderQuantity;

    public Orders() {
    }

    public Orders(String orderID, String customerID, String productID, 
            int orderQuantity, LocalDate orderDate, boolean status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.productID = productID;
        this.orderDate = orderDate;
        this.status = status;
        this.orderQuantity = orderQuantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    } 

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getLastName (HashMap<String,String> customerMap){
        String name = customerMap.get(getCustomerID());
        String lastName = name.substring(name.lastIndexOf(" ") +1);
        return lastName;
    }
    
    @Override
    public String toString() {
        return orderID + "," + customerID + "," + productID + "," + orderQuantity 
                + "," + Validation.formatter.format(orderDate) + "," + status;
    } 
}
