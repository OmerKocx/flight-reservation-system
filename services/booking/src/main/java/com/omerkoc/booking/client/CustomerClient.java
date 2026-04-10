package com.omerkoc.booking.client;

import com.omerkoc.booking.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8090/api/customers")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerResponse getCustomerById(@PathVariable("id") String id);
}
