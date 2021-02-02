package org.selflearning.msa.products.services;

import org.selflearning.msa.products.entities.Product;

public interface ProductsDetailService {

	Product getProductByDesignNumber(String designNumber);
}
