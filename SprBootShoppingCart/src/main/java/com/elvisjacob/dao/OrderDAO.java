package com.elvisjacob.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elvisjacob.entities.Order;
import com.elvisjacob.entities.OrderDetail;
import com.elvisjacob.entities.Product;
import com.elvisjacob.model.CartInfo;
import com.elvisjacob.model.CartLineInfo;
import com.elvisjacob.model.CustomerInfo;

@Transactional
@Repository
public class OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProductDAO productDao;
	
	private int getMaxOrderNum() {
		String sqlCmd = "Select max(o.orderNum) from " + Order.class.getName() +  " o ";
		Session session = this.sessionFactory.getCurrentSession();
		Query<Integer> query = session.createQuery(sqlCmd, Integer.class);
		Integer value = (Integer) query.getSingleResult();
		if (value == null) {
			return 0;
		}
		return value;
	}
	
	public void saveOrder(CartInfo cartInfo) {
		Session session = this.sessionFactory.getCurrentSession();
		
		int orderNum = this.getMaxOrderNum()+1;
		Order order = new Order();
		
		order.setId(UUID.randomUUID().toString());
		order.setOrderNum(orderNum);
		order.setOrderDate(new Date());
		order.setAmount(cartInfo.getAmountTotal());
		
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setName(customerInfo.getName());
        order.setAddr(customerInfo.getAddress());
        order.setEmail(customerInfo.getEmail());
        order.setPhone(customerInfo.getPhone());
        
        session.persist(order);
        
        List<CartLineInfo> lines = cartInfo.getCartLines();
        for (CartLineInfo line: lines) {
        	OrderDetail detail = new OrderDetail();
        	detail.setId(UUID.randomUUID().toString());
        	detail.setOrder(order);
        	detail.setAmount(line.getAmount());
        	detail.setPrice(line.getProductInfo().getPrice());
        	detail.setQuantity(line.getQuantity());
        	
        	String code = line.getProductInfo().getCode();
        	Product product = this.productDao.findProduct(code);
        	detail.setProduct(product);
        	
        	session.persist(detail);
        }
        
        cartInfo.setOrderNum(orderNum);
        session.flush(); 
	}
	
}
