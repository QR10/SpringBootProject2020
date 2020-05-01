package com.sales.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sales.exceptions.NotEnoughStockException;
import com.sales.models.Customer;
import com.sales.models.Order;
import com.sales.models.Product;
import com.sales.services.CustomerService;
import com.sales.services.OrderService;
import com.sales.services.ProductService;

@Controller
@SessionAttributes({"customerList","productList"}) // For data persistence after validation error in new order
public class MainController {
	
	@Autowired
	ProductService ps;
	
	@Autowired
	CustomerService cs;
	
	@Autowired
	OrderService os;
	
	// Get products
	@RequestMapping(value = "/showProducts.html")
	public String getProducts(Model model) {
		ArrayList<Product> products = ps.getAllProducts();
		model.addAttribute("products", products);
		return "showProducts";
	}
	
	// Add Product GET
	@RequestMapping(value = "/addProduct.html", method=RequestMethod.GET)
	public String addProduct(@ModelAttribute("product") Product p) {

		return "addProduct";
	}
	
	// Add Product POST
	@RequestMapping(value = "/addProduct.html", method=RequestMethod.POST)
	public String addProductPOST(@Valid @ModelAttribute("product") Product p, BindingResult result) {
		// Validate input and display errors if present
		if(result.hasErrors())
			return "addProduct";
		ps.save(p);
		return "redirect:showProducts.html";
	}
	
	// Get customers
	@RequestMapping(value = "/showCustomers.html")
	public String getCustomers(Model model) {
		ArrayList<Customer> customers = cs.getAllCustomers();
		model.addAttribute("customers", customers);
		return "showCustomers";
	}
	
	// Add Customer GET
	@RequestMapping(value = "/addCustomer.html", method=RequestMethod.GET)
	public String addCustomerGET(@ModelAttribute("customer") Customer c) {

		return "addCustomer";
	}
	
	// Add Customer POST
	@RequestMapping(value = "/addCustomer.html", method=RequestMethod.POST)
	public String addCustomerPOST(@Valid @ModelAttribute("customer") Customer c, BindingResult result) {
		// Validate input and display errors if present
		if(result.hasErrors())
			return "addCustomer";
		
		cs.save(c);
		return "redirect:showCustomers.html";
	}
	
	// Get Orders
	@RequestMapping(value = "/showOrders.html")
	public String getOrders(Model model) {
		ArrayList<Order> orders = os.getAllOrders();
		model.addAttribute("orders", orders);
		return "showOrders";
	}
	
	// Add Order GET
	@RequestMapping(value = "/newOrder.html", method=RequestMethod.GET)
	public String addOrderGET(Model model) {
		// Get all customers and products and store them into ArrayList 
		ArrayList<Customer> customers = cs.getAllCustomers();
		ArrayList<Product> products = ps.getAllProducts();
		
		Map<Long,String> customerList = 
				new LinkedHashMap<Long, String>();
		// Put customer id and name into customer list
		for (Customer c : customers) {
			customerList.put(c.getcId(), c.getcName());
		}
		
		Map<Long,String> productList = 
				new LinkedHashMap<Long, String>();
		// Put product id and description into customer list
		for (Product p : products) {
			productList.put(p.getpId(), p.getpDesc());
		}
		
		// Add customer and product list to the model
		model.addAttribute("customerList", customerList);
		model.addAttribute("productList", productList);
		
		// Create a new order and add it to the model as well
		Order o = new Order();
		model.addAttribute("order", o);
		return "newOrder";
	}
	
	// Add Order POST
	@RequestMapping(value = "/newOrder.html", method=RequestMethod.POST)
	public String addOrderPOST(@Valid @ModelAttribute("order") Order o, BindingResult result) throws NotEnoughStockException {
		// Get the product that the user intends to order and store it
		Product product = o.getProd();
		// Validate input and display errors if present
		if(result.hasErrors())
		{
			return "newOrder";
		}
		// If the order quantity is greater than the current stock throw exception
		else if (product.getQtyInStock() < o.getQty()) {
			Customer cust = o.getCust();
			// Throws exception and pass details about the error
			throw new NotEnoughStockException(product.getQtyInStock(), product.getpId(), cust.getcId(), o.getQty());
		}
		// Valid order
		else {
			// Update product stock and save it
			product.setQtyInStock(product.getQtyInStock() - o.getQty());
			ps.save(product);
		}

		// Get the current time
		LocalDate curDate = LocalDate.now();
		// set the order date as the current date and save the order
		o.setOrderDate(curDate.toString());
		os.save(o);
		return "redirect:showOrders.html";
	}
	
}
