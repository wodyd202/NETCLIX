package com.netclix.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netclix.model.MovieList;
import com.netclix.service.MovieService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/movie")
@AllArgsConstructor
public class MovieSearchApi {
	private MovieService movieService;
	
	@GetMapping("current")
	public ResponseEntity<MovieList> findCurrentMovieList(){
		MovieList findCurrentMovieList = movieService.findCurrentMovieList();
		return new ResponseEntity<>(findCurrentMovieList, HttpStatus.OK);
	}
}
