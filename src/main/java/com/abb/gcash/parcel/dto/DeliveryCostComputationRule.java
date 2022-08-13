package com.abb.gcash.parcel.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum DeliveryCostComputationRule {

    REJECT(input -> input.compareTo(new BigDecimal(50)) == 1, (input) -> input.multiply(new BigDecimal(-1)),
	    DeliveryCostDto::getWeight), //
    HEAVY(input -> input.compareTo(new BigDecimal(10)) == 1, (input) -> input.multiply(new BigDecimal(20)),
	    DeliveryCostDto::getWeight), //
    SMALL(input -> input.compareTo(new BigDecimal(1500)) == -1, (input) -> input.multiply(new BigDecimal(.03)),
	    DeliveryCostDto::getVolume), //
    MEDIUM(input -> input.compareTo(new BigDecimal(2500)) == -1, (input) -> input.multiply(new BigDecimal(.04)),
	    DeliveryCostDto::getVolume), //
    LARGE((input) -> true, (input) -> input.multiply(new BigDecimal(.05)), DeliveryCostDto::getVolume);

    Predicate<BigDecimal> ruleCondition;
    Function<BigDecimal, BigDecimal> calculator;
    Function<DeliveryCostDto, BigDecimal> getterFunction;

    DeliveryCostComputationRule(Predicate<BigDecimal> ruleCondition, Function<BigDecimal, BigDecimal> calculator,
	    Function<DeliveryCostDto, BigDecimal> getterFunction) {
	this.ruleCondition = ruleCondition;
	this.calculator = calculator;
	this.getterFunction = getterFunction;
    }

    public static List<DeliveryCostComputationRule> getRules() {
	return Stream.of(DeliveryCostComputationRule.values()).collect(Collectors.toList());
    }

    public static Optional<DeliveryCostComputationRule> getDeliveryCostRule(DeliveryCostDto dto) {
	boolean isRuleToRun = false;
	for (DeliveryCostComputationRule rule : DeliveryCostComputationRule.getRules()) {
	    isRuleToRun = rule.getRuleCondition().test(rule.getGetterFunction().apply(dto));
	    if (isRuleToRun) {
		return Optional.of(rule);
	    }
	}
	return Optional.empty();
    }

    public static String getParcelCategory(DeliveryCostDto dto) {
	Optional<DeliveryCostComputationRule> rule = getDeliveryCostRule(dto);
	return rule.isPresent() ? rule.get().name() : "NOT FOUND";
    }

}
