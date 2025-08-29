package com.example.Box_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoxRequestDTO {
    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotNull(message = "Weight limit is required")
    @Positive(message = "Weight limit must be positive")
    private BigDecimal weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery must be between 0 and 100")
    @Max(value = 100, message = "Battery must be between 0 and 100")
    private Integer batteryLevel;


}
