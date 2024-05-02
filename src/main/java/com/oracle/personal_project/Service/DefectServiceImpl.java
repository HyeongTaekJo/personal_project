package com.oracle.personal_project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.personal_project.Dao.DefectDao;
import com.oracle.personal_project.model.Defect;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {
	private final DefectDao dd;
	
	//불량 총수량
	@Override
	public int totalDefect() {
		System.out.println("불량 총수량 서비스");
		int result = dd.totalDefect();
		return result;
	}

	//불량 1순위
	@Override
	public Defect firstDefect() {
		System.out.println("불량 1순위 서비스");
		Defect result = dd.firstDefect();
		return result;
	}

	//불량 2순위
	@Override
	public Defect secondDefect() {
		System.out.println("불량 2순위 서비스");
		Defect result = dd.secondDefect();
		return result;
	}

	//불량 3순위
	@Override
	public Defect thirdDefect() {
		System.out.println("불량 3순위 서비스");
		Defect result = dd.thirdDefect();
		return result;
	}

	//불량 4순위
	@Override
	public Defect forthDefect() {
		System.out.println("불량 4순위 서비스");
		Defect result = dd.forthDefect();
		return result;
	}

}
