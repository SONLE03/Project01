package com.clothing.InventoryService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Setter
@Getter
public class ImportItemId implements Serializable {
    @Column(name = "import_id")
    private UUID importId;
    @Column(name = "productItem_id")
    private UUID productItemId;
}
