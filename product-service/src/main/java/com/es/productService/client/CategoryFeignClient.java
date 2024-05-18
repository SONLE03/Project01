package com.es.productService.client;

import com.es.productService.dto.response.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "category-service", url = "${category-service.url}")
public interface CategoryFeignClient {
    @RequestMapping(value = "category-service/{categoryId}", method = RequestMethod.GET)
    CategoryResponse getCategory(@PathVariable Integer categoryId);
}
