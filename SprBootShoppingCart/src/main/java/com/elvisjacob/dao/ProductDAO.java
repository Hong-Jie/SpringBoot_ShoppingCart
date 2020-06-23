package com.elvisjacob.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elvisjacob.entities.Product;
import com.elvisjacob.model.ProductInfo;

@Transactional
@Repository
public class ProductDAO {

	
	@Autowired
	private EntityManager entityManager;
	
	public Product findProduct(String code) {
		try {
			String sqlCmd = "Select e from " + Product.class.getName() + "e where e.code=:code ";

			TypedQuery<Product> query = entityManager.createQuery(sqlCmd, Product.class);
			query.setParameter("code", code);
			return (Product) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public ProductInfo findProductInfo(String code) {
		Product product = this.findProduct(code);
		if (product == null) {
			return null;
		}
		return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
	}
	
	// TODO: Upload new product
	// public void save(ProductForm productForm)
	
	
	
	
	
}
