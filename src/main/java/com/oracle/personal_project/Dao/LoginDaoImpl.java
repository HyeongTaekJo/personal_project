package com.oracle.personal_project.Dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginDao {
	private final SqlSession session;
	private final HttpSession https; 

	//로그인
	@Override
	public Emp empChk(String chk_Id) {
		
		System.out.println("로그인 검사 다오");
		Emp result = null;
		System.out.println("chk_Id ==> " + chk_Id);
		try {
			result = session.selectOne("userLogin", chk_Id);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//회원가입 아이디 중복검사
	@Override
	public Emp checkDuplicate(String chk_Id) {
		System.out.println("아이디 중복 검사 다오");
		Emp result = null;
		try {
			result = session.selectOne("userLogin", chk_Id);
			if(result != null) {
				return result;
			}else {
				return result;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<Dept> deptList() {
		System.out.println("부서  리스트 검색");
		List<Dept> list = null;
		try {
			list = session.selectList("deptList");
			System.out.println("result--> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public Emp login(Emp emp1) {
		System.out.println("EmpDaoImpl login Start...");
		Emp emp = null;
		 
		try {
			emp = session.selectOne("ybUserLogin", emp1);
			System.out.println("MemberDaoImpl login member1 -> " + emp.getE_id());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(emp == null) {
			return null;
		} else {
			return emp;			
		}
	}

	@Override
	public int signInsert(Emp emp) {
		System.out.println("아이디 중복 검사 다오");
		int result = 0;
		try {
			result = session.insert("signInsert", emp);
			if(result != 0) {
				return result;
			}else {
				return result;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

//	@Override
//	public boolean checkDuplicate(String chk_Id) {
//		System.out.println("아이디 중복 검사 다오");
//		System.out.println(chk_Id);
//		Emp result = null;
//		try {
//			result = session.selectOne("checkDuplicate", chk_Id);
//			System.out.println("result--> " + result);
//			if(result != null) {
//				return true;
//			}else {
//				return false;
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//		return false;
//	}
	
}
