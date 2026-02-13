package com.omerkoc.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.omerkoc.customer.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
