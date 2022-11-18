package com.arsal.microservice3.models;

import java.util.List;

public class UserRatingsModel {
	List<RatingModel> userRatings;
	String userId;
	
	public UserRatingsModel(List<RatingModel> userRatings, String userId) {
		super();
		this.userRatings = userRatings;
		this.userId = userId;
	}
	public List<RatingModel> getUserRatings() {
		return userRatings;
	}
	public void setUserRatings(List<RatingModel> userRatings) {
		this.userRatings = userRatings;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
