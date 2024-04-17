package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Product {	//제품
	private String p_code;		//제품코드
	private String p_name;  //제품명
	private String b_code;  //대분류 코드
	private String s_code;  //소분류 코드
	private int p_cost; 	// 제품가격
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  b_name;				// 대분류 이름
	private String  s_name;				// 소분류 이름
}
