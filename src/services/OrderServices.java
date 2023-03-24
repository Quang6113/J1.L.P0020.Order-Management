/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Customers;
import entities.Orders;
import entities.OrdersList;
import entities.Products;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class OrderServices {

    private static final OrderServices ORDER_SERVICES = new OrderServices();
    private final OrdersList ordersList = new OrdersList();
    private final File ordersFile = new File("orders.txt");
    private Orders order;

    private OrderServices() {
        ordersList.loadFromFile(ordersFile);
    }

    public static OrderServices getInstance() {
        return ORDER_SERVICES;
    }

    public OrdersList getList() {
        return ordersList;
    }

    public void printAllOrderInformation(HashMap<String, String> customerMap) {
        System.out.println(
                "Please save your changes first if you want to see them printed "
                + "from the file");
        OrdersList tmpOrders = new OrdersList();
        
        tmpOrders.loadFromFile(ordersFile);
        tmpOrders.getOrderList().sort((Orders o1, Orders o2) 
                -> o1.getLastName(customerMap).compareToIgnoreCase(o2.getLastName(customerMap)));
        tmpOrders.printOrders(customerMap);
    }

    public void printAllPendingOrders() {
        OrdersList tmpOrders = new OrdersList();
        tmpOrders.loadFromFile(ordersFile);
        List<Orders> pendingOrders = tmpOrders.findPendingOrders();
        pendingOrders.forEach(System.out::println);
    }

    public void addNewOrder(List<Customers> customerList, List<Products> productList) {
        order = ordersList.create(customerList, productList);
        if(ordersList.add(order)){
            System.out.println("Added!");    
        }
        if (Validation.getUserConfirmation("Do you want to continue adding?")) {
            addNewOrder(customerList, productList); 
        } 
    }

    public void updateOrder(){
        int choice = Validation.getUserChoice(1, 2);
        if (choice == 1){
            updateOrderInformation();
        }else{
            deleteOrder();
        }
    }
    public void updateOrderInformation() {
        System.out.println("--- Update an Order based on its ID ---");
        order = ordersList.searchByID();
        if(order != null){
            ordersList.update(order);
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }
    }

    public void deleteOrder(){
        System.out.println("--- Delete an Order based on its ID ---");
        order = ordersList.searchByID();
        if (order != null) {
            ordersList.delete(order);
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }
    }
    
    public void saveOrdersToTheFile() {
        if (ordersList.saveToFile(ordersFile)) {
            System.out.println("File name: " + ordersFile.getName());
            System.out.println("File location: " + ordersFile.getAbsolutePath());
            System.out.println("Success!");
        } else {
            System.err.println("Error");
        }
    }
}
