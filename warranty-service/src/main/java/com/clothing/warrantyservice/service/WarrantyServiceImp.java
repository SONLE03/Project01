package com.clothing.warrantyservice.service;

import com.clothing.warrantyservice.constant.APIStatus;
import com.clothing.warrantyservice.dto.request.WarrantyInvoiceRequest;
import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import com.clothing.warrantyservice.dto.response.OrderItemResponse;
import com.clothing.warrantyservice.dto.response.WarrantyInvoiceResponse;
import com.clothing.warrantyservice.event.WarrantyInvoiceEvent;
import com.clothing.warrantyservice.exception.BusinessException;
import com.clothing.warrantyservice.model.Warranty;
import com.clothing.warrantyservice.model.WarrantyInvoice;
import com.clothing.warrantyservice.model.WarrantyStatus;
import com.clothing.warrantyservice.repository.WarrantyInvoiceRepository;
import com.clothing.warrantyservice.repository.WarrantyRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarrantyServiceImp implements WarrantyService{
    private final WarrantyRepository warrantyRepository;
    private final WarrantyInvoiceRepository warrantyInvoiceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct()
    private void updateWarrantiesStatus(){
        warrantyRepository.updateExpiredWarranties(new Date());
    }

    @Override
    @Transactional
    public void createWarrantyInvoice(WarrantyInvoiceRequest warrantyInvoiceRequest) {
        Warranty warranty = warrantyRepository.findById(warrantyInvoiceRequest.getWarrantyId()).orElseThrow(
                () -> new BusinessException(APIStatus.WARRANTY_NOT_FOUND));

        if(warranty.getEndDate().before(new Date())){
            throw new BusinessException(APIStatus.WARRANTY_EXPIRED);
        }
        WarrantyInvoice invoice = WarrantyInvoice.builder()
                .warranty(warranty)
                .description(warrantyInvoiceRequest.getDescription())
                .price(warrantyInvoiceRequest.getPrice())
                .build();
        warrantyInvoiceRepository.save(invoice);
        applicationEventPublisher.publishEvent(new WarrantyInvoiceEvent(this,
                new WarrantyInvoiceResponse(invoice.getId(), warranty.getId(), warrantyInvoiceRequest.getCustomerName()
                , warrantyInvoiceRequest.getCustomerEmail(), warrantyInvoiceRequest.getProductName(), warrantyInvoiceRequest.getDescription(), warrantyInvoiceRequest.getPrice())));
    }
    @Override
    @Transactional
    public void createWarranty(OrderEventResponse orderEventResponse) {
        List<OrderItemResponse> order = orderEventResponse.getOrderList();
        LocalDate currentDate = LocalDate.now();
        Date endDate;
        Date currentDateAsDate;
        UUID customerId = orderEventResponse.getCustomerResponse().getCustomerId();
        for (OrderItemResponse orderItem : order){

            Integer warrantyPeriod = orderItem.getWarrantyPeriod();
            endDate = Date.from(currentDate.plusMonths(warrantyPeriod).atStartOfDay(ZoneId.systemDefault()).toInstant());
            currentDateAsDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Warranty warranty = Warranty.builder()
                    .productId(orderItem.getProductId())
                    .customerId(customerId)
                    .createdAt(currentDateAsDate)
                    .updatedAt(currentDateAsDate)
                    .startDate(currentDateAsDate)
                    .endDate(endDate)
                    .warrantyStatus(WarrantyStatus.UNEXPIRED)
                    .build();
            warrantyRepository.save(warranty);
        }
    }
}
