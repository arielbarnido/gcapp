package com.abb.gcash.parcel.dto;

import java.math.BigDecimal;

import javax.ws.rs.QueryParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryCostDto {

    @QueryParam("weight")
    private BigDecimal weight;
    @QueryParam("height")
    private BigDecimal height;
    @QueryParam("width")
    private BigDecimal width;
    @QueryParam("length")
    private BigDecimal length;
    @QueryParam("voucherCode")
    private String voucherCode;

    private BigDecimal volume;

    public BigDecimal getVolume() {
	return this.volume = height.multiply(width).multiply(length);
    }

}
