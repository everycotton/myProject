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
		System.out.println("����Ϸ�!");
	}

	// http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date=20171205
	/*
	 * <td class="title"> <div class="tit5"> <a
	 * href="/movie/bi/mi/basic.nhn?code=10200" title="�͹̳����� 2">�͹̳����� 2</a> </div>
	 * </td> ��ũ �о����
	 */
	public List<String> movieLinkData() {
		List<String> list = new ArrayList<String>();
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			for (int i = 1; i <= 40; i++) {
				Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date="
						+ sdf.format(date) + "&page=" + i).get(); // �ҽ�����
				Elements elem = doc.select("td.title div.tit5 a"); // Elements: ������������ ���� ���� �о�� a�±� 50�� �о��
				for (int j = 0; j < elem.size(); j++) {
					Element code = elem.get(j);
					list.add("http://movie.naver.com" + code.attr("href")); // href�� ����
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
					String link = linkList.get(i); // �������� ��ũ ���ڿ��� ����
					Document doc = Jsoup.connect(link).get(); // ����!
					Element title = doc.select("div.mv_info h3.h_movie a").first(); // Element :���������� �ѳ����� ������
					//System.out.println((i + 1) + ":" + title.text()); // text=>tag��tag����
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
					s = s.replace("'", ""); //db�� ���� �� �۵�, ū�� ����! 
					s = s.replace("\"", "");
					vo.setStory(s);
					System.out.println((i+1)+" title : "+title.text());
					list.add(vo);
					dao.movieInsert(vo);
					
				} catch (Exception ex) {} //for���ȿ��� try-catch�ɾ���� 19�ݿ� �ɸ� �������� ���� �ű⼭ �������ʰ� ���� ������ �ܾ��
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}
}
