package org.selflearning.msa.products.controllers;

import javax.annotation.Resource;

import org.selflearning.msa.products.entities.Product;
import org.selflearning.msa.products.services.ProductsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
@RequestMapping(value = "/products")
@EnableCircuitBreaker
public class ProductsController {

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	@Value("${server.port}")
	private int port;

	@Resource
	ProductsDetailService productsDetailService;

	@GetMapping(value = "/{designNumber}")
	Product getProductDetails(@PathVariable(name = "designNumber") String designNumber) {
		System.out.println("Working from port " + port + " of product service");
		return productsDetailService.getProductByDesignNumber(designNumber);
	}

	@GetMapping(value = "/price/{designNumber}")
	public double getPriceForProduct(@PathVariable(name = "designNumber") String designNumber) {
		Product product = productsDetailService.getProductByDesignNumber(designNumber);
		String url = "/prices?goldWeight=" + product.getGoldWeight() + "&goldPurity=" + product.getGoldWeight()
				+ "&pearlsWeight=" + product.getPearlsWeight();
		
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("prices", false);
		double price = restTemplate.getForObject(instance.getHomePageUrl() + url, Double.class);
		
//		url = "http://prices" + url;
//		ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, null, Double.class);
//		double price = response.getBody();
		
		return price;
	}

	public EurekaClient getEurekaClient() {
		return eurekaClient;
	}

	public void setEurekaClient(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}
