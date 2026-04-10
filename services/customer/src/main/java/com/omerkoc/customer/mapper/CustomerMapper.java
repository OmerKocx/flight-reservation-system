package com.omerkoc.customer.mapper;

import org.springframework.stereotype.Component;

import com.omerkoc.customer.dto.CustomerRequestDto;
import com.omerkoc.customer.dto.CustomerResponseDto;
import com.omerkoc.customer.model.Customer;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .tcNo(customerRequestDto.tcNo())
                .name(customerRequestDto.name())
                .surname(customerRequestDto.surname())
                .email(customerRequestDto.email())
                .phone(customerRequestDto.phone())
                .address(customerRequestDto.address())
                .build();
    }

    public CustomerResponseDto toCustomerResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .tcNo(customer.getTcNo())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .createdDate(customer.getCreatedDate() != null ? customer.getCreatedDate().toString() : null)
                .lastModifiedDate(
                        customer.getLastModifiedDate() != null ? customer.getLastModifiedDate().toString() : null)
                .build();
    }
}
