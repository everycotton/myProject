package com.sist.model;

import javax.servlet.http.HttpServletRequest;
//Ŭ���� ������ ��Ƽ� 
/*
 * 
 * ���
 * 	class / interface
 * 		 extends
 *  class =====> class
 *  		 implements
 *  interface ====> interface
 *  		 implements
 *  interface ====> class
 *   	  (X�Ұ���)
 *  class ====> interface
 */
public interface Model {
	public String execute(HttpServletRequest req) throws Exception;
	//default�� ���̸� ��������  1.8�̻� (+static�޼ҵ�)
	
}
