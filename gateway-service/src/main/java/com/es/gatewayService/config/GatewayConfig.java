package com.es.gatewayService.config;

import com.es.gatewayService.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableHystrix
public class GatewayConfig {

//    @Autowired
//    AuthFilter filter;
//
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/users/**")
//                        .filters(f -> f.filter((GatewayFilter) filter))
//                        .uri("lb://user-service"))
//
//                .route("auth-service", r -> r.path("/auth/**")
//                        .filters(f -> f.filter((GatewayFilter) filter))
//                        .uri("lb://auth-service"))
//                .build();
//    }
}
