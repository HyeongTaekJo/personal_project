package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Customer { // 거래처
	private String c_code;		//거래처 코드
	private String c_name;	// 거래처명
	private	String c_tel;		// 연락처
	private String c_email;	// 이메일
	private String e_code;		// 담당자
	private String s_code;  // 거래처 구분
	
	
	// 조회용
		private String 	pageNum;			// 페이징번호
		private int 	start;				// 페이징 시작번호
		private int 	end;				// 페이징 종료번호
		private String  e_name;				// 대분류 이름
		private String  s_name;				// 소분류 이름
		private String p_name;  //제품명
		
}
