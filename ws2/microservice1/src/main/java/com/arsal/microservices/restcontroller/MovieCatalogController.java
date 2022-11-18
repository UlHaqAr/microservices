package com.arsal.microservices.restcontroller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.arsal.microservices.models.CatalogModel;
import com.arsal.microservices.models.MovieModel;
import com.arsal.microservices.models.RatingModel;
import com.arsal.microservices.models.UserRatingsModel;

@RestController
@RequestMapping("/catalogs")
public class MovieCatalogController {

	@Autowired
	RestTemplate restTemplate;
	
	//'reactive-web' (alternative to using RestTemplate) 
	@Autowired
	WebClient.Builder webClient;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
//	@GetMapping("/{userId}")
	@RequestMapping("/{userId}")
	public List<CatalogModel> getCatalogsForUser(@PathVariable("userId") String userId)
	{
		
		// 1. for the user id , get all the movie ids with ratings
		
//		List<RatingModel> userList = Arrays.asList(
//			new RatingModel("movieid1", 2), 
//			new RatingModel("movieid2", 5)
//		);
		
//		if microservice 'ratingdata' has multiple instances running on different ports, we can get list of them.
//		discoveryClient.getInstances("ratingdata");
		
		UserRatingsModel userList = restTemplate.getForObject("http://ratingdata/ratings/users/" + userId, UserRatingsModel.class);
		
		// 2. for each movie id, get the info - name, desc
		
//		hard-coding
//		List<CatalogModel> resultList = userList.stream()
//				.map(rating -> new CatalogModel(rating.getMovieId(), rating.getRating(), "soem desc"))
//				.collect(Collectors.toList());
		
//		RestTemplate restTemplate = new RestTemplate();
		
//		Rest template , old spring web 
//		List<CatalogModel> resultList = userList.stream()
//				.map(rating -> {
//					MovieModel mm = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), MovieModel.class);
////					new CatalogModel("someMovieName", rating.getRating(), "soem desc");
//					 return new CatalogModel(mm.getMovieName(), rating.getRating(), mm.getMovieDesc());
//				})
//				.collect(Collectors.toList());
		
//		new spring reactive web 
//		List<CatalogModel> resultList = userList.getUserRatings().stream()
//				.map(rating -> {
//					MovieModel mm = webClient.build()
//							.get()
//							.uri("http://movieinfo/movies/" + rating.getMovieId())
//							.retrieve()
//							.bodyToMono(MovieModel.class)
//							.block();
//					return new CatalogModel(mm.getMovieName(), rating.getRating(), mm.getMovieDesc());
//				})
//				.collect(Collectors.toList());
		
		List<CatalogModel> resultList = userList.getUserRatings().stream()
				.map(rating -> {
					MovieModel mm = restTemplate.getForObject("http://movieinfo/movies/" + rating.getMovieId(), MovieModel.class);
					 return new CatalogModel(mm.getMovieName(), rating.getRating(), mm.getMovieDesc());
				})
				.collect(Collectors.toList());
				
		
		// 3. form the result
		
		//hard-coding
		//return Collections.singletonList(new CatalogModel("movie1",3));
		return resultList;
	}
}
