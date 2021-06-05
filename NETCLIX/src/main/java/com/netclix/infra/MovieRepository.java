package com.netclix.infra;

import com.netclix.model.MovieDetail;
import com.netclix.model.MovieList;
import com.netclix.model.MovieSearch;

public interface MovieRepository {
	MovieList findCurrentMovieList();
	
	MovieList findSearchMovieList(MovieSearch dto);
	
	MovieDetail findDetailByMovieCode(String code);
}
