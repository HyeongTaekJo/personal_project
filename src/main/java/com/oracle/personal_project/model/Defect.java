package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Defect { // 불량
	private String df_code;		//불량코드
	private String wo_code;		//작업지시번호
	private float qty;			//불량수량
	private String fail_date;
	private String p_code;		//제품코드
	private String c_code;		//제품코드
	private String e_code;		//제품코드
	private String ins_date;	//등록일자
	private int ins_emp;		//등록자
	private int up_emp;			//수정자
	private String up_date;		//수정일자
	private String df_type;     //불량타팁
	
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
	private int  total_count;  			// 불량 총수량
	private int  rank;  			    // 불량 순위
	private int  randomrank;			// 
	private String s_name;				// 불량 유형이름
	
}
