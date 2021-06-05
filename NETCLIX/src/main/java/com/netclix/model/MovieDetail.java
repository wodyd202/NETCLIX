package com.netclix.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetail {
	private String code;
	private String mainImage;
	private String title;
	private String mainContent;
	private String subContent;
	private String category;
	private String star;
	
	private List<String> subImages;
}
