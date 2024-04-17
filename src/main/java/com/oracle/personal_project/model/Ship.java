package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Ship {  //수주
	private String ship_code;   // 수주번호
	private int 	p_code;		// 제품코드
	private int 	c_code;		// 거래처코드
	private int 	d_code;		// 부서코드
	private float 	ship_num;	// 수주수량
	private String  ship_date;	// 수주일자
	private String  ship_deli;	// 납품일자
	private int		ins_emp;	// 등록자
	private String  ins_date;	// 등록일자
	private int 	up_emp;		// 수정자
	private String  up_date;	// 수정일자
}
