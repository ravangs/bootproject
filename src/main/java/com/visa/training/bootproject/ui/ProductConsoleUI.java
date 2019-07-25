package com.visa.training.bootproject.ui;

import java.util.List;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.visa.training.bootproject.domain.Product;
import com.visa.training.bootproject.service.ProductService;

@Component
public class ProductConsoleUI {

	ProductService service;
	final static String REST_SERVICE_URI = "http://localhost:8080/api/products";

	@Autowired
	public void setService(ProductService service) {
		this.service = service;
	}

	/*
	 * private static void getUser(){
	 * System.out.println("Testing getUser API----------"); RestTemplate
	 * restTemplate = new RestTemplate(); User user =
	 * restTemplate.getForObject(REST_SERVICE_URI+"/user/1", User.class);
	 * System.out.println(user);     }            POST     private static void
	 * createUser() {
	 *         System.out.println("Testing create User API----------");
	 *         RestTemplate restTemplate = new RestTemplate();         User user =
	 * new User(0,"Sarah",51,134);         URI uri =
	 * restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
	 *         System.out.println("Location : "+uri.toASCIIString());     }
	 * 
	 * PUT     private static void updateUser() {
	 *         System.out.println("Testing update User API----------");
	 *         RestTemplate restTemplate = new RestTemplate();         User user  =
	 * new User(1,"Tomy",33, 70000);
	 *         restTemplate.put(REST_SERVICE_URI+"/user/1", user);
	 *         System.out.println(user);     }        DELETE     private static void
	 * deleteUser() {
	 *         System.out.println("Testing delete User API----------");
	 *         RestTemplate restTemplate = new RestTemplate();
	 *         restTemplate.delete(REST_SERVICE_URI+);     }
	 */

	public void createProductWithUI() {
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter a name: ");
		String name = kb.nextLine();
		System.out.println("Enter a price : ");
		float price = Float.parseFloat(kb.nextLine());
		System.out.println("Enter a qoh : ");
		int qoh = Integer.parseInt(kb.nextLine());

		

		Product p = new Product(name, price, qoh);
		try {
			int id = service.addNewProduct(p);
			System.out.println("Created product with id : " + id);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<Product>> response = restTemplate.exchange(REST_SERVICE_URI,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
					});

			List<Product> products = response.getBody();
			
			System.out.println(products);
		} catch (IllegalArgumentException e) {
			System.out.println("Value too low for creation");
		}
		

	}

}