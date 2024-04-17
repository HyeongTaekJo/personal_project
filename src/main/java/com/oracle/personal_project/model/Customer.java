package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Customer { // 거래처
	private int c_code;		//거래처 코드
	private String c_name;	// 거래처명
	private	int c_tel;		// 연락처
	private String c_email;	// 이메일
	private int e_code;		// 담당자
}
