package com.es.gatewayService;

import com.es.gatewayService.config.RedisHashComponent;
import com.es.gatewayService.dto.ApiKey;
import com.es.gatewayService.util.AppConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication {
//	private final RedisHashComponent redisHashComponent;
//
//	public GatewayServiceApplication(RedisHashComponent redisHashComponent) {
//		this.redisHashComponent = redisHashComponent;
//	}
//	@PostConstruct
//	public void initKeysToRedis() {
//		List<ApiKey> apiKeys = new ArrayList<>();
////		apiKeys.add(new ApiKey("343C-ED0B-4137-B27E", List.of(AppConstants.AUTH_SERVICE_KEY)));
////		apiKeys.add(new ApiKey("FA48-EF0C-427E-8CCF", List.of(AppConstants.PRODUCT_SERVICE_KEY)));
//		apiKeys.add(new ApiKey(
//				AppConstants.API_KEY,
//				Stream.of(
//								AppConstants.AUTH_SERVICE_KEY,
//								AppConstants.PRODUCT_SERVICE_KEY
//						)
//						.collect(Collectors.toList())));
////		redisHashComponent.hDel(AppConstants.RECORD_KEY);
//		List<Object> lists = redisHashComponent.hValues(AppConstants.RECORD_KEY);
//		for(Object object : lists){
//			System.out.println(object.toString());
//		}
//		if (lists.isEmpty()) {
//			apiKeys.forEach(apiKey -> redisHashComponent.hSet(AppConstants.RECORD_KEY, apiKey.getKey(), apiKey));
//		}
//	}

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(AppConstants.AUTH_SERVICE_KEY,
//						r -> r.path("/auth/**")
//								.filters(f -> f).uri("http://localhost:9004"))
//				.route(AppConstants.PRODUCT_SERVICE_KEY,
//						r -> r.path("/product/**")
//								.filters(f -> f).uri("http://localhost:9091"))
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
