package com.elvisjacob.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elvisjacob.entities.Product;
import com.elvisjacob.model.ProductInfo;

@Transactional
@Repository
public class ProductDAO {

	private SessionFactory sessionFactory;
	
	public Product findProduct(String code) {
		try {
			String sqlCmd = "Select e from " + Product.class.getName() + "e where e.code=:code ";
		
			Session session = this.sessionFactory.getCurrentSession();
			Query<Product> query = session.createQuery(sqlCmd, Product.class);
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
	
	// TODO: Upload a new product
	// public void save(ProductForm productForm)
	
	
	
	
	
}
