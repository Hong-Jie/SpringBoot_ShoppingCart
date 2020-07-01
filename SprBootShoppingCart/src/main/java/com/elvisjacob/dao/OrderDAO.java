package com.elvisjacob.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
import com.elvisjacob.model.OrderDetailInfo;
import com.elvisjacob.model.OrderInfo;
import com.elvisjacob.pagination.PaginationResult;

@Transactional
@Repository
public class OrderDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ProductDAO productDao;
	
	private int getMaxOrderNum() {
		String sqlCmd = "Select max(o.orderNum) from " + Order.class.getName() +  " o ";
		TypedQuery<Integer> query = entityManager.createQuery(sqlCmd, Integer.class);
		Integer value = (Integer) query.getSingleResult();
		if (value == null) {
			return 0;
		}
		return value;
	}
	
	public void saveOrder(CartInfo cartInfo) {
		int orderNum = this.getMaxOrderNum()+1;
		Order order = new Order();
		
		order.setId(UUID.randomUUID().toString());
		order.setOrderNum(orderNum);
		order.setOrderDate(new Date());
		order.setAmount(cartInfo.getAmountTotal());
		
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setName(customerInfo.getName());
        order.setAddr(customerInfo.getAddr());
        order.setEmail(customerInfo.getEmail());
        order.setPhone(customerInfo.getPhone());
        
        entityManager.persist(order);
        
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
        	
        	entityManager.persist(detail);
        }
        
        cartInfo.setOrderNum(orderNum);
        entityManager.flush(); 
	}
	
	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
		
		String sqlCmd = "Select new " + OrderInfo.class.getName()
				+ " (ord.id, ord.orderDate, ord.orderNum, ord.amount, ord.name, ord.addr, "
				+ "ord.email, ord.phone) from " + Order.class.getName() + " ord "
				+ "order by ord.orderNum desc";
		
		Query<OrderInfo> query = (Query<OrderInfo>) entityManager.createQuery(sqlCmd, OrderInfo.class);
		return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}
	
	public Order findOrder(String id) {
		return entityManager.find(Order.class, id);
	}
	
	public OrderInfo getOrderInfo(String id) {
		Order order = this.findOrder(id);
		if (order == null) {
			return null;
		}
		return new OrderInfo(order);
	}
	
	public List<OrderDetailInfo> listOrderDetailInfos(String id){
		String sqlCmd = "Select new " + OrderDetailInfo.class.getName()
				+ " (d.id, d.product.code, d.product.name, d.quantity, d.price, d.amount) "
                + "from " + OrderDetail.class.getName() + " d "
                + "where d.order.id = :orderId ";
		
		Query<OrderDetailInfo> query = (Query<OrderDetailInfo>) entityManager.createQuery(sqlCmd, OrderDetailInfo.class);
		query.setParameter("orderId", id);
		
		return query.getResultList();
	}
}
