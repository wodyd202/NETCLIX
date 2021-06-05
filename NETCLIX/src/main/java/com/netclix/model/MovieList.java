package com.netclix.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
		
		@JsonInclude(content = Include.NON_NULL)
		private Double star;
	}
}
