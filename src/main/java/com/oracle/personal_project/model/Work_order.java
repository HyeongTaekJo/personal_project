package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Work_order { 	//작업지시
	private String wo_code;		//작업지시번호
	private String plan_code;	//생산계획번호
	private String mc_code;		//설비코드
	private String wc_code;		//작업장코드
	private String wo_status;	//작업상태
	private String wo_date;		//지시일자
	private float  qty;			//지시수량
	private int    ins_emp;		//등록자
	private String ins_date;	//등록일자
	private int    up_emp;		//수정자
	private String up_date;		//수정일자
	
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  date_from; 			// 지시시작일자
	private String  date_to;  			// 지시종료일자
	private String  ship_deli;			// 납품일자
	private float 	ship_num;			// 수주수량
	private String 	wc_name;			// 수주수량
	private String 	mc_name;			// 수주수량
	private String 	s_name;				// 작업상태
	private float   plan_qty;			// 생산계획수량
}
