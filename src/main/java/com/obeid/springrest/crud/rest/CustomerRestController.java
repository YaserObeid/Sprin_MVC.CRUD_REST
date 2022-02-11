package com.obeid.springrest.crud.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// read customer by id Get: /customer/{customerId}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		
		Customer customer = customerService.getCustomer(customerId);
		//if id not found -> return null
		if(customer == null) {
			throw new CustomerNotFounfException(
					"There is no customer for the id: "+customerId);
		}
			
		return customer;
	}
	
	// add a new customer POST: /customers
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		// if id == null -> add , else update
		// to force add new customer: id = 0 (null)
		customer.setId(0); 
		customerService.saveCustomer(customer);
		
		return customer;
	}
	
	// update a given customer PUT: /customer
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		// since the given customer has id -> id != null
		// -> apply update
		customerService.saveCustomer(customer);
		
		return customer;
	}
	
	// delete a customer by id DELETE: /customers/{cusomerId}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		Customer customer = customerService.getCustomer(customerId);
		
		//if id not found -> return null
				if(customer == null) {
					throw new CustomerNotFounfException(
							"There is no customer for the id: "+customerId);
				}
					
				
		customerService.deleteCustomer(customerId);
		
		return "customer with id: " + customerId + " is deleted";
	}
	
	

}
