package org.example.cloud.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
class RoutesConfig {

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("demo_service_route",
					route -> route.path("/demo-service/**")
						.and()
						.method(HttpMethod.GET)
						.filters(filter -> filter.stripPrefix(1))
						.uri("lb://demo-service"))
			.build();
	}

}