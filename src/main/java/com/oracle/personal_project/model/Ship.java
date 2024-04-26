package com.oracle.personal_project.model;

import lombok.Data;
import java.util.Date;

@Data
public class Ship {  //수주
	private String ship_code;   // 수주번호
	private String 	p_code;		// 제품코드
	private String 	c_code;		// 거래처코드
	private float 	ship_num;	// 수주수량
	private String  ship_date;	// 수주일자
	private String  ship_deli;	// 납품일자
	private int		ins_emp;	// 등록자
	private String  ins_date;	// 등록일자
	private int 	up_emp;		// 수정자
	private String  up_date;	// 수정일자
	
	// 조회용
	private String 	pageNum;			// 페이징번호
	private int 	start;				// 페이징 시작번호
	private int 	end;				// 페이징 종료번호
	private String  e_name;				// 담당자명
	private String  s_name;				// 소분류 이름
	private String  p_name;  			// 제품명
	private String  e_code;  			// 담당자코드
	private String  c_name;  			// 거래처명
	private String  date_from; 			// 수주시작일자
	private String  date_to;  			// 수주종료일자
	private float  adjusted_ship_num;  	// 남은 수주수량
	
}
