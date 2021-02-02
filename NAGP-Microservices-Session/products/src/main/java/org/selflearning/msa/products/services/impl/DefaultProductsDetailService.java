package org.selflearning.msa.products.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.selflearning.msa.products.entities.Product;
import org.selflearning.msa.products.services.ProductsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DefaultProductsDetailService implements ProductsDetailService {

	@Value("${server.port}")
	private int port;
	
	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Resource
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@HystrixCommand(fallbackMethod = "getProductInfoWithoutPrice")
	public Product getProductByDesignNumber(String designNumber) {

		Optional<Product> resultproduct = getAllProducts().stream()
				.filter(p -> designNumber.equals(p.getDesignNumber())).findFirst();
		if (resultproduct.isPresent()) {

			String baseUrl = loadBalancerClient.choose("prices").getUri().toString() + "/prices";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Double> response = null;

			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("goldWeight", resultproduct.get().getGoldWeight())
						.queryParam("goldPurity", resultproduct.get().getGoldPurity())
						.queryParam("pearlsWeight", resultproduct.get().getPearlsWeight());
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null,
						Double.class);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			
			resultproduct.get().setPrice(response.getBody());
			resultproduct.get().setDescription(resultproduct.get().getDescription()+ " from product service port " + port +" price endpoint "+ baseUrl );
			return resultproduct.get();
		}
		return null;
	}
	
	public Product getProductInfoWithoutPrice(String designNumber){
		Optional<Product> resultproduct = getAllProducts().stream()
				.filter(p -> designNumber.equals(p.getDesignNumber())).findFirst();
		if (resultproduct.isPresent()) {
			return resultproduct.get();
		}
		return null;
	}
	

	protected List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		products.add(new Product("001", "Men Ring", 7.08D, "22", 0.0D));
		products.add(new Product("002", "Women Ring", 6.50D, "22", 0.0D));
		products.add(new Product("003", "Men Chain", 17.06D, "22", 0.0D));
		products.add(new Product("004", "Women Chain", 15.33D, "22", 0.0D));
		products.add(new Product("005", "Women Bangle", 22.76D, "22", 0.0D));
		products.add(new Product("006", "Women Earings", 10.54D, "22", 4.35D));
		products.add(new Product("007", "Women Necklace", 33.52D, "22", 12.40D));
		return products;
	}

}
