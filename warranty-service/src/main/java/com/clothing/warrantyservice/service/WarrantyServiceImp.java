package com.clothing.warrantyservice.service;

import com.clothing.warrantyservice.constant.APIStatus;
import com.clothing.warrantyservice.dto.SagaDTO;
import com.clothing.warrantyservice.dto.request.WarrantyInvoiceRequest;
import com.clothing.warrantyservice.dto.response.CustomerResponse;
import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import com.clothing.warrantyservice.dto.response.OrderItemResponse;
import com.clothing.warrantyservice.dto.response.WarrantyInvoiceResponse;
import com.clothing.warrantyservice.event.MailEvent;
import com.clothing.warrantyservice.event.OrderSuccessEvent;
import com.clothing.warrantyservice.event.WarrantyFailureEvent;
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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WarrantyServiceImp implements WarrantyService{
    private final WarrantyRepository warrantyRepository;
    private final WarrantyInvoiceRepository warrantyInvoiceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthService authService;

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
                .createdBy(authService.getIdLogin())
                .build();
        warrantyInvoiceRepository.save(invoice);
        applicationEventPublisher.publishEvent(new WarrantyInvoiceEvent(this,
                new WarrantyInvoiceResponse(invoice.getId(), warranty.getId(), warrantyInvoiceRequest.getCustomerName()
                , warrantyInvoiceRequest.getCustomerEmail(), warrantyInvoiceRequest.getProductName(), warrantyInvoiceRequest.getDescription(), warrantyInvoiceRequest.getPrice())));
    }
    @Override
    @Transactional
    public void createWarranty(SagaDTO sagaDTO) {
        try{
            List<Warranty> warrantyList = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();
            Date currentDateAsDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            OrderEventResponse orderEventResponse = sagaDTO.getOrderEventResponse();
            List<OrderItemResponse> orderItemResponses = orderEventResponse.getOrderList();
            UUID customerId = orderEventResponse.getCustomerResponse().getId();
            for(OrderItemResponse item : orderItemResponses){
                Integer warrantyPeriod = item.getWarrantyPeriod();
                Date endDate = Date.from(currentDate.plusMonths(warrantyPeriod).atStartOfDay(ZoneId.systemDefault()).toInstant());
                for(UUID productItem : item.getProductItemList()){
                    Warranty warranty = Warranty.builder()
                            .productItemId(productItem)
                            .customerId(customerId)
                            .createdAt(currentDateAsDate)
                            .updatedAt(currentDateAsDate)
                            .startDate(currentDateAsDate)
                            .endDate(endDate)
                            .warrantyStatus(WarrantyStatus.UNEXPIRED)
                            .createdBy(authService.getIdLogin())
                            .build();
                    warrantyList.add(warranty);
                }
            }
            if(true){
                throw new BusinessException(APIStatus.WARRANTY_EXPIRED);
            }
            warrantyRepository.saveAll(warrantyList);
            applicationEventPublisher.publishEvent(new OrderSuccessEvent(this, orderEventResponse.getOrderId()));
            applicationEventPublisher.publishEvent(new MailEvent(this, orderEventResponse));
        }catch (BusinessException ex){
            applicationEventPublisher.publishEvent(new WarrantyFailureEvent(this, sagaDTO.getOrderEventResponse()));
        }catch (Exception ex){
            applicationEventPublisher.publishEvent(new WarrantyFailureEvent(this, sagaDTO.getOrderEventResponse()));
        }
    }
}
