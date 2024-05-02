package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Performance {	 // 실적
	private String pm_code; 	// 실적코드
	private String wo_code;		// 작업지시번호
	private float qty;			// 양품수량
	private String pm_date;		// 등록일자
	private String p_code;			// 제품코드
	private String c_code;			// 거래처
	private String e_code;			// 담장자
	private String ins_date;	// 등록일자
	private int ins_emp;		// 등록자
	private int up_emp;			// 수정자
	private String up_date;		// 수정일자
	
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  date_from; 			// 지시시작일자
	private String  date_to;  			// 지시종료일자
	private String  p_name;  			// 제품명
	private String  c_name;  			// 거래처명
	private String  e_name;  			// 등록자명
	private float   order_qty;			// 양품수량
	
}
