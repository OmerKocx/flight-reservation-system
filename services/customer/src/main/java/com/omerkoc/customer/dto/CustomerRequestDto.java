package com.omerkoc.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerRequestDto(
                @Size(min = 11, max = 11, message = "TC No must be 11 characters long") String tcNo,

                @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long") String name,

                @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters long") String surname,

                @Email(message = "Email must be valid") @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters long") String email,

                @Size(min = 11, max = 11, message = "Phone must be 11 characters long") String phone,

                @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters long") String address) {
}