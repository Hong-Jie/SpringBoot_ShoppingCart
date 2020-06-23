package com.elvisjacob.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elvisjacob.entities.Account;

@Transactional
@Repository
public class AccountDAO {

	@Autowired
	private EntityManager entityManager;
	
	public Account findAccount(String username) {
		return this.entityManager.find(Account.class, username);
	}
	
}
