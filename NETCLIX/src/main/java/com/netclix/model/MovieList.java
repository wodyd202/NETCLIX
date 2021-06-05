package com.netclix.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieList {
	private List<MovieListData> movies;
	private long totalCount;
	
	@Getter
	@AllArgsConstructor
	public static class MovieListData {
		private String code;
		private String title;
		private String image;
	}
}
