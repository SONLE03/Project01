package com.clothing.MailService.event;

import com.clothing.MailService.dto.response.WarrantyInvoiceResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarrantyInvoiceEvent {
    private WarrantyInvoiceResponse warrantyInvoice;
}
