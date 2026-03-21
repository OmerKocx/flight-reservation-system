package com.omerkoc.customer.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

        @Id
        private String id;
        private String tcNo;
        private String name;
        private String surname;
        @CreatedDate
        private LocalDateTime createdDate;
        @LastModifiedDate
        private LocalDateTime lastModifiedDate;

        private String email;
        private String phone;
        private String address;
}
