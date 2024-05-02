package com.oracle.personal_project.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.personal_project.model.Defect;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DefectDaoImpl implements DefectDao {
	private final SqlSession session;
	
	//불량 총수량
	@Override
	public int totalDefect() {
		System.out.println("불량 총수량  다오");
		int result = 0;
		
		try {
			result = session.selectOne("totalDefect");
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//불량 1순위
	@Override
	public Defect firstDefect() {
		System.out.println("불량 1순위  다오");
		Defect result = null;
		
		try {
			result = session.selectOne("firstDefect");
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//불량 2순위
	@Override
	public Defect secondDefect() {
		System.out.println("불량 2순위  다오");
		Defect result = null;
		
		try {
			result = session.selectOne("secondDefect");
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//불량 3순위
	@Override
	public Defect thirdDefect() {
		System.out.println("불량 3순위  다오");
		Defect result = null;
		
		try {
			result = session.selectOne("thirdDefect");
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//불량 4순위
	@Override
	public Defect forthDefect() {
		System.out.println("불량 4순위  다오");
		Defect result = null;
		
		try {
			result = session.selectOne("forthDefect");
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

}
