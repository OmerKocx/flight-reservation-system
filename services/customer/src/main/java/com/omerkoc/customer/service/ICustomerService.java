package com.omerkoc.customer.service;

import java.util.List;

import com.omerkoc.customer.dto.CustomerRequestDto;
import com.omerkoc.customer.dto.CustomerResponseDto;

public interface ICustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto updateCustomer(String id, CustomerRequestDto customerRequestDto);

    void deleteCustomer(String id);

    CustomerResponseDto getCustomerById(String id);

    List<CustomerResponseDto> getAllCustomers();
}
