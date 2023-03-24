/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QUANG
 */
public class ProductsList {
    private final List<Products> productList;

    public ProductsList(List<Products> productList) {
        this.productList = productList;
    }

    public ProductsList() {
        productList = new ArrayList<>();
    }

    public List<Products> getProductsList() {
        return productList;
    }
    
    public List<Products> loadFromFile(File f) {
        try (BufferedReader bf = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] productData = line.split(",");
                Products product = new Products(productData[0], productData[1], 
                        productData[2], productData[3], 
                        new BigDecimal(productData[4]));
                productList.add(product);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + f.getName() + " could not be found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file " 
                    + f.getName() + ".");
        }
        return productList;
    }
    
    public void printProducts() {
        productList.forEach((p) -> System.out.println(p));
    }
}
