package com.sist.model;

import javax.servlet.http.HttpServletRequest;
/*
 * {
      movieId: "001",
      movieTitle: "Ender's Game",
      movieDirector: "Gavin Hood",
      movieImage: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-endersgame.jpg"
	}
 * */
import java.util.*;
import com.sist.dao.*;

public class MovieList implements Model{

	@Override
	public String execute(HttpServletRequest req) throws Exception {
		try {
		String page = req.getParameter("page");
		if(page==null)
			page="1";
		int curpage = Integer.parseInt(page);
		MovieDAO dao = new MovieDAO();
		List<MovieVO> list = dao.movieListData(curpage);
		String json = "[";
		for(MovieVO vo : list) {
			json += "{ movieId: \"" + vo.getMno() + "\", "
					+"movieTitle: \"" + vo.getTitle() + "\", "
					+"movieDirector: \"" + vo.getDirector() + "\", "
					+ "movieImage: \"" + vo.getPoster() + "\"}, ";
		}
		json = json.substring(0, json.lastIndexOf(",")); //맨 마지막 콤마는 없애기위해
		json += "]";
		req.setAttribute("json", json);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return "movie/movie_list.jsp";
	}

}
