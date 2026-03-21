package com.omerkoc.customer.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.customer.dto.CustomerRequestDto;
import com.omerkoc.customer.dto.CustomerResponseDto;
import com.omerkoc.customer.exception.CustomerNotFoundException;
import com.omerkoc.customer.mapper.CustomerMapper;
import com.omerkoc.customer.model.Customer;
import com.omerkoc.customer.repository.CustomerRepository;
import com.omerkoc.customer.service.ICustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {

        Customer customer = customerMapper.toCustomer(customerRequestDto);
        customer.setId(UUID.randomUUID().toString());
        customerRepository.save(customer);
        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public CustomerResponseDto updateCustomer(String id, CustomerRequestDto customerRequestDto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer nor found with id: " + id));
        customer.setTcNo(customerRequestDto.tcNo());
        customer.setName(customerRequestDto.name());
        customer.setSurname(customerRequestDto.surname());
        customer.setEmail(customerRequestDto.email());
        customer.setPhone(customerRequestDto.phone());
        customer.setAddress(customerRequestDto.address());
        customerRepository.save(customer);
        return customerMapper.toCustomerResponseDto(customer);

    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseDto getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        var customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponseDto).collect(Collectors.toList());
    }

}
