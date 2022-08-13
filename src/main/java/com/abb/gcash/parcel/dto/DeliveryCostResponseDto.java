package com.abb.gcash.parcel.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryCostResponseDto {

    @JsonProperty("category")
    private String category;

    @JsonProperty("cost")
    private BigDecimal cost;

}
