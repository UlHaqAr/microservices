package com.arsal.microservice3.restcontroller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsal.microservice3.models.RatingModel;
import com.arsal.microservice3.models.UserRatingsModel;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

	@RequestMapping("/{movieId}")
	public RatingModel getRating(@PathVariable("movieId") String movieId)
	{
		return new RatingModel(movieId, 12345);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRatingsModel getUserRating(@PathVariable("userId") String userId)
	{
		List<RatingModel> userList = Arrays.asList(
				new RatingModel("hardcodeMovieId1", 1234), 
				new RatingModel("hardcodeMovieId2", 5678)
			);
		UserRatingsModel urm = new UserRatingsModel(userList, userId);
		return urm;
	}
}
