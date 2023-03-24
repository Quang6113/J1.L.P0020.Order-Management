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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class CustomersList {
    private final List<Customers> customerList;
    private HashMap<String, String> customerMap;
    private String customerID = "",
            customerName = "",
            customerAddress = "",
            customerPhone = "";

    public CustomersList(List<Customers> customersList) {
        this.customerList = customersList;
    }

    public CustomersList() {
        customerList = new ArrayList<>();
    }

    public List<Customers> getCustomerList() {
        return customerList;
    }
    
    public HashMap<String, String> getCustomerMap() {
        customerMap = new HashMap<>();
        customerList.forEach((customer) -> {
            customerMap.put(customer.getCustomerID(), customer.getCustomerName());
        });
        return customerMap;
    }
    
    public String getCustomerName(String customerID){
        return customerMap.get(customerID);
    }
    
    public List<Customers> loadFromFile(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                Customers customer = new Customers(customerData[0], customerData[1],
                        customerData[2], customerData[3]);
                customerList.add(customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + f.getName() + " could not be found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file " 
                    + f.getName() + ".");
        }
        return customerList;
    }

    public boolean saveToFile(File f) {
        boolean append = false;
        try (FileWriter fw = new FileWriter(f, append);
                BufferedWriter bw = new BufferedWriter(fw)) {
            for (Customers customer : customerList) {
                String customerData = customer.getCustomerID() + "," 
                        + customer.getCustomerName() + "," + customer.getCustomerAddress() 
                        + "," + customer.getCustomerPhone();
                bw.write(customerData);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public void printCustomers(){
        if(customerList.isEmpty()){
            System.err.println("Customers list is empty!");
            return;
        }
        
        customerList.forEach(customer -> {System.out.println(customer);});
    }
    
    public Customers searchByID() {
        if(customerList.isEmpty()){
            System.err.println("Customers list is empty!");
            return null;
        }
        
        while (true) {
            System.out.print("Customer's ID (Cxxx): ");
            customerID = Validation.getInput(customerID);
            if (!Validation.checkCustomerID(customerID)) {
                System.err.println("Wrong format!");
            } else {
                break;
            }
        }
        
        Optional<Customers> optionalCustomer = customerList.stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findAny();
        if (optionalCustomer.isPresent()) {
            System.out.println("Current customer: " + optionalCustomer.get());
            return optionalCustomer.get();
        } else {
            System.out.println("Customer does not exist");
            return null;
        }
    }
    
    public void add(Customers customer){
        customerList.add(customer);
    }
    
    public Customers create() {
        // Id
        while (true) {
            System.out.print("Customer's ID (Cxxx): ");
            customerID = Validation.getInput(customerID);
            if (Validation.checkCustomerID(customerID) == false) {
                System.err.println("Wrong format!");
            }else if (Validation.checkCustomerIdAnyMatch(customerID, customerList) == true) {
                System.err.println("The ID has been used!");
            }else{
                break;
            }
        }
        //Name
        while (true) {
            System.out.print("Customer name: ");
            customerName = Validation.getInput(customerName);
            if (Validation.containsSpecialCharacters(customerName) 
                    && Validation.hasNumber(customerName)) {
                System.err.println("Wrong format!");
            } else {
                break;
            }
        }
        customerName = customerName.toUpperCase();
        //Address
        while (true) {
            System.out.print("Customer address: ");
            customerAddress = Validation.getInput(customerAddress);
            if (Validation.containsSpecialCharacters(customerAddress)) {
                System.err.println("Wrong format!");
            } else {
                break;
            }
        }
        customerAddress = customerAddress.toUpperCase();
        //Phone
        while (true) {
            System.out.print("Phone number (10-12 digits): ");
            customerPhone = Validation.getInput(customerPhone);
            if(!Validation.checkPhone(customerPhone)){
                System.err.println("Wrong format!");
            }else{
                break;
            }
        }

        return new Customers(customerID, customerName, customerAddress, customerPhone);
    }
    
    public boolean update(Customers customer) {
        customerID = customer.getCustomerID();
        customerName = customer.getCustomerName();
        customerAddress = customer.getCustomerAddress();
        customerPhone = customer.getCustomerPhone();

        //Id
        while(true){
            System.out.print("Customer's ID (Cxxx): ");
            customerID = Validation.getUpdateInput(customerID);
            if (!customerID.isEmpty() && !Validation.checkCustomerID(customerID)) {
                System.err.println("Wrong format!");
            }else if (!customerID.equals(customer.getCustomerID()) 
                    && Validation.checkCustomerIdAnyMatch(customerID, customerList)){
                System.err.println("The ID has been used!");
            }
            else{
                break;
            }
        }
        //Name
        System.out.print("Customer name: ");
        customerName = Validation.getUpdateInput(customerName);
        customerName = customerName.toUpperCase();

        //Address
        System.out.print("Customer address: ");
        customerAddress = Validation.getUpdateInput(customerAddress);
        customerAddress = customerAddress.toUpperCase();

        //Phone
        while (true) {
            System.out.print("Phone number (10-12 digits): ");
            customerPhone = Validation.getUpdateInput(customerPhone);
            if(!customerPhone.isEmpty() && !Validation.checkPhone(customerPhone)){
                System.err.println("Wrong format!");
            }else{
                break;
            }
        }

        customer.setCustomerID(customerID);
        customer.setCustomerName(customerName);
        customer.setCustomerAddress(customerAddress);
        customer.setCustomerPhone(customerPhone);
        return true;
    }
}