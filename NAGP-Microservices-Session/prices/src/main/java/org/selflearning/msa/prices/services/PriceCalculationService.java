package org.selflearning.msa.prices.services;

public interface PriceCalculationService {

	Double calculateProductPrice(Double goldWeight, String goldPurity, Double pearlsWeight);
}
