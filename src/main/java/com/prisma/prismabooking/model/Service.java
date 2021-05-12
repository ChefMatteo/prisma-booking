package com.prisma.prismabooking.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Service {
    private String name;
    private boolean luxury;
    private BigDecimal price;
}
