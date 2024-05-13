package com.clothing.MailService.service;

import com.clothing.MailService.dto.response.OrderEventResponse;

public interface MailService {
    void sendOrder(OrderEventResponse order);

}
