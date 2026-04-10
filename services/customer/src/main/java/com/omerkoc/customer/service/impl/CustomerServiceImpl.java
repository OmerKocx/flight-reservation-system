package com.omerkoc.customer.service.impl;

import java.util.List;
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
        if (customerRequestDto == null) {
            throw new IllegalArgumentException("Customer request data cannot be null!");
        }

        Customer customer = customerMapper.toCustomer(customerRequestDto);
        if (customer == null) {
            throw new IllegalStateException("Mapping failed: Customer object is null!");
        }

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toCustomerResponseDto(savedCustomer);
    }

    @Override
    public CustomerResponseDto updateCustomer(String id, CustomerRequestDto customerRequestDto) {
        if (customerRequestDto == null) {
            throw new IllegalArgumentException("Update data cannot be null!");
        }

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty!");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        customer.setTcNo(customerRequestDto.tcNo());
        customer.setName(customerRequestDto.name());
        customer.setSurname(customerRequestDto.surname());
        customer.setEmail(customerRequestDto.email());
        customer.setPhone(customerRequestDto.phone());
        customer.setAddress(customerRequestDto.address());

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty!");
        }

        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Cannot delete: Customer not found with id: " + id);
        }

        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseDto getCustomerById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty!");
        }
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        var customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toCustomerResponseDto)
                .collect(Collectors.toList());
    }
}