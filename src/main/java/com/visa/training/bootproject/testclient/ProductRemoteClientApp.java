
package com.visa.training.bootproject.testclient;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.visa.training.bootproject.domain.Product;



public class ProductRemoteClientApp {

    public static void main(String[] args) {
        createProductWithUI();
        System.out.println("All Products : ");
        listProducts();
    }
    
    private static void listProducts() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<Product>> response = template.exchange("http://localhost:8080/api/products", 
                HttpMethod.GET, null    , new ParameterizedTypeReference<List<Product>>(){});
        
        List<Product> list = response.getBody();
        list.forEach(System.out::println);
    }

    public static void createProductWithUI() {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a name: ");
        String name = kb.nextLine();
        System.out.println("Enter a price : ");
        float price = Float.parseFloat(kb.nextLine());
        System.out.println("Enter a qoh : ");
        int qoh = Integer.parseInt(kb.nextLine());
        
        Product p = new Product(name, price, qoh);
        try {
            RestTemplate template = new RestTemplate();
            URI uri = template.postForLocation("http://localhost:8080/api/products", p);
            System.out.println("Created product with uri : "+uri.toString());
        } catch (Exception e) {
            //System.out.println("Value too low for creation");
            e.printStackTrace();
        }
        

    }

}


