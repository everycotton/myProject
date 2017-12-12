package com.sist.manager;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.MovieDAO;
import com.sist.dao.MovieVO;

import java.text.*;

public class MovieManager {
	public static void main(String[] args) {
		MovieManager m = new MovieManager();
		// m.movieLinkData();
		m.movieDetailData();
		System.out.println("저장완료!");
	}

	// http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date=20171205
	/*
	 * <td class="title"> <div class="tit5"> <a
	 * href="/movie/bi/mi/basic.nhn?code=10200" title="터미네이터 2">터미네이터 2</a> </div>
	 * </td> 링크 읽어오기
	 */
	public List<String> movieLinkData() {
		List<String> list = new ArrayList<String>();
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			for (int i = 1; i <= 40; i++) {
				Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date="
						+ sdf.format(date) + "&page=" + i).get(); // 소스보기
				Elements elem = doc.select("td.title div.tit5 a"); // Elements: 한페이지에서 여러 내용 읽어옴 a태그 50개 읽어옴
				for (int j = 0; j < elem.size(); j++) {
					Element code = elem.get(j);
					list.add("http://movie.naver.com" + code.attr("href")); // href값 저장
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	public List<MovieVO> movieDetailData() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieDAO dao = new MovieDAO();
		try {
			List<String> linkList = movieLinkData();
			for (int i = 0; i < linkList.size(); i++) {
				try {
					String link = linkList.get(i); // 상세페이지 링크 문자열로 저장
					Document doc = Jsoup.connect(link).get(); // 연결!
					Element title = doc.select("div.mv_info h3.h_movie a").first(); // Element :한페이지에 한내용을 가져옴
					//System.out.println((i + 1) + ":" + title.text()); // text=>tag와tag사이
					Element director = doc.selectFirst("div.info_spec2 dl.step1 dd a");
					Element actor = doc.selectFirst("div.info_spec2 dl.step2 dd a");
					Elements temp = doc.select("p.info_spec span");
					Element genre = temp.get(0);
					Element time = temp.get(2);
					Element regdate = temp.get(3);
					Element grade = temp.get(4);
					Element poster = doc.selectFirst("div.poster a img");
					Element story = doc.selectFirst("div.story_area p.con_tx");
					/*System.out.println((i + 1) + ":" + title.text()+"=="+director.text() + "==" + actor.text()
					+"=="+genre.text()+"=="+time.text()+"=="+regdate.text()+"=="+grade.text()+"=="+poster.attr("src")
					+"=="+story.text()
					); */
					 
					MovieVO vo = new MovieVO();
					vo.setMno(i+1);
					vo.setTitle(title.text());
					vo.setDirector(director.text());
					vo.setActor(actor.text());
					vo.setGenre(genre.text());
					vo.setTime(time.text());
					vo.setRegdate(regdate.text());
					vo.setGrade(grade.text());
					vo.setPoster(poster.attr("src"));
					String s = story.text();
					s = s.replace("'", ""); //db에 넣을 때 작따, 큰따 문제! 
					s = s.replace("\"", "");
					vo.setStory(s);
					System.out.println((i+1)+" title : "+title.text());
					list.add(vo);
					dao.movieInsert(vo);
					
				} catch (Exception ex) {} //for문안에서 try-catch걸어줘야 19금에 걸린 페이지에 들어가도 거기서 멈추지않고 다음 페이지 긁어옴
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}
}
