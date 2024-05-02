package com.oracle.personal_project.model;

import java.util.List;

import lombok.Data;

@Data
public class Product {	//제품
	private String p_code;		//제품코드
	private String p_name;  //제품명
	private String b_code;  //대분류 코드
	private String s_code;  //소분류 코드
	private String p_cost; 	// 제품가격
	
	//private List<Product> productList;
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  b_name;				// 대분류 이름
	private String  s_name;				// 소분류 이름
	
	private float 	totalOrderPrice;	 // 이번달 총 발주금액
	private float 	lastTotalOrderPrice; // 저번달 총 발주금액
	
	private float 	totalIncomePrice;	 // 이번달 총 입고금액
	private float 	lastTotalIncomePrice; // 저번달 총 입고금액
	
	private float 	totalDefectPrice;	 // 이번달 총 불량금액
	private float 	lastTotalDefectPrice; // 저번달 총 불량금액
}
