package com.example.Box_management.dto.response;

import com.example.Box_management.enums.BoxState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor
@Builder
@Data
@Getter
public class BoxResponseDTO {
    private Long id;
    private String serialNumber;
    private BigDecimal weightLimit;
    private Integer batteryLevel;
    private BoxState state;
    private List<ItemResponseDTO> items;
}
