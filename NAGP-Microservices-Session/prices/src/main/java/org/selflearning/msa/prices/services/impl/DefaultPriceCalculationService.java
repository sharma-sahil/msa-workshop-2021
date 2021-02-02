package org.selflearning.msa.prices.services.impl;

import org.selflearning.msa.prices.services.PriceCalculationService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPriceCalculationService implements PriceCalculationService {

	@Override
	public Double calculateProductPrice(Double goldWeight, String goldPurity, Double pearlsWeight) {
		// TODO Auto-generated method stub
		return goldWeight*4000.0D+ pearlsWeight *500.0D;
	}

}
