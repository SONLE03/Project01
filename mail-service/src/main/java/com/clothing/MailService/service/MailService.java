package com.clothing.MailService.service;

import com.clothing.MailService.dto.response.OrderEventResponse;
import com.clothing.MailService.dto.response.SendOtpResponse;

public interface MailService {
    void sendOrder(OrderEventResponse order);
    void sendOtp(SendOtpResponse sendOtpResponse);
}
