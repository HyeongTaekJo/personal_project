package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Emp {	//회원
	private int e_code;		// 회원코드
	private int d_code;		// 부서코드
	private String e_id;	// 회원ID
	private String e_name;	// 회원이름
	private String e_pw;	// 회원 PW
	private String e_ph;	// 회원 전화번호
	private String e_email; // 이메일
	private String e_date;  // 가입일
	private int e_admin; // 관리자여부
	private int e_wd;    // 탈퇴여부
	private String e_image; // 회원이미지
	private String chk;
}
