package com.oracle.personal_project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.personal_project.Dao.ProductDao;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Ship;
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
		System.out.println("제품 삭제서비스");
		int result = pd.deleteProduct(p_code);
		return result;
	}

	//신규 제품 추가
	@Override
	public String newProductInsert(List<Product> dataList) {
		System.out.println("신규 제품 추가  서비스");
		String result = pd.newProductInsert(dataList);
		return result;
	}

	//거래처 수량
	@Override
	public int customerCnt(Customer customer) {
		System.out.println("거래처 수량  서비스");
		int result = pd.customerCnt(customer);
		return result;
	}

	//거래처 리스트
	@Override
	public List<Customer> customerList(Customer customer) {
		System.out.println("거래처 리스트  서비스");
		List<Customer> result = pd.customerList(customer);
		return result;
	}

	//담당자 리스트
	@Override
	public List<Emp> empList() {
		System.out.println("담당자 리스트 서비스");
		List<Emp> result = pd.empList();
		return result;
	}

	//거래처 구분
	@Override
	public List<Small_code> customerSmallList() {
		System.out.println("거래처구분 리스트 서비스");
		List<Small_code> result = pd.customerSmallList();
		return result;
	}

	//신규 거래처 추가
	@Override
	public int newCustomertInsert(List<Customer> customertList) {
		System.out.println("신규 제품 추가  서비스");
		int result = pd.newCustomerInsert(customertList);
		return result;
	}

	//거래처 삭제
	@Override
	public int deleteCustomer(String c_code) {
		System.out.println("소분류 리스트 서비스");
		int result = pd.deleteCustomer(c_code);
		return result;
	}

	//수주 리스트
	@Override
	public List<Ship> shipList(Ship ship) {
		System.out.println("거래처구분 리스트 서비스");
		List<Ship> result = pd.shipList(ship);
		return result;
	}

	//제품 리스트
	@Override
	public List<Product> productList() {
		System.out.println("거래처구분 리스트 서비스");
		List<Product> result = pd.productList();
		return result;
	}


	//수주 수량
	@Override
	public int shipCnt(Ship ship) {
		System.out.println("수주 수량  서비스");
		int result = pd.shipCnt(ship);
		return result;
	}

	//거래처 리스트
	@Override
	public List<Customer> customerList() {
		System.out.println("거래처 리스트 서비스");
		List<Customer> result = pd.customerList();
		return result;
	}

	//신규 수주추가
	@Override
	public int newShiptInsert(List<Ship> shipList) {
		System.out.println("신규 제품 추가  서비스");
		int result = pd.newShiptInsert(shipList);
		return result;
	}

	//수주삭제
	@Override
	public int deleteShip(String ship_code) {
		System.out.println("수주삭제 서비스");
		int result = pd.deleteShip(ship_code);
		return result;
	}

	// 생산계획수량
	@Override
	public int planOrderCnt(Plan_order planOrder) {
		System.out.println("생산계획수량  서비스");
		int result = pd.planOrderCnt(planOrder);
		return result;
	}

	// 생산계획 리스트
	@Override
	public List<Plan_order> planOrderList(Plan_order planOrder) {
		System.out.println("생산계획 리스트  서비스");
		List<Plan_order> result = pd.planOrderList(planOrder);
		return result;
	}

	//모달 수주리스트
	@Override
	public List<Ship> shipList() {
		System.out.println("생산계획 리스트  서비스");
		List<Ship> result = pd.shipList();
		return result;
	}

	//생산계획 추가
	@Override
	public int newPlanInsert(List<Plan_order> planList) {
		System.out.println("신규 생산계획 추가  서비스");
		int result = pd.newPlanInsert(planList);
		return result;
	}

	//생산계획 삭제
	@Override
	public int deletePlan(String plan_code) {
		System.out.println("생산계획 서비스");
		int result = pd.deletePlan(plan_code);
		return result;
	}

	
	
	
	
	
}
