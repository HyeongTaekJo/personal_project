package com.oracle.personal_project.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Small_code;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
	private final SqlSession session;

	//제품수량
	@Override
	public int productCnt(Product product) {
		System.out.println("제품수량  다오");
		int result = 0;
		
		try {
			result = session.selectOne("productCnt", product);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//제품 리스트
	@Override
	public List<Product> productList(Product product) {
		System.out.println("제품 리스트  다오");
		List<Product> list = null;
		
		try {
			list = session.selectList("productList", product);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//대분류 리스트
	@Override
	public List<Big_code> bigList() {
		System.out.println("대분류 리스트  다오");
		List<Big_code> list = null;
		
		try {
			list = session.selectList("bigList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//소분류 리스트
	@Override
	public List<Small_code> smallList() {
		System.out.println("소분류 리스트  다오");
		List<Small_code> list = null;
		
		try {
			list = session.selectList("smallList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//삭제
	@Override
	public int deleteProduct(String p_code) {
		System.out.println("삭제  다오");
		List<String> shipList = null;
		int result = 0;
		try {
			shipList = session.selectList("findShip", p_code);
			System.out.println("shipList--> " + shipList);
			if(!shipList.isEmpty()) {
				result = 2;
				return result;
			} else {
				result = session.delete("deleteProduct", p_code);
				System.out.println("deleteProduct result--> " + result);
			}
			
		}catch (Exception e) {
			System.out.println("deleteProduct Exception--> " + e.getMessage());
		}
		return result;
	}

}
