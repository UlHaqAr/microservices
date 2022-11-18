package com.arsal.microservices.models;

public class MovieModel {

	String movieId;
	String movieName;
	String movieDesc;
	
	public MovieModel(String movieId, String movieName, String movieDesc) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieDesc = movieDesc;
	}
	
	//empty const needed by rest template during marshalling/unmarshall of response
	public MovieModel() {
		super();
	}
	
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieDesc() {
		return movieDesc;
	}
	public void setMovieDesc(String movieDesc) {
		this.movieDesc = movieDesc;
	}
	
	
}
