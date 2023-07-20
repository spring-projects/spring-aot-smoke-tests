package com.example.webclient;

import org.springframework.web.service.annotation.GetExchange;

interface DataService {

	@GetExchange("/anything")
	ExchangeDataDto getData();

}
