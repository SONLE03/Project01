package com.clothing.OrderService.service;

import com.clothing.OrderService.constant.APIStatus;
import com.clothing.OrderService.dto.SagaDTO;
import com.clothing.OrderService.dto.request.OrderItemRequest;
import com.clothing.OrderService.dto.request.OrderRequest;
import com.clothing.OrderService.dto.response.CustomerResponse;
import com.clothing.OrderService.dto.response.OrderItemResponse;
import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import com.clothing.OrderService.dto.response.ProductToOrder;
import com.clothing.OrderService.event.SagaEvent;
import com.clothing.OrderService.exception.BusinessException;
import com.clothing.OrderService.model.*;
import com.clothing.OrderService.repository.OrderItemRepository;
import com.clothing.OrderService.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthService authService;
    private final PaymentService paymentService;
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(APIStatus.ORDER_NOT_FOUND));
        return order;
    }
    @Transactional
    @Override
    public UUID createOrder(OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        // Danh sách sản phẩm cần mua
        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();
        // Chi tiết hóa đơn
        List<OrderItem> orderItems = new ArrayList<>();
        // Lưu trữ thông tin toàn bộ order để gửi mail đến khách hàng
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        // Lưu trữ productItem (ProductId, List<ProductItemId>)
        List<UUID> productItemId = new ArrayList<>();
        List<UUID> productItems = new ArrayList<>();
        UUID customerId = orderRequest.getCustomerId();
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        if(customerResponse == null){
            throw new BusinessException(APIStatus.CUSTOMER_NOT_FOUND);
        }

        Order order = new Order();
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomer(customerId);
        order.setCreatedBy(authService.getIdLogin());
        orderRepository.save(order);

        BigDecimal _total =  BigDecimal.ZERO;

        for(OrderItemRequest item : orderItemRequestList){
            UUID productId = item.getProductId();
            ProductToOrder productResponse = productService.getProductToOrder(productId);

            Integer quantity = item.getQuantity();

            if(productResponse == null){
                throw new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
            }
            if(productResponse.getQuantity() < quantity){
                throw new BusinessException(APIStatus.INSUFFICIENT_PRODUCT_QUANTITY);
            }

            // Lưu trữ cặp <key,value> để cập nhật số lượng sản phẩm
            productItemId = productService.getProductItem(productId, quantity);
            productItems.addAll(productItemId);

            BigDecimal price = productResponse.getPrice();
            BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
            _total = _total.add(total);

            OrderItemKey orderItemKey = new OrderItemKey();
            orderItemKey.setOrderId(order.getId());
            orderItemKey.setProductItemId(productId);

            OrderItem orderItem = OrderItem.builder()
                    .id(orderItemKey)
                    .order(order)
                    .price(productResponse.getPrice())
                    .quantity(quantity)
                    .total(total)
                    .build();
            orderItemResponses.add(new OrderItemResponse(productId, productItems, productResponse.getProduct_Name() , quantity, price, total, productResponse.getWarrantyPeriod()));
            orderItems.add(orderItem);
        }
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        order.setTotal(_total);
        order.setCreatedAt(time);
        order.setUpdatedAt(time);
        orderItemRepository.saveAll(orderItems);
        OrderEventResponse orderEventResponse = new OrderEventResponse(order.getId(), orderItemResponses, customerResponse, order.getUpdatedAt(), _total);
        SagaDTO sagaDTO = new SagaDTO(orderEventResponse);
        saveEventToRedis(order.getId(), sagaDTO);
        if(PaymentMethod.convertIntegerToPaymentMethod(orderRequest.getPaymentMethod()) == PaymentMethod.CASH){
            handlePaymentResult(order.getId(),"ordersuccess");
        }else{
            paymentService.submitOrder(_total.intValue(), "Thanh toán hóa đơn bán hàng", httpServletRequest);
        }
        orderRepository.save(order);
//        return "Order was created successfully";
        return order.getId();
    }
    private void saveEventToRedis(UUID orderId, SagaDTO sagaDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String dto = objectMapper.writeValueAsString(sagaDTO);
            System.out.println(dto);
            redisTemplate.opsForHash().put("order:" + orderId, "sagaDTO", dto);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }
    private <T> T getEventFromRedis(UUID orderId, TypeReference<T> typeReference, String hashKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = (String) redisTemplate.opsForHash().get("order:" + orderId, hashKey);
        if (jsonString == null) {
            System.out.println("No value found in Redis for key: " + hashKey);
            return null;
        }

        try {
            System.out.println(jsonString);
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void deleteOrderInRedis(UUID orderId) {
        redisTemplate.delete("order:" + orderId);
    }

    @Override
    public void handlePaymentResult(UUID orderId, String paymentResult) {
        final String paymentSuccess = "ordersuccess";
        final String paymentFail =  "orderfail";
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(APIStatus.ORDER_NOT_FOUND));
        if(paymentResult.equals(paymentSuccess)){
            order.setOrderStatus(OrderStatus.PAID);
            order.setPaymentAt(time);
            order.setUpdatedAt(time);
            SagaDTO sagaDTO = getEventFromRedis(orderId, new TypeReference<SagaDTO>() {}, "sagaDTO");
            applicationEventPublisher.publishEvent(new SagaEvent(this, sagaDTO));
        }else{
            order.setOrderStatus(OrderStatus.CANCELED);
            order.setCanceledAt(time);
            order.setUpdatedAt(time);
        }
        deleteOrderInRedis(orderId);
        orderRepository.save(order);
    }

    @Override
    public String rollBackOrder(UUID orderId) {
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(APIStatus.ORDER_NOT_FOUND));
        order.setOrderStatus(OrderStatus.CANCELED);
        order.setCanceledAt(time);
        order.setUpdatedAt(time);
        orderRepository.save(order);
        return "Order was not created successfully";
    }
    @Override
    public String orderSuccess(UUID orderId){
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(APIStatus.ORDER_NOT_FOUND));
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setCompletedAt(time);
        order.setUpdatedAt(time);
        orderRepository.save(order);
        return "Order was created successfully";
    }

    @Override
    public Flux<String> getOrderStatus(UUID orderId) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> orderRepository.findById(orderId)
                        .map(order -> order.getOrderStatus().toString())
                        .orElse("Order not found"))
                .distinctUntilChanged()
                .take(Duration.ofSeconds(3));
    }
}