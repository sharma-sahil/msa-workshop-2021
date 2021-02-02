package org.selflearning.msa.products.entities;

public class Product {
	
	private String designNumber;
	
	private String description;
	
	private Double goldWeight;
	
	private String goldPurity;
	
	private Double pearlsWeight;
	
	private Double netWeight;
	
	private Double price;

	public Product(String designNumber,String description, Double goldWeight, String goldPurity, Double pearlsWeight) {
		this.designNumber=designNumber;
		this.description=description;
		this.goldWeight=goldWeight;
		this.goldPurity=goldPurity;
		this.pearlsWeight=pearlsWeight;
		this.netWeight=goldWeight+pearlsWeight;
	}
	
	public String getDesignNumber() {
		return designNumber;
	}

	public void setDesignNumber(String designNumber) {
		this.designNumber = designNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getGoldWeight() {
		return goldWeight;
	}

	public void setGoldWeight(Double goldWeight) {
		this.goldWeight = goldWeight;
	}

	public String getGoldPurity() {
		return goldPurity;
	}

	public void setGoldPurity(String goldPurity) {
		this.goldPurity = goldPurity;
	}

	public Double getPearlsWeight() {
		return pearlsWeight;
	}

	public void setPearlsWeight(Double pearlsWeight) {
		this.pearlsWeight = pearlsWeight;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
