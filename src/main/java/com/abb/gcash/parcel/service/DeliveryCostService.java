package com.abb.gcash.parcel.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.abb.gcash.parcel.dto.DeliveryCostComputationRule;
import com.abb.gcash.parcel.dto.DeliveryCostDto;
import com.abb.gcash.parcel.exception.CannotCategorizeparcelException;
import com.abb.gcash.parcel.exception.InvalidVoucherException;

@Service
public class DeliveryCostService {

    @Autowired
    Discountservice discountservice;

    public BigDecimal calculateParcelCost(DeliveryCostDto dto) throws JsonMappingException, JsonProcessingException,
	    InvalidVoucherException, CannotCategorizeparcelException {

	BigDecimal discountPercentage = discountservice.getDiscountPercentageUsingViaVoucher(dto.getVoucherCode());

	BigDecimal finalCost = BigDecimal.ZERO;

	Optional<DeliveryCostComputationRule> rule = DeliveryCostComputationRule.getDeliveryCostRule(dto);
	if (rule.isPresent()) {
	    finalCost = rule.get().getCalculator().apply(rule.get().getGetterFunction().apply(dto)).setScale(2,
		    BigDecimal.ROUND_HALF_EVEN);
	} else {
	    throw new CannotCategorizeparcelException("Cannot categorize parcel");
	}
	BigDecimal discountAmount = finalCost.multiply(discountPercentage).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	BigDecimal discountedCost = finalCost.subtract(discountAmount);
	System.out.println("FINAL COST=" + (finalCost));
	System.out.println("DISCOUNT AMOUNT=" + (discountAmount));
	System.out.println("discountedCost=" + (discountedCost));
	return discountedCost.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    }

    public String getParcelCategory(DeliveryCostDto dto) {
	return DeliveryCostComputationRule.getParcelCategory(dto);
    }

}
