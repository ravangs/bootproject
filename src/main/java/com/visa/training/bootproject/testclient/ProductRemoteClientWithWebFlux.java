
package com.visa.training.bootproject.testclient;

import org.springframework.web.reactive.function.client.WebClient;

import com.visa.training.bootproject.domain.Product;

import reactor.core.publisher.Flux;



public class ProductRemoteClientWithWebFlux {

    public static void main(String[] args) throws InterruptedException {
        //createProductWithUI();
        long start = System.currentTimeMillis();
        System.out.println("All Products : ");
        listProducts();
        long stop = System.currentTimeMillis();
        System.out.println("listing took "+(stop - start)+" ms");
        doSomeOtherWork();
        
        
        Thread.sleep(3000);
        
    }
    
    private static void doSomeOtherWork() {
        System.out.println("Doing Some other work.................");
        
    }

    private static void listProducts() {
        WebClient client = WebClient.create("http://localhost:8080");
        Flux<Product> all = client.get().uri("/api/products").retrieve().bodyToFlux(Product.class);
        all.subscribe(System.out::println);
    }

    

}
