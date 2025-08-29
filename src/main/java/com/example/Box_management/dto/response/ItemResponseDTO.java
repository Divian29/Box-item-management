package com.example.Box_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Getter

public class ItemResponseDTO {
    private Long id;
    private String name;
    private BigDecimal weight;
    private String code;
}
