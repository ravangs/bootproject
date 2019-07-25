package com.visa.training.bootproject.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visa.training.bootproject.domain.Product;
import com.visa.training.bootproject.service.ProductService;

@RestController
public class ProductRestController {
        
    @Autowired
    ProductService service;
    
    @RequestMapping(value="/api/products",method=RequestMethod.GET)
    public List<Product> getAll(){
    	
    	try {
    		Thread.sleep(2000);
    	}catch(Exception e) {
    		
    	}
    	
        return service.findAll();
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/api/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id")int id) {
        
        Product p = service.findById(id);
        
        if(p != null) {
            return new ResponseEntity<Product>(p, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
         
    }
    
    @RequestMapping(value="/api/products",method=RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody Product toBeCreated) {
        
        try {
            int id = service.addNewProduct(toBeCreated);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/api/products/"+id));
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        
    }
    
    @RequestMapping(method=RequestMethod.PUT,value="/api/products/{id}")
    public ResponseEntity<Product> updateExisting(@RequestBody Product p,@PathVariable("id")int id) {
        Product fromDB = service.findById(id);
        if(fromDB == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        fromDB.setName(p.getName());
        fromDB.setPrice(p.getPrice());
        fromDB.setQoh(p.getQoh());
        service.update(p);
        return new ResponseEntity<Product>(fromDB,HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.DELETE,value="/api/products/{id}")
    public ResponseEntity<Product> remove(@PathVariable("id")int id){
        Product fromDB = service.findById(id);
        if(fromDB == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.remove(id);
        return new ResponseEntity<Product>(fromDB,HttpStatus.OK);
    }

}