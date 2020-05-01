package com.sales.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.sales.exceptions.NotEnoughStockException;

@ControllerAdvice
public class ExceptionsController {
	@ExceptionHandler(NotEnoughStockException.class)
	public ModelAndView notEnoughStockPage(HttpServletRequest req, NotEnoughStockException ex) {
		// Create a new ModelAndView using the "newOrderException" jsp
		ModelAndView model = new ModelAndView("newOrderException");
		// Add the exception to the model to access exception data in the jsp and return the model
		model.addObject("exception", ex);
		return model;
	}
}
