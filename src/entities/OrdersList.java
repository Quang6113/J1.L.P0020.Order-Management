/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class OrdersList {

    private final List<Orders> orderList;
    private String orderID = "",
            customerID = "",
            productID = "";
    private boolean status;
    private LocalDate orderDate;
    private int orderQuantity = 0;
    private int choice = -1;

    public OrdersList(List<Orders> ordersList) {
        this.orderList = ordersList;
    }

    public OrdersList() {
        orderList = new ArrayList<>();
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public List<Orders> loadFromFile(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                Orders order = new Orders(orderData[0], orderData[1], orderData[2],
                        Integer.parseInt(orderData[3]),
                        LocalDate.parse(orderData[4], DateTimeFormatter.ofPattern("M/d/yyyy")), Boolean.parseBoolean(orderData[5]));
                orderList.add(order);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + f.getName() + " could not be found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file "
                    + f.getName() + ".");
        }

        return orderList;
    }

    public boolean saveToFile(File f) {
        boolean append = false;
        try (FileWriter fw = new FileWriter(f, append);
                BufferedWriter bw = new BufferedWriter(fw)) {
            for (Orders order : orderList) {
                String orderData = order.getOrderID() + "," + order.getCustomerID()
                        + "," + order.getProductID() + "," + order.getOrderQuantity()
                        + "," + Validation.formatter.format(order.getOrderDate()) + "," + order.getStatus();
                bw.write(orderData);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void printOrders(HashMap<String, String> customerMap) {
        if (orderList.isEmpty()) {
            System.err.println("Orders list is empty!");
            return;
        }
        orderList.forEach((o)
                -> System.out.println(o + " - " + o.getLastName(customerMap)));
    }
    
    public List findPendingOrders() {
        if (orderList.isEmpty()) {
            System.out.println("Orders list is empty!");
            return null;
        }
        
        List<Orders> result = orderList.stream()
                .filter((order) -> (order.getStatus() == false))
                .collect(Collectors.toList());

        if (!result.isEmpty()) {
            return result;
        }
        return null;
    }

    public Orders searchByID() {
        if (orderList.isEmpty()) {
            System.err.println("Orders list is empty!");
            return null;
        }
        
        while (true) {
            System.out.print("Order ID (Dxxx): ");
            orderID = Validation.getInput(orderID);
            if (!Validation.checkOrderID(orderID)) {
                System.out.println("Wrong format!");
            } else {
                break;
            }
        }

        Optional<Orders> optionalOrder = orderList.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findAny();
        if (optionalOrder.isPresent()) {
            System.out.println("Current order: " + optionalOrder.get());
            return optionalOrder.get();
        } else {
            System.out.println("Order does not exist");
            return null;
        }
    }

    public boolean add(Orders order) {
        if (order != null) {
            orderList.add(order);
            return true;
        }
        return false;
    }

    public Orders create(List<Customers> customerList, List<Products> productList) {
        //customerID
        System.out.println("Make sure to add all customers before adding a new order!");
        System.out.println("\n--- Available customers ---");
        System.out.println("0. Exit to add");
        for (int i = 0; i < customerList.size(); i++) {
            System.out.println(i + 1 + ". " + customerList.get(i));
        }
        System.out.print("Choose customer: ");
        choice = Validation.getUserChoice(0, customerList.size());
        if (choice != 0) {
            customerID = customerList.get(choice - 1).getCustomerID();
        } else {
            return null;
        }
        //productID
        System.out.println("\n--- Available products ---");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(i + 1 + ". " + productList.get(i));
        }
        System.out.print("Choose product: ");
        choice = Validation.getUserChoice(1, productList.size());
        productID = productList.get(choice - 1).getProductID();
        //orderID
        while (true) {
            System.out.print("Order ID (Dxxx): ");
            orderID = Validation.getInput(orderID);
            if (Validation.checkOrderID(orderID) == false) {
                System.out.println("Wrong format!");
            } else if (Validation.checkOrderIdAnyMatch(orderID, orderList) == true) {
                System.out.println("The order ID has been used!");
            } else {
                break;
            }
        }
        //orderQuantity
        System.out.print("Order Quantity: ");
        orderQuantity = Validation.getInput(orderQuantity);
        //orderDate
        System.out.print("Order Date (M/d/yyyy): ");
        orderDate = Validation.getDate();
        // Status
        System.out.print("Status (1. True, 2. False): ");
        status = false;
        choice = Validation.getUserChoice(1, 2);
        if (choice == 1) {
            status = true;
        }
        return new Orders(orderID, customerID, productID, orderQuantity, orderDate, status);
    }

    public boolean update(Orders order) {
        status = order.getStatus();
        System.out.print("Status (0. Not update, 1. True, 2. False): ");
        choice = Validation.getUserChoice(0, 2);
        switch (choice) {
            case 1:
                status = true;
                break;
            case 2:
                status = false;
                break;
            case 0:
                status = order.getStatus();
        }
        order.setStatus(status);
        return true;
    }

    public boolean delete(Orders order) {
        if (order != null) {
            return orderList.remove(order);
        }
        return false;
    }
}
