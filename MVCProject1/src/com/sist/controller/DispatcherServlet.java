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

import java.util.*; //Map�� ����(��û=>Ŭ����(��) ��Ī)
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
	public void init(ServletConfig config) throws ServletException { //�ѹ��� ȣ��
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
			//URI : ����ڰ� �ּ��Է¶��� ��û�� ����
			//http://localhost:8080/MVCProject1/list.do
			// URI : /MVCProject1/list.do
			//       =============
			//		 contextPath()
			//����ڰ� ��û�� ���� ������
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf(".")); //list�� ������
			//��û�� ó�� => ��Ŭ����(Ŭ����,�޼ҵ�) �ʿ� => ã��
			Model model = (Model)clsMap.get(cmd);
			//model => ������ �� �Ŀ� ������� request�� ��ƶ�
			//call by reference => �ּҸ� �Ѱ��ְ� �ּҿ� ���� ä���
			String jsp = model.execute(request);
			//jsp�� request, session���� ����
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			//jsp�� _jspService()�� ȣ���Ѵ�.
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
