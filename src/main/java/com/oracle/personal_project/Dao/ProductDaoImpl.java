package com.oracle.personal_project.Dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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

	//신규 제품 추가 
	@Override
	public String newProductInsert(List<Product> dataList) {
		System.out.println("신규 추가  다오");
		int result = 0;
		String result2 = "";
		List<Product> findName = null;
		try {
			
			// 중복을 확인할 Set 생성
			Set<String> pNames = new HashSet<String>();

			// dataList 안에 있는 Product 객체들을 반복하여 p_name 추출하여 중복 확인
			for (Product product : dataList) {
			    String pName = product.getP_name();
			    // Set에 p_name을 넣기 전에 이미 있는지 확인하여 중복 여부 판단
			    if (pNames.contains(pName)) {
			        // 중복이 발견되면 처리
			        result2 = "fail";
			        return result2; // 중복이 발견되면 반복문 종료
			    }
			    // 중복이 없다면 Set에 p_name 추가
			    pNames.add(pName);
			}
			
			
			findName = session.selectList("findProductName", dataList);
			
			if(findName.isEmpty()) {
				result = session.insert("newProductInsert", dataList);
				System.out.println("result--> " + result);
				result2 = Integer.toString(result);
			}else {
				result2 = "fail";
			}
			
			
		}catch (Exception e) {
			System.out.println("deleteProduct Exception--> " + e.getMessage());
		}
		System.out.println("result2--> " + result2);
		return result2;
	}

	// 거래처 수량
	@Override
	public int customerCnt(Customer customer) {
		System.out.println("거래처수량");
		int result = 0;
		
		try {
			result = session.selectOne("customerCnt", customer);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//거래처 리스트
	@Override
	public List<Customer> customerList(Customer customer) {
		System.out.println("거래처 리스트  다오");
		List<Customer> list = null;
		
		try {
			list = session.selectList("customerList", customer);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//담당자 리스트
	@Override
	public List<Emp> empList() {
		System.out.println("담당자 리스트  다오");
		List<Emp> list = null;
		
		try {
			list = session.selectList("empList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	// 거래처 구분
	@Override
	public List<Small_code> customerSmallList() {
		System.out.println("소분류 리스트  다오");
		List<Small_code> list = null;
		
		try {
			list = session.selectList("customerSmallList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//신규 거래처 추가
	@Override
	public int newCustomerInsert(List<Customer> customertList) {
		System.out.println("신규 거래처 추가  다오");
		int result = 0;
		List<Customer> findName = null;
		try {
			// 중복을 확인할 Set 생성
			Set<String> pNames = new HashSet<String>();

			// dataList 안에 있는 Product 객체들을 반복하여 p_name 추출하여 중복 확인
			for (Customer customer : customertList) {
			    String pName = customer.getC_name();
			    // Set에 p_name을 넣기 전에 이미 있는지 확인하여 중복 여부 판단
			    if (pNames.contains(pName)) {
			        // 중복이 발견되면 처리
			        result = -1;
			        return result; // 중복이 발견되면 반복문 종료
			    }
			    // 중복이 없다면 Set에 p_name 추가
			    pNames.add(pName);
			}
			
			
			findName = session.selectList("findCustomerName", customertList);
			System.out.println("findName--> " + findName);
			if(findName.isEmpty()) {
				result = session.insert("newCustomerInsert", customertList);
			}else {
				result = -1;
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	//거래처 삭제
	@Override
	public int deleteCustomer(String c_code) {
		System.out.println("삭제  다오");
		List<String> findShip = null;
		int result = 0;
		try {
			findShip = session.selectList("findShip_customer", c_code);
			System.out.println("findShip--> " + findShip);
			if(!findShip.isEmpty()) {
				result = 2;
				return result;
			} else {
				result = session.delete("deleteCustomer", c_code);
				System.out.println("deleteCustomer result--> " + result);
			}
			
		}catch (Exception e) {
			System.out.println("deleteCustomer Exception--> " + e.getMessage());
		}
		return result;
	}

	//수주 수량
	@Override
	public int shipCnt(Ship ship) {
		System.out.println("거래처수량");
		int result = 0;
		
		try {
			result = session.selectOne("shipCnt", ship);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	//수주 리스트
	@Override
	public List<Ship> shipList(Ship ship) {
		System.out.println("수주 리스트  다오");
		List<Ship> list = null;
		
		try {
			list = session.selectList("shipList", ship);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//제품 리스트
	@Override
	public List<Product> productList() {
		System.out.println("제품 리스트  다오");
		List<Product> list = null;
		
		try {
			list = session.selectList("productList2");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	

	// 거래처 리스트
	@Override
	public List<Customer> customerList() {
		System.out.println("거래처 리스트2  다오");
		List<Customer> list = null;
		
		try {
			list = session.selectList("customerList2");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	// 수주 추가
	@Override
	public int newShiptInsert(List<Ship> shipList) {
		System.out.println("신규 추가  다오");
		int result = 0;
		try {
			result = session.insert("newShiptInsert", shipList);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	//수주 삭제
	@Override
	public int deleteShip(String ship_code) {
		System.out.println("삭제  다오");
		List<String> planOrderList = null;
		int result = 0;
		try {
			planOrderList = session.selectList("findPlanOrder", ship_code);
			System.out.println("planOrderList--> " + planOrderList);
			if(!planOrderList.isEmpty()) {
				result = 2;
				return result;
			} else {
				result = session.delete("deleteShip", ship_code);
				System.out.println("deleteProduct result--> " + result);
			}
			
		}catch (Exception e) {
			System.out.println("deleteShip Exception--> " + e.getMessage());
		}
		return result;
	}

	// 생산계획 수량
	@Override
	public int planOrderCnt(Plan_order planOrder) {
		System.out.println("생산계획 수량");
		int result = 0;
		
		try {
			result = session.selectOne("planOrderCnt", planOrder);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	// 생산계획 리스트
	@Override
	public List<Plan_order> planOrderList(Plan_order planOrder) {
		System.out.println("생산계획 리스트  다오");
		List<Plan_order> list = null;
		
		try {
			list = session.selectList("planOrderList", planOrder);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//모달 수주 리스트
	@Override
	public List<Ship> shipList() {
		System.out.println("모달수주 리스트  다오");
		List<Ship> list = null;
		
		try {
			list = session.selectList("shipListModal");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//신규 생산계획 추가
	@Override
	public int newPlanInsert(List<Plan_order> planList) {
		System.out.println("신규생산계획 추가  다오");
		int result = 0;
		try {
			result = session.insert("newPlanInsert", planList);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	//생산계획 삭제
	@Override
	public int deletePlan(String plan_code) {
		System.out.println("삭제  다오");
		List<String> workOrderList = null;
		int result = 0;
		try {
			workOrderList = session.selectList("findWorkOrder", plan_code);
			System.out.println("workOrderList--> " + workOrderList);
			if(!workOrderList.isEmpty()) {
				result = 2;
				return result;
			} else {
				result = session.delete("deletePlan", plan_code);
				System.out.println("deletePlan result--> " + result);
			}
			
		}catch (Exception e) {
			System.out.println("deletePlan deletePlan--> " + e.getMessage());
		}
		return result;
	}

	//지시 수량
	@Override
	public int workOrderCnt(Work_order workOrder) {
		System.out.println("지시 수량");
		int result = 0;
		
		try {
			result = session.selectOne("workOrderCnt", workOrder);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//지시 리스트
	@Override
	public List<Work_order> workOrderList(Work_order workOrder) {
		System.out.println("지시 리스트  다오");
		List<Work_order> list = null;
		
		try {
			list = session.selectList("workOrderList", workOrder);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//설비리스트
	@Override
	public List<Machine> MachineList() {
		System.out.println("설비리스트  다오");
		List<Machine> list = null;
		
		try {
			list = session.selectList("MachineList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//작업장 리스트
	@Override
	public List<Work_center> WorkCenterList() {
		System.out.println("작업장 리스트  다오");
		List<Work_center> list = null;
		
		try {
			list = session.selectList("WorkCenterList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//작업상태 리스트
	@Override
	public List<Small_code> workStatusList() {
		System.out.println("작업장 리스트  다오");
		List<Small_code> list = null;
		
		try {
			list = session.selectList("workStatusList");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//모달 생산계획 리스트
	@Override
	public List<Plan_order> planOrderListModal() {
		System.out.println("모달 생산계획 리스트  다오");
		List<Plan_order> list = null;
		
		try {
			list = session.selectList("planOrderListModal");
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//신규 작업지시추가
	@Override
	public int newWorkOrderInsert(List<Work_order> workOrderList) {
		System.out.println("신규작업지시추가  다오");
		int result = 0;
		try {
			result = session.insert("newWorkOrderInsert", workOrderList);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	//작업지시 삭제
	@Override
	public int deleteWorkOrder(String wo_code) {
		System.out.println("삭제  다오");
		List<String> performanceList = null;
		int result = 0;
		try {
			performanceList = session.selectList("findPerformance", wo_code);
			System.out.println("performanceList--> " + performanceList);
			if(!performanceList.isEmpty()) {
				result = 2;
				return result;
			} else {
				result = session.delete("deleteWorkOrder", wo_code);
				System.out.println("deleteWorkOrder result--> " + result);
			}
			
		}catch (Exception e) {
			System.out.println("deleteWorkOrder--> " + e.getMessage());
		}
		return result;
	}

	//실적수량
	@Override
	public int performanceCnt(Performance performance) {
		System.out.println("실적수량");
		int result = 0;
		
		try {
			result = session.selectOne("performanceCnt", performance);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//실적 리스트
	@Override
	public List<Performance> performanceList(Performance performance) {
		System.out.println("실적 리스트");
		List<Performance> list = null;
		
		try {
			list = session.selectList("performanceList", performance);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	//불량 수량
	@Override
	public int defectCnt(Defect defect) {
		System.out.println("불량 수량");
		int result = 0;
		
		try {
			result = session.selectOne("defectCnt", defect);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//불량 리스트
	@Override
	public List<Defect> defectList(Defect defect) {
		System.out.println("불량 리스트");
		List<Defect> list = null;
		
		try {
			list = session.selectList("defectList", defect);
			System.out.println("list ==> " + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	
    
	//이번달 총 발주금액 구하기
	@Override
	public int monthTotalOrderPrice() {
		System.out.println("이번달 총 발주금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString = previousMonth.format(formatter);
	    System.out.println("lastDateString ==> " + lastDateString);
	    
		try {
			result = session.selectOne("monthTotalOrderPrice", dateString);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	
	//저번달 총 발주금액 구하기
	@Override
	public int lastMonthTotalOrderPrice() {
		System.out.println("저번달 총 발주금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString2 = previousMonth.format(formatter);
	    System.out.println("lastDateString2 ==> " + lastDateString2);
        
		try {
			result = session.selectOne("lastMonthTotalOrderPrice", lastDateString2);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//이번달 총 입고금액 구하기
	@Override
	public int monthTotalIncomePrice() {
		System.out.println("이번달 총 입고금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString = previousMonth.format(formatter);
	    System.out.println("lastDateString ==> " + lastDateString);
	    
		try {
			result = session.selectOne("monthTotalIncomePrice", dateString);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//저번달 총 입고금액 구하기
	@Override
	public int lastMonthTotalIncomePrice() {
		System.out.println("저번달 총 발주금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString2 = previousMonth.format(formatter);
	    System.out.println("lastDateString2 ==> " + lastDateString2);
        
		try {
			result = session.selectOne("lastMonthTotalIncomePrice", lastDateString2);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//이번달 총 불량금액 구하기
	@Override
	public int monthTotalDefectPrice() {
		System.out.println("이번달 총 불량금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString = previousMonth.format(formatter);
	    System.out.println("lastDateString ==> " + lastDateString);
	    
		try {
			result = session.selectOne("monthTotalDefectPrice", dateString);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//저번달 총 불량금액 구하기
	@Override
	public int lastMonthTotalDefectPrice() {
		System.out.println("저번달 총 불량금액 구하기");
		int result = 0;
		
		// 현재 날짜
	    LocalDate date = LocalDate.now();
	    // 날짜를 "yyyy-MM" 형식의 문자열로 변환
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    String dateString = date.format(formatter);
	    System.out.println("dateString ==> " + dateString);
	    
	    LocalDate previousMonth = date.minusMonths(1);
	    String lastDateString2 = previousMonth.format(formatter);
	    System.out.println("lastDateString2 ==> " + lastDateString2);
        
		try {
			result = session.selectOne("lastMonthTotalDefectPrice", lastDateString2);
			System.out.println("result ==> " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public void selectYearOrderCnt(HashMap<String, Object> map) {
		System.out.println("TotalOrderDao selectYearOrderCnt start...");
		
		try {
			session.selectOne("gbSelectYearOrderCnt", map);
		} catch (Exception e) {
			System.out.println("TotalOrderDao selectYearOrderCnt Exception -> "+e.getMessage());
		}
		
	}

	@Override
	public void selectYearReturnCnt(HashMap<String, Object> map) {
		System.out.println("TotalOrderDao selectYearReturnCnt start...");
		
		try {
			session.selectOne("gbSelectYearReturnCnt", map);
			System.out.println("TotalOrderDao selectYearReturnCnt end...");
		} catch (Exception e) {
			System.out.println("TotalOrderDao selectYearReturnCnt Exception -> "+e.getMessage());
		}
		
	}

}
