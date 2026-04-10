package com.omerkoc.booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.omerkoc.booking.client.CustomerClient;
import com.omerkoc.booking.client.FlightClient;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class BookingApplicationTests {

	@MockitoBean
	CustomerClient customerClient;

	@MockitoBean
	FlightClient flightClient;

	@Test
	void contextLoads() {
	}

}
