package com.sales.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{ 
 @Override
 protected void configure(HttpSecurity http) throws Exception {
  http
    .authorizeRequests()
	.antMatchers("/showProducts.html", "/addProduct.html", "/showCustomers.html","/addCustomer.html","/showOrders.html","/newOrder.html")
	.authenticated()
	.and()
	.formLogin()
	.and()
	.logout();
  }
 
  @Bean
  @Override
  public UserDetailsService userDetailsService() {
	  // Create new manager
	  InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	  // Create a user in the manager with the specified credentials and return the manager
	  manager.createUser(User.withUsername("user").password("user").roles("USER").build());
	  return manager;
  }
}
