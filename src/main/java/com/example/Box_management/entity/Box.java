package com.example.Box_management.entity;

import com.example.Box_management.enums.BoxState;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "box")

public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private BigDecimal weightLimit;

    private int batteryLevel;

    @Enumerated(EnumType.STRING)
    private BoxState state = BoxState.IDLE;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public BigDecimal getCurrentWeight() {
        return items.stream()
                .map(Item::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
