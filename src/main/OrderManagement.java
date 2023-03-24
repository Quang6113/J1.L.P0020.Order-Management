/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Customers;
import entities.Orders;
import entities.Products;
import java.util.HashMap;
import java.util.List;
import services.CustomerServices;
import services.OrderServices;
import services.ProductServices;
import utils.Validation;

/**
 *
 * @author QUANG
 */
public class OrderManagement {

    public static void main(String[] args) {
        int choice;
        boolean checkSave = true;
        List<Customers> customers = CustomerServices.getInstance().getList().getCustomerList();
        List<Products> products = ProductServices.getInstance().getList().getProductsList();
        List<Orders> orders = OrderServices.getInstance().getList().getOrderList();
        HashMap<String,String> customerMap = CustomerServices.getInstance().getList().getCustomerMap();
        do {
            System.out.println("\n==== Order Management ====\n"
                    + "1. List all Products\n"
                    + "2. List all Customers\n"
                    + "3. Search a Customer based on his/her ID\n"
                    + "4. Add a Customer\n"
                    + "5. Update a Customer\n"
                    + "6. Save Customers to the file, named customers.txt\n"
                    + "7. List all Orders in ascending order of Customer name\n"
                    + "8. List all pending Orders\n"
                    + "9. Add an Order\n"
                    + "10. Update an Order\n"
                    + "11. Save Orders to file, named orders.txt\n"
                    + "12. Quit");
            System.out.print("Your choice: ");
            choice = Validation.getUserChoice(1, 12);
            switch (choice) {
                case 1:
                    System.out.println("\n---- List all Products ----");
                    ProductServices.getInstance().printAllProducts();
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 2:
                    System.out.println("\n---- List all customers ----");
                    CustomerServices.getInstance().printAllCustomerInformation();
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 3:
                    System.out.println("\n---- Search a Customer based on his/her ID ----");
                    CustomerServices.getInstance().searchCustomerByID();
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 4:
                    System.out.println("\n---- Add a Customer ----");
                    CustomerServices.getInstance().addNewCustomer();
                    checkSave = false;
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 5:
                    System.out.println("\n---- Update a Customer ----");
                    CustomerServices.getInstance().updateCustomer(customers);
                    checkSave = false;
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 6:
                    System.out.println("\n---- Save Customers to the file, named customers.txt ----");
                    CustomerServices.getInstance().saveCustomersToTheFile();
                    checkSave = true;
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 7: 
                    System.out.println("\n---- List all Orders in ascending order of Customer name ----");
                    OrderServices.getInstance().printAllOrderInformation(customerMap);
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 8:
                    System.out.println("\n---- List all pending Orders ----");
                    OrderServices.getInstance().printAllPendingOrders();
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 9: 
                    System.out.println("\n---- Add an Order ----");
                    OrderServices.getInstance().addNewOrder(customers, products);
                    checkSave = false;
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                case 10:
                    System.out.println("\n---- Update an Order ----");
                    System.out.println("1. Update an Order based on its ID\n"
                            + "2. Delete an Order based on its ID");
                    System.out.print("Your choice: ");
                    OrderServices.getInstance().updateOrder();
                    choice = Validation.backToMainMenu(choice, checkSave);
                    checkSave = false;
                    break;
                case 11: 
                    System.out.println("\n---- Save Orders to file, named orders.txt ----");
                    OrderServices.getInstance().saveOrdersToTheFile();
                    checkSave = true;
                    choice = Validation.backToMainMenu(choice, checkSave);
                    break;
                default:
                    if (checkSave == false) {
                        Validation.saveBeforeLeaving();
                    }
                    break;
            }
        } while (choice >= 1 && choice <= 11);
    }
}
