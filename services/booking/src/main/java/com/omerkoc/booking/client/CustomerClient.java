package com.omerkoc.booking.client;

import com.omerkoc.booking.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${application.config.customer-url}")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerResponse getCustomerById(@PathVariable("id") String id);
}
