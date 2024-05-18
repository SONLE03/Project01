package com.clothing.warrantyservice.event;

import com.clothing.warrantyservice.dto.response.WarrantyInvoiceResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class WarrantyInvoiceEvent extends ApplicationEvent {
    private WarrantyInvoiceResponse warrantyInvoice;

    public WarrantyInvoiceEvent(Object source, WarrantyInvoiceResponse warrantyInvoice) {
        super(source);
        this.warrantyInvoice = warrantyInvoice;
    }
    public WarrantyInvoiceEvent(WarrantyInvoiceResponse warrantyInvoice) {
        super(null);
        this.warrantyInvoice = warrantyInvoice;
    }
}
