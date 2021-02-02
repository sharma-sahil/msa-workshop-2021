package org.selflearning.msa.prices.controller;

import javax.annotation.Resource;

import org.selflearning.msa.prices.services.PriceCalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/prices")
public class PriceController {

	@Value("${server.port}")
	private int port;

	@Resource
	PriceCalculationService priceCalculationService;

	@GetMapping
	Double getProductPrice(@RequestParam(name = "goldWeight") Double goldWeight,
			@RequestParam(name = "goldPurity") String goldPurity,
			@RequestParam(name = "pearlsWeight") Double pearlsWeight) {
		System.out.println("Working from port " + port + " of price service");
		return priceCalculationService.calculateProductPrice(goldWeight, goldPurity, pearlsWeight);
	}

	@GetMapping(value = "gold")
	Double getGoldPrice() {
		return 4000.0D;
	}
	
	@GetMapping(value = "pearl")
	Double getPearlsPrice() {
		return 500.0D;
	}
}
