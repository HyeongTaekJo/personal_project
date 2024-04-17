package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Work_order { 	//작업지시
	private String wo_code;		//작업지시번호
	private String plan_code;	//생산계획번호
	private String mc_code;		//설비코드
	private String wc_code;		//작업장코드
	private int    wo_status;	//작업상태
	private String wo_date;		//지시일자
	private int    ins_emp;		//등록자
	private String ins_date;	//등록일자
	private int    up_emp;		//수정자
	private String up_date;		//수정일자
}
