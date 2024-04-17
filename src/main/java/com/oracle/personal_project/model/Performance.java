package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Performance { // 실적
	private String pm_code; 	// 실적코드
	private String wo_code;		// 작업지시번호
	private float qty;			// 양품수량
	private String ins_date;	// 등록일자
	private int p_code;			// 제품코드
	private int ins_emp;		// 등록자
	private int up_emp;			// 수정자
	private String up_date;		// 수정일자
}
