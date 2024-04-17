package com.oracle.personal_project.model;

import lombok.Data;

@Data
public class Plan_order {
	private String plan_code;
	private String ship_code;
	private int    d_code;
	private int    p_code;
	private int    c_code;
	private int    ins_emp;
	private float  qty;
	private String plan_date;
	private String plan_deli;
	private String ins_date;
	private int    up_emp;
	private String up_date;
}	
