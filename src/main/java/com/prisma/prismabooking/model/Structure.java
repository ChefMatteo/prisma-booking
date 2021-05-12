package com.prisma.prismabooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Structure {

    private String id;
    private String phone;
    private String address;
    private String email;
    private Boolean wifi;
    private String structureType;
    private List<String> services;

}
