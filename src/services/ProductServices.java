package services;

import entities.ProductsList;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author QUANG
 */
public class ProductServices {
    
    private static final ProductServices PRODUCT_SERVICES = new ProductServices();
    private final ProductsList productsList = new ProductsList();
    private final File productsFile = new File("products.txt");
    private String customerID;

    private ProductServices() {
        productsList.loadFromFile(productsFile);
    }

    public static ProductServices getInstance() {
        return PRODUCT_SERVICES;
    }

    public ProductsList getList() {
        return productsList;
    }

    public void printAllProducts() {
        System.out.println(
                "Please save your changes first if you want to see them printed "
                + "from the file");
        ProductsList tmpProducts = new ProductsList();
        tmpProducts.loadFromFile(productsFile);
        tmpProducts.printProducts();
    }
}
