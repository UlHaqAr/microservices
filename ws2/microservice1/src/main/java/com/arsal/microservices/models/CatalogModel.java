package com.arsal.microservices.models;

public class CatalogModel {
	String movieName;
	int rating;
	String desc;
	
	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public CatalogModel(String name, int rating, String desc) {
		super();
		this.movieName = name;
		this.rating = rating;
		this.desc = desc;
	}
	

	public String getName() {
		return movieName;
	}

	public void setName(String name) {
		this.movieName = name;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}

	
}
