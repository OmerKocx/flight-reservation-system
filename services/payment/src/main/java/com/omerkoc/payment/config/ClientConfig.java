package com.omerkoc.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.omerkoc.payment.client.BookingClient;

@Configuration
public class ClientConfig {

    @Value("${application.config.booking-url}")
    private String bookingUrl;

    @Bean
    public BookingClient bookingClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(bookingUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(BookingClient.class);
    }
}
