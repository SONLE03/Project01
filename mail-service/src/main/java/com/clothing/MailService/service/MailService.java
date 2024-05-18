package com.clothing.MailService.service;

import com.clothing.MailService.dto.response.OrderEventResponse;
import com.clothing.MailService.dto.response.SendOtpResponse;
import com.clothing.MailService.dto.response.WarrantyInvoiceResponse;

public interface MailService {
    void sendOrder(OrderEventResponse order);
    void sendOtp(SendOtpResponse sendOtpResponse);
    void sendWarrantyInvoice(WarrantyInvoiceResponse warrantyInvoiceResponse);
}
