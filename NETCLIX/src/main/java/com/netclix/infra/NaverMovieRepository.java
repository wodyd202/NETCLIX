package com.netclix.infra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import com.netclix.model.MovieDetail;
import com.netclix.model.MovieList;
import com.netclix.model.MovieSearch;
import com.netclix.model.MovieList.MovieListData;

@Repository
public class NaverMovieRepository implements MovieRepository {

	@Override
	public MovieList findCurrentMovieList() {
		List<MovieListData> movies = new ArrayList<>();

		try {
			Document doc = Jsoup
					.connect("https://movie.naver.com/movie/running/current.nhn?view=list&tab=normal&order=point")
					.get();
			Elements element = doc.select("ul.lst_detail_t1");
			Iterator<Element> aTag = element.select("li > div > a").iterator();
			Iterator<Element> imgTag = element.select("li > div > a > img").iterator();
			Iterator<Element> strongTag = element.select("li > dl > dt > a").iterator();
			// 평점
			Iterator<Element> starTag = element.select(".num").iterator();

			while (aTag.hasNext()) {
				String href = aTag.next().attr("href");
				int codeIdx = href.indexOf("code") + 5;
				String code = href.substring(codeIdx);
				String movieTitle = strongTag.next().text();
				String movieImage = imgTag.next().attr("src");
				movies.add(new MovieListData(code, movieTitle, movieImage,Double.parseDouble(starTag.next().text())));
			}
			return new MovieList(movies, movies.size());
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public MovieList findSearchMovieList(MovieSearch dto) {
		List<MovieListData> movies = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(
					"https://movie.naver.com/movie/search/result.nhn?query=" + dto.getTitle() + "&section=all&ie=utf8")
					.get();
			Elements element = doc.select("ul.search_list_1");
			Iterator<Element> aTag = element.select("li > p > a").iterator();
			Iterator<Element> imgTag = element.select("li > p > a > img").iterator();
			Iterator<Element> strongTag = element.select("li > dl > dt > a").iterator();

			while(aTag.hasNext()) {
				String href = aTag.next().attr("href");
				int codeIdx = href.indexOf("code") + 5;
				String code = href.substring(codeIdx);
				String movieTitle = strongTag.next().text();
				String movieImage = imgTag.next().attr("src");
				movies.add(new MovieListData(code, movieTitle, movieImage, null));
			}
			
			return new MovieList(movies, movies.size());
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public MovieDetail findDetailByMovieCode(String code) {
		try {
			Document doc = Jsoup.connect(
					"https://movie.naver.com/movie/bi/mi/basic.nhn?code=" + code)
					.get();
			Element select = doc.select(".mv_info .info_spec > span").first();
			
			String mainImage = doc.select(".poster a img").attr("src");
			String category = select.text();
			String star = doc.select("#actualPointPersentWide div span span").text();
			String mainContent = doc.select(".h_tx_story").text();
			String subContent = doc.select(".story_area .con_tx").text();

			doc = Jsoup.connect(
					"https://movie.naver.com/movie/bi/mi/photoView.nhn?code=" + code)
					.get();
			
			List<String> subImages = doc.select(".rolling_list ul li a img").stream().map(img-> {
				return new String(img.attr("src"));
			}).collect(Collectors.toList());
			
			return MovieDetail
						.builder()
						.code(code)
						.mainImage(mainImage)
						.category(category)
						.star(star)
						.mainContent(mainContent)
						.subContent(subContent)
						.subImages(subImages)
						.build();
		}catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

}
