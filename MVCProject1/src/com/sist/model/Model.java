package com.sist.model;

import javax.servlet.http.HttpServletRequest;
//클래스 여러개 모아서 
/*
 * 
 * 상속
 * 	class / interface
 * 		 extends
 *  class =====> class
 *  		 implements
 *  interface ====> interface
 *  		 implements
 *  interface ====> class
 *   	  (X불가능)
 *  class ====> interface
 */
public interface Model {
	public String execute(HttpServletRequest req) throws Exception;
	//default를 붙이면 구현가능  1.8이상 (+static메소드)
	
}
