package com.netclix.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netclix.infra.MovieRepository;
import com.netclix.model.MovieDetail;
import com.netclix.model.MovieList;
import com.netclix.model.MovieSearch;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/movie")
@AllArgsConstructor
public class MovieSearchApi {
	private MovieRepository movieRepository;
	
	@GetMapping("current")
	public ResponseEntity<MovieList> findCurrentMovieList(){
		MovieList findCurrentMovieList = movieRepository.findCurrentMovieList();
		return new ResponseEntity<>(findCurrentMovieList, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<MovieList> findSearchMovieList(MovieSearch dto){
		MovieList findSearchMovieList = movieRepository.findSearchMovieList(dto);
		return new ResponseEntity<>(findSearchMovieList, HttpStatus.OK);
	}
	
	@GetMapping("detail/{code}")
	public ResponseEntity<MovieDetail> findDetailByCode(@PathVariable String code){
		MovieDetail findDetailByMovieCode = movieRepository.findDetailByMovieCode(code);
		return new ResponseEntity<>(findDetailByMovieCode, HttpStatus.OK);
	}
}
