package com.obeid.springrest.crud.sevice;

import java.util.List;

import com.obeid.springrest.crud.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public Customer getCustomer(int customerId);

	public void saveCustomer(Customer customer);

	public void deleteCustomer(int customerId);

}
