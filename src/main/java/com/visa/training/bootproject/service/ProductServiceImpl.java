package com.visa.training.bootproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.visa.training.bootproject.dal.ProductRepository;
import com.visa.training.bootproject.domain.Product;

@Service
public class ProductServiceImpl implements ProductService{
	
	ProductRepository dao;	
	
	@Autowired
	public void setDao(ProductRepository dao) {
		this.dao = dao;
	}

	@Override
	public int addNewProduct(Product p) {
		int id=0;
		if(p.getPrice()*p.getQoh()>=10000) {
			Product created = dao.save(p);
			id = created.getId();
		}else {
			throw new IllegalArgumentException("min value of stock should be at least 10k");
		}
		return id;
	}

	@Override
	public Product findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public List<Product> findAll() {
		return dao.findAll();
	}

	@Override
	public void deleteProduct(int id) {
		Product p = dao.findById(id);
		if(p.getPrice() * p.getQoh()>10000) {
			throw new IllegalArgumentException("can't remove when value > 10000");
		}else {
			dao.deleteById(id);
		}
		
	}
	
	

}
