package com.oracle.personal_project.Dao;

import java.util.List;

import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;

public interface LoginDao {

	Emp empChk(String chk_Id);

	Emp checkDuplicate(String chk_id);

	List<Dept> deptList();

	
}
