package com.omerkoc.booking.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
                String id,
                String tcNo,
                String name,
                String surname,
                String email,
                String phone,
                String address,
                String createdDate,
                String lastModifiedDate) {
}
