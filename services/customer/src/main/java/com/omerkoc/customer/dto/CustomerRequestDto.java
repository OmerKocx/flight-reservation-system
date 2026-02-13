package com.omerkoc.customer.dto;

import lombok.Builder;

@Builder
public record CustomerRequestDto(
                String tcNo,
                String name,
                String surname,
                String email,
                String phone,
                String address) {

}
