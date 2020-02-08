package com.paulgeorge.imdb;

import java.util.List;

public class Actor {
	private String name;
	private List<String> movies;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getMovies() {
		return movies;
	}
	public void setMovies(List<String> movies) {
		this.movies = movies;
	}

	
}
