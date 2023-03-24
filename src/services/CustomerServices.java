/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Customers;
import entities.CustomersList;
import java.io.File;
import java.util.List;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class CustomerServices {

    private static final CustomerServices CUSTOMER_SERVICES = new CustomerServices();
    private final CustomersList customersList = new CustomersList();
    private final File customersFile = new File("customers.txt");
    private String customerID;
    private Customers customer;

    private CustomerServices() {
        customersList.loadFromFile(customersFile);
    }

    public static CustomerServices getInstance() {
        return CUSTOMER_SERVICES;
    }

    public CustomersList getList() {
        return customersList;
    }

    public void printAllCustomerInformation() {
        System.out.println(
                "Please save your changes first if you want to see them printed "
                + "from the file");
        CustomersList tmpOrders = new CustomersList();
        tmpOrders.loadFromFile(customersFile).forEach(System.out::println);
    }

    public void searchCustomerByID(){
        customersList.searchByID();
    }
    
    public void addNewCustomer() {
        customer = customersList.create();
        customersList.add(customer);
        System.out.println("Added!");
        if (Validation.getUserConfirmation("Do you want to continue adding?")) {
            addNewCustomer();
        }
    }

    public void updateCustomer(List<Customers> customerList) {
        customer = customersList.searchByID();
        if (customer != null) {
            customersList.update(customer);
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }
    }

    public void saveCustomersToTheFile() {
        if (customersList.saveToFile(customersFile)) {
            System.out.println("File name: " + customersFile.getName());
            System.out.println("File location: " + customersFile.getAbsolutePath());
            System.out.println("Success!");
        } else {
            System.err.println("Error");
        }
    }
}
