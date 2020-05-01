package com.sales.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.models.Order;
import com.sales.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository or;
	
	public ArrayList<Order> getAllOrders() {
		return (ArrayList<Order>)or.findAll();
	}
	
	public void save(Order o) {
		or.save(o);
	}
}
