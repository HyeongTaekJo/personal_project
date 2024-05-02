package com.oracle.personal_project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.personal_project.Dao.LoginDao;
import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	private final LoginDao ld;
	
	// 로그인
	@Override
	public Emp empChk(String chk_Id) {
		System.out.println("로그인검사 서비스 시작");
		Emp result = ld.empChk(chk_Id);
		return result;
	}
	
	//회원가입 아이디 중복검사
	@Override
	public Emp checkDuplicate(String chk_id) {
		System.out.println("아이디 중복검사 시작");
		Emp result = ld.checkDuplicate(chk_id);
		return result;
	}

	@Override
	public List<Dept> deptList() {
		System.out.println("부서 리스트 시작");
		List<Dept> list = ld.deptList();
		return list;
	}

	//로그인 검사
	@Override
	public Emp login(Emp emp1) {
		System.out.println("login Start");
		emp1 = ld.login(emp1);
		
		return emp1;
	}

	@Override
	public int signInsert(Emp emp) {
		System.out.println("아이디 중복검사 시작");
		int result = ld.signInsert(emp);
		return result;
	}

}
