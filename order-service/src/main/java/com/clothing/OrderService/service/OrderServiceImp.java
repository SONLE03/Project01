package com.clothing.OrderService.service;

import com.clothing.OrderService.constant.APIStatus;
import com.clothing.OrderService.dto.request.OrderItemRequest;
import com.clothing.OrderService.dto.request.OrderRequest;
import com.clothing.OrderService.dto.response.CustomerResponse;
import com.clothing.OrderService.dto.response.OrderItemResponse;
import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import com.clothing.OrderService.dto.response.event.ProductEventResponse;
import com.clothing.OrderService.dto.response.ProductResponse;
import com.clothing.OrderService.event.OrderEvent;
import com.clothing.OrderService.event.ProductEvent;
import com.clothing.OrderService.exception.BusinessException;
import com.clothing.OrderService.model.Order;
import com.clothing.OrderService.model.OrderItem;
import com.clothing.OrderService.model.OrderItemKey;
import com.clothing.OrderService.model.OrderStatus;
import com.clothing.OrderService.repository.OrderItemRepository;
import com.clothing.OrderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
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
    public String createOrder(OrderRequest orderRequest) {
        UUID customerId = orderRequest.getCustomerId();
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        if(customerResponse == null){
            throw new BusinessException(APIStatus.CUSTOMER_NOT_FOUND);
        }

        Order order = new Order();
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setCustomer(customerId);
        orderRepository.save(order);

        BigDecimal _total =  BigDecimal.ZERO;

        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();
        List<OrderItem> orderItems = new ArrayList<>();
        List<ProductEventResponse> productListEvent = new ArrayList<>();

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for(OrderItemRequest item : orderItemRequestList){
            UUID productId = item.getProductId();
            ProductResponse productResponse = productService.getDetailProduct(productId);

            Integer quantity = item.getQuantity();

            if(productResponse == null){
                throw new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
            }
            if(productResponse.getQuantity() < quantity){
                throw new BusinessException(APIStatus.INSUFFICIENT_PRODUCT_QUANTITY);
            }

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

            orderItemResponses.add(new OrderItemResponse(productId, productResponse.getProduct_Name(), quantity, price, total));

            orderItems.add(orderItem);
            productListEvent.add(new ProductEventResponse(productId, quantity * -1));
        }
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        order.setTotal(_total);
        order.setCreatedAt(time);
        order.setUpdatedAt(time);
        orderItemRepository.saveAll(orderItems);

        OrderEventResponse orderEventResponse = new OrderEventResponse(order.getId(), orderItemResponses, customerResponse, order.getUpdatedAt(), _total);
        applicationEventPublisher.publishEvent(new ProductEvent(this, productListEvent));
        applicationEventPublisher.publishEvent(new OrderEvent(this, orderEventResponse));
        return "Order was created successfully";
    }

    @Override
    public void updateOrderStatus(UUID orderId, Integer status) {

    }
}
