package com.obeid.springrest.crud.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obeid.springrest.crud.entity.Customer;
import com.obeid.springrest.crud.sevice.CustomerService;


@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	// read customers Get: /customers
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		
		return  customerService.getCustomers();
	}
	
	

}
