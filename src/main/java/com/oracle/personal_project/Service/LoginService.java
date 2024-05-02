package com.oracle.personal_project.Service;

import java.util.List;

import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;

public interface LoginService {

	Emp empChk(String chk_Id);

	Emp checkDuplicate(String chk_id);

	List<Dept> deptList();

	Emp login(Emp emp1);

	int signInsert(Emp emp);



}
