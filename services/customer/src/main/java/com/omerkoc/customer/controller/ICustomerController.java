package com.omerkoc.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.customer.dto.CustomerRequestDto;
import com.omerkoc.customer.dto.CustomerResponseDto;

public interface ICustomerController {

    ResponseEntity<CustomerResponseDto> createCustomer(CustomerRequestDto customerRequestDto);

    ResponseEntity<CustomerResponseDto> updateCustomer(String id, CustomerRequestDto customerRequestDto);

    ResponseEntity<Void> deleteCustomer(String id);

    ResponseEntity<CustomerResponseDto> getCustomerById(String id);

    ResponseEntity<List<CustomerResponseDto>> getAllCustomers();
}
