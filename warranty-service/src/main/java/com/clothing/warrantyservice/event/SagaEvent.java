package com.clothing.warrantyservice.event;

import com.clothing.warrantyservice.dto.SagaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SagaEvent {
    private SagaDTO sagaDTO;
}
