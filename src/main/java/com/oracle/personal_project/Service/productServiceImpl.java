package com.oracle.personal_project.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.personal_project.Dao.ProductDao;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Defect;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Machine;
import com.oracle.personal_project.model.Performance;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Ship;
import com.oracle.personal_project.model.Small_code;
import com.oracle.personal_project.model.Work_center;
import com.oracle.personal_project.model.Work_order;

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

	//지시수량
	@Override
	public int workOrderCnt(Work_order workOrder) {
		System.out.println("지시수량  서비스");
		int result = pd.workOrderCnt(workOrder);
		return result;
	}

	//지시리스트
	@Override
	public List<Work_order> workOrderList(Work_order workOrder) {
		System.out.println("지시리스트 서비스");
		List<Work_order> result = pd.workOrderList(workOrder);
		return result;
	}

	//설비 리스트
	@Override
	public List<Machine> MachineList() {
		System.out.println("설비 리스트  서비스");
		List<Machine> result = pd.MachineList();
		return result;
	}

	//작업장 리스트
	@Override
	public List<Work_center> WorkCenterList() {
		System.out.println("작업장 리스트");
		List<Work_center> result = pd.WorkCenterList();
		return result;
	}

	//작업상태 리스트
	@Override
	public List<Small_code> workStatusList() {
		System.out.println("작업상태 리스트");
		List<Small_code> result = pd.workStatusList();
		return result;
	}

	//모달 생산계획 리스트
	@Override
	public List<Plan_order> planOrderListModal() {
		System.out.println("생산계획 리스트  서비스");
		List<Plan_order> result = pd.planOrderListModal();
		return result;
	}

	//작업지시 등록
	@Override
	public int newWorkOrderInsert(List<Work_order> workOrderList) {
		System.out.println("신규 생산계획 추가  서비스");
		int result = pd.newWorkOrderInsert(workOrderList);
		return result;
	}

	//작업지시 삭제
	@Override
	public int deleteWorkOrder(String wo_code) {
		System.out.println("작업지시 서비스");
		int result = pd.deleteWorkOrder(wo_code);
		return result;
	}

	//실적수량
	@Override
	public int performanceCnt(Performance performance) {
		System.out.println("지시수량  서비스");
		int result = pd.performanceCnt(performance);
		return result;
	}

	//실적 리스트
	@Override
	public List<Performance> performanceList(Performance performance) {
		System.out.println("실적 리스트");
		List<Performance> result = pd.performanceList(performance);
		return result;
	}

	//불량 수량
	@Override
	public int defectCnt(Defect defect) {
		System.out.println("불량수량  서비스");
		int result = pd.defectCnt(defect);
		return result;
	}
	
	//불량 리스트
	@Override
	public List<Defect> defectList(Defect defect) {
		System.out.println("불량 리스트");
		List<Defect> result = pd.defectList(defect);
		return result;
	}

	//이번달 총 발주금액 구하기
	@Override
	public int monthTotalOrderPrice() {
		System.out.println("이번달 총 발주금액 구하기 서비스");
		int result = pd.monthTotalOrderPrice();
		return result;
	}

	//저번달 총 발주금액 구하기
	@Override
	public int lastMonthTotalOrderPrice() {
		System.out.println("저번달 총 발주금액 구하기 서비스");
		int result = pd.lastMonthTotalOrderPrice();
		return result;
	}

	//이번달 총 입고금액 구하기
	@Override
	public int monthTotalIncomePrice() {
		System.out.println("이번달 총 입고금액 구하기 서비스");
		int result = pd.monthTotalIncomePrice();
		return result;
	}

	//저번달 총 입고금액 구하기
	@Override
	public int lastMonthTotalIncomePrice() {
		System.out.println("저번달 총 입고금액 구하기 서비스");
		int result = pd.lastMonthTotalIncomePrice();
		return result;
	}

	//이번달 총 불량금액 구하기
	@Override
	public int monthTotalDefectPrice() {
		System.out.println("이번달 총 불량금액 구하기 서비스");
		int result = pd.monthTotalDefectPrice();
		return result;
	}

	//저번달 총 불량금액 구하기
	@Override
	public int lastMonthTotalDefectPrice() {
		System.out.println("저번달 총 불량금액 구하기 서비스");
		int result = pd.lastMonthTotalDefectPrice();
		return result;
	}

	@Override
	public void selectYearOrderCnt(HashMap<String, Object> map) {
		System.out.println("TotalOrderService selectYearOrderCnt start...");
		pd.selectYearOrderCnt(map);
		
	}

	@Override
	public void selectYearReturnCnt(HashMap<String, Object> map) {
		System.out.println("TotalOrderService selectYearReturnCnt start...");
		pd.selectYearReturnCnt(map);
		System.out.println("TotalOrderService selectYearReturnCnt end...");
		
	}

	
	
	
	
	
}
