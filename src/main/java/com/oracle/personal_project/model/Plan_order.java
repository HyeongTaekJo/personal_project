package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Plan_order {
	private String plan_code;
	private String ship_code;
	private String p_code;
	private String c_code;
	private float  qty;
	private String plan_date;
	private int    ins_emp;
	private String ins_date;
	private int    up_emp;
	private String up_date;
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  p_name;  			// 제품명
	private String  c_name;  			// 거래처명
	private String  e_code;  			// 담당자코드
	private String  e_name;				// 담당자명
	private String  date_from; 			// 수주시작일자
	private String  date_to;  			// 수주종료일자
	private String  ship_deli;			// 납품일자
	private float 	ship_num;			// 수주수량
	private float  adjusted_plan_num;  	// 남은 수주수량
}	
