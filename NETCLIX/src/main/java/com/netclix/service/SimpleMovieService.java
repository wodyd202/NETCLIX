package com.netclix.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.netclix.model.MovieList;
import com.netclix.model.MovieList.MovieListData;

@Service
public class SimpleMovieService implements MovieService {
	private final String MOVIE_LIST_URL = "https://movie.naver.com/movie/running/current.nhn?view=image&tab=normal&order=reserve";
	private final String MOVIE_LIST_ROOT_TAG = "ul.lst_default_t1";
	private final String MOVIE_LIST_IMAGE_TAG = "img";
	private final String MOVIE_LIST_TITLE_TAG = "strong";
	private final String MOVIE_LIST_CODE_TAG = "a";
	
	@Override
	public MovieList findCurrentMovieList() {
		List<MovieListData> movies = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(MOVIE_LIST_URL).get();
			Elements element = doc.select(MOVIE_LIST_ROOT_TAG);
			
			Iterator<Element> aTag = element.select(MOVIE_LIST_CODE_TAG).iterator();
			Iterator<Element> imgTag = element.select(MOVIE_LIST_IMAGE_TAG).iterator();
			Iterator<Element> strongTag = element.select(MOVIE_LIST_TITLE_TAG).iterator();
			
			while(aTag.hasNext()) {
				String href = aTag.next().attr("href");
				int codeIdx = href.indexOf("code") + 5;
				String code = href.substring(codeIdx);
				movies.add(new MovieListData(code, strongTag.next().text(), imgTag.next().attr("src")));
			}
			return new MovieList(movies, movies.size());
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

}
