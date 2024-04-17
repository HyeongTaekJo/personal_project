package com.oracle.personal_project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.personal_project.Dao.ProductDao;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Small_code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class productServiceImpl implements ProductService {
	private final ProductDao pd;

	// 제품수량
	@Override
	public int productCnt(Product product) {
		System.out.println("제품 수량 서비스");
		int result = pd.productCnt(product);
		return result;
	}

	// 제품 리스트
	@Override
	public List<Product> productList(Product product) {
		System.out.println("제품 리스트 서비스");
		List<Product> result = pd.productList(product);
		return result;
	}
	
	//대분류 리스트
	@Override
	public List<Big_code> bigList() {
		System.out.println("대분류 리스트 서비스");
		List<Big_code> result = pd.bigList();
		return result;
	}

	//소분류 리스트
	@Override
	public List<Small_code> smallList() {
		System.out.println("소분류 리스트 서비스");
		List<Small_code> result = pd.smallList();
		return result;
	}

	// 삭제
	@Override
	public int deleteProduct(String p_code) {
		System.out.println("소분류 리스트 서비스");
		int result = pd.deleteProduct(p_code);
		return result;
	}
	
	
	
	
}
