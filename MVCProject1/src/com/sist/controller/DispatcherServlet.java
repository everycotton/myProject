package com.sist.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.Model;

import java.util.*; //Map에 저장(요청=>클래스(모델) 매칭)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] strCls = {
		"com.sist.model.MovieList",
		"com.sist.model.MovieDetail"
	};
	private String[] strCmd = {
		"list","detail"	
	};
	private Map clsMap = new HashMap();
	// HashMap, Hashtable
	public void init(ServletConfig config) throws ServletException { //한번만 호출
		try {
			for(int i = 0; i < strCls.length; i++) {
				Class clsName = Class.forName(strCls[i]);
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cmd = request.getRequestURI();
			//URI : 사용자가 주소입력란에 요청한 파일
			//http://localhost:8080/MVCProject1/list.do
			// URI : /MVCProject1/list.do
			//       =============
			//		 contextPath()
			//사용자가 요청한 내용 가져옴
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf(".")); //list만 가져옴
			//요청을 처리 => 모델클래스(클래스,메소드) 필요 => 찾기
			Model model = (Model)clsMap.get(cmd);
			//model => 실행을 한 후에 결과값을 request에 담아라
			//call by reference => 주소를 넘겨주고 주소에 값을 채운다
			String jsp = model.execute(request);
			//jsp에 request, session값을 전송
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			//jsp의 _jspService()를 호출한다.
			/*
			 * service(request, response){
			 * 		a_jsp._jspService(request)
			 * }
			 */
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
