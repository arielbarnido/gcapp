package com.abb.gcash.parcel.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.abb.gcash.parcel.exception.InvalidVoucherException;

@Service
public class Discountservice {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final String BASE_URL = "https://mynt-exam.mocklab.io/";

    public BigDecimal getDiscountPercentageUsingViaVoucher(String voucherCode)
	    throws JsonMappingException, JsonProcessingException, InvalidVoucherException {

	if (null == voucherCode || voucherCode.isEmpty()) {
	    return new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	ResponseEntity<String> response = restTemplate
		.getForEntity(BASE_URL + "voucher/" + voucherCode + buildQueryParams(), String.class);
	if (response.getStatusCode() == HttpStatus.OK) {
	    return extractValue(response);
	}
	throw new InvalidVoucherException("Invalid Voucher");
    }

    private BigDecimal extractValue(ResponseEntity<String> response)
	    throws JsonMappingException, JsonProcessingException {
	JsonNode root = objectMapper.readTree(response.getBody());
	JsonNode discount = root.path("discount");

	return new BigDecimal(discount.asText()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private String buildQueryParams() {
	return "?key=apikey";
    }

}
