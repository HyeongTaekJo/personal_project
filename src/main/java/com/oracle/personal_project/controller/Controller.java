package com.oracle.personal_project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.personal_project.Service.LoginService;
import com.oracle.personal_project.Service.Paging;
import com.oracle.personal_project.Service.ProductService;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.ProductListWrapper;
import com.oracle.personal_project.model.Ship;
import com.oracle.personal_project.model.Small_code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
@Slf4j
public class Controller {
	private final LoginService ls;
	private final ProductService pd;
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		
		System.out.println("로그인 시작");
		
		return "/loginForm";
	}
	
	
	// 로그인 시 회원 체크 Ajax
	@ResponseBody
	@RequestMapping("/empChk")
	public String memberLoginChk(Emp emp, String chk_Id, String chk_Pw) {
		System.out.println(" empChk() start...");
		// jsp로 받은 파라미터로 회원 있나 검색
		emp = ls.empChk(chk_Id);
		if (emp != null) {
			
			int m_wd = emp.getE_wd();
			String m_id = emp.getE_id();
			String m_pw = emp.getE_pw();
			
			int result = 0;
			// 받은 파라미터로 체크
			if (chk_Id.equals(m_id) && chk_Pw.equals(m_pw) && m_wd == 0) {
				result = 1;
			} else if (chk_Id.equals(m_id) && chk_Pw.equals(m_pw) && m_wd == 1) {
				result = 2;
			} else {
				result = 0;
			}

			System.out.println("memberLoginChk result -> " + result);

			String strResult = Integer.toString(result);
			return strResult;

		} else {
			return "0";
		}
	}

	//로그인 검사
	@GetMapping("/login")
	public String login(Model model, Emp emp, HttpSession session) {
		System.out.println("로그인 완료");
		
		// 세션에 로그인 정보 담기
		session.setAttribute("e_code", emp.getE_id());
		
		return "/loginForm";
	}
	
	//회원가입
	@RequestMapping("/signUp")
	public String signUp(Model model, Dept dept) {
		
		System.out.println("회원가입폼");
		
		List<Dept> deptList = ls.deptList();
		
		model.addAttribute("deptList",deptList);
		
		return "/signUp";
	}
	
	//아이디 중복검사
	@ResponseBody
	@RequestMapping("/checkDuplicate")
    public String checkDuplicate(String chk_id) {
		Emp result = ls.checkDuplicate(chk_id);
		
        if (result != null) {
        	System.out.println("존재");
            return "exists";
        } else {
        	System.out.println("비존재");
            return "available";
        }
    }
	
	// 제품 삭제 ajax
	@ResponseBody
	@RequestMapping("/deleteProduct")
	public String deleteProduct(String p_code) {
		System.out.println(" deleteProduct() start...");
		System.out.println(" p_code --> " + p_code);
		
		// jsp로 받은 파라미터로 삭제할 데이터 확인
		String result = String.valueOf(pd.deleteProduct(p_code));
		
		System.out.println("result--> " + result);
		
		return result;
	}
	
	
	@RequestMapping("/mainBo")
	public String main() {
		
		System.out.println("main 페이지");
		
		
		return "/mainBo";
	}
	
	
	@RequestMapping("/productList")
	public String productList(String currentPage, Model model, Product product) {
		System.out.println("제품조회 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// 도서 총 개수
		int productCnt = pd.productCnt(product); // 제품 상세별 총 개수를 구해준다.
		System.out.println("productCnt -> "+productCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(productCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		product.setStart(page.getStartRow());
		product.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("product() -> " + product);
		
		// 제품 리스트(조건 포함)
		List<Product> productList = pd.productList(product);
		
		// 대분류 리스트
		List<Big_code> bigList = pd.bigList();
		
		// 소분류 리스트
		List<Small_code> smallList = pd.smallList();
		
		System.out.println("product--> " + product);
		
		model.addAttribute("productCnt", productCnt);
		model.addAttribute("productList", productList);
		model.addAttribute("product", product);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("bigList", bigList);
		model.addAttribute("smallList", smallList);
		model.addAttribute("currentPage", currentPage);
		
		
		return "/productList";
	}
	
	
	@PostMapping("/newProductInsert")
	public ResponseEntity<String> saveProductList(@RequestBody List<Product> productList) {
	    // 받은 JSON 데이터 처리
		System.out.println("productList--> " + productList);
	    for (Product product : productList) {
	        // 각 제품에 대한 처리 수행
	        System.out.println("Received Product Code: " + product.getP_code());
	        System.out.println("Received Product Name: " + product.getP_name());
	        System.out.println("Received Big Category Code: " + product.getB_code());
	        System.out.println("Received Small Category Code: " + product.getS_code());
	        System.out.println("Received Product Cost: " + product.getP_cost());
	        
	        // 여기에 받은 제품 정보를 데이터베이스에 저장하거나 다른 비즈니스 로직을 수행할 수 있습니다.
	    }
	    
	    // jsp로 받은 파라미터로 삭제할 데이터 확인
 		String result = String.valueOf(pd.newProductInsert(productList));
 		
    
	    // 처리 결과에 따라 적절한 응답 반환
	    return ResponseEntity.ok(result);
	}
	
	@RequestMapping("/customerList")
	public String customerList(String currentPage, Model model, Customer customer) {
		System.out.println("거래처조회 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// 도서 총 개수
		int customerCnt = pd.customerCnt(customer); // 제품 상세별 총 개수를 구해준다.
		System.out.println("customerCnt -> "+customerCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(customerCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		customer.setStart(page.getStartRow());
		customer.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("customer() -> " + customer);
		
		// 거래처 리스트(조건 포함)
		List<Customer> customerList = pd.customerList(customer);
		
		// 담당자 리스트
		List<Emp> empList = pd.empList();
		
		// 거래처 구분 리스트
		List<Small_code> flagList = pd.customerSmallList();
		
		System.out.println("customer--> " + customer);
		
		model.addAttribute("customerCnt", customerCnt);
		model.addAttribute("customerList", customerList);
		model.addAttribute("customer", customer);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("empList", empList);
		model.addAttribute("flagList", flagList);
		model.addAttribute("currentPage", currentPage);
		
		
		return "/customerList";
	}
	
	@PostMapping("/newCustomertInsert")
	public ResponseEntity<String> newCustomertInsert(@RequestBody List<Customer> customertList) {
	    // 받은 JSON 데이터 처리
		System.out.println("customertList--> " + customertList);
	    for (Customer customer : customertList) {
	        // 각 제품에 대한 처리 수행
	        System.out.println("Received customer C_code: " + customer.getC_code());
	        System.out.println("Received customer C_name: " + customer.getC_name());
	        System.out.println("Received customer C_tel: " + customer.getC_tel());
	        System.out.println("Received customer C_email: " + customer.getC_email());
	        System.out.println("Received customer S_code: " + customer.getS_code());
	        System.out.println("Received customer E_code: " + customer.getE_code());
	        
	        // 여기에 받은 제품 정보를 데이터베이스에 저장하거나 다른 비즈니스 로직을 수행할 수 있습니다.
	    }
	    // jsp로 받은 파라미터로 삭제할 데이터 확인
 		String result = String.valueOf(pd.newCustomertInsert(customertList));
    
	    // 처리 결과에 따라 적절한 응답 반환
	    return ResponseEntity.ok(result);
	}
	
	// 제품 삭제 ajax
	@ResponseBody
	@RequestMapping("/deleteCustomer")
	public String deleteCustomer(String c_code) {
		System.out.println(" deleteCustomer() start...");
		System.out.println(" c_code --> " + c_code);
		
		// jsp로 받은 파라미터로 삭제할 데이터 확인
		String result = String.valueOf(pd.deleteCustomer(c_code));
		
		System.out.println("result--> " + result);
		
		return result;
	}
	
	//수주 리스트
	@RequestMapping("/shipList")
	public String shipList(String currentPage, Model model, Ship ship, Customer customer) {
		System.out.println("수주등록 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// ship 객체의 각 필드 값 확인하여 null이면 공백으로 처리
		Field[] fields = ship.getClass().getDeclaredFields();
		for (Field field : fields) {
		    // pageNum 필드는 제외
		    if (field.getName().equals("pageNum")) {
		        continue;
		    }
		    
		    field.setAccessible(true);
		    try {
		        Object value = field.get(ship);
		        if (value == null) {
		            field.set(ship, "");
		        }
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    }
		}
		
		System.out.println("ship--> " + ship);
		// 수주수량
		int shipCnt = pd.shipCnt(ship); // 제품 상세별 총 개수를 구해준다.
		System.out.println("shipCnt -> "+shipCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(shipCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		ship.setStart(page.getStartRow());
		ship.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("ship() -> " + ship);
		
		// 거래처 리스트(조건 포함)
		List<Ship> shipList = pd.shipList(ship);
		
		// 제품 리스트
		List<Product> productList = pd.productList();
		
		// 거래처 리스트(조건 포함)
		List<Customer> customerList = pd.customerList();
		
		// 담당자 리스트
		List<Emp> empList = pd.empList();
		
		model.addAttribute("shipCnt", shipCnt);
		model.addAttribute("shipList", shipList);
		model.addAttribute("ship", ship);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("productList", productList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("empList", empList);
		
		return "/shipList";
	}
	
	@PostMapping("/newShipInsert")
	public ResponseEntity<String> newShiptInsert(@RequestBody List<Ship> shipList) {
	    // 받은 JSON 데이터 처리
		System.out.println("shipList--> " + shipList);
	    for (Ship ship : shipList) {
	        // 각 제품에 대한 처리 수행
	        System.out.println("Received ship ship_code: " + ship.getShip_code());
	        System.out.println("Received ship p_code: " + ship.getP_code());
	        System.out.println("Received ship c_code: " + ship.getC_code());
	        System.out.println("Received ship ship_num: " + ship.getShip_num());
	        System.out.println("Received ship ship_date: " + ship.getShip_date());
	        System.out.println("Received ship ship_deli: " + ship.getShip_deli());
	        System.out.println("Received ship e_code: " + ship.getE_code());
	        
	        // 여기에 받은 제품 정보를 데이터베이스에 저장하거나 다른 비즈니스 로직을 수행할 수 있습니다.
	    }
	    // jsp로 받은 파라미터로 삭제할 데이터 확인
 		String result = String.valueOf(pd.newShiptInsert(shipList));
    
	    // 처리 결과에 따라 적절한 응답 반환
	    return ResponseEntity.ok(result);
	}
	
	// 제품 삭제 ajax
	@ResponseBody
	@RequestMapping("/deleteShip")
	public String deleteShip(String ship_code) {
		System.out.println(" deleteShip() start...");
		System.out.println(" ship_code --> " + ship_code);
		
		// jsp로 받은 파라미터로 삭제할 데이터 확인
		String result = String.valueOf(pd.deleteShip(ship_code));
		
		System.out.println("result--> " + result);
		
		return result;
	}
	
	//planOrder 리스트
	@RequestMapping("/planOrderList")
	public String planOrderList(String currentPage, Model model, Plan_order planOrder) {
		System.out.println("생산계획 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// ship 객체의 각 필드 값 확인하여 null이면 공백으로 처리
		Field[] fields = planOrder.getClass().getDeclaredFields();
		for (Field field : fields) {
		    // pageNum 필드는 제외
		    if (field.getName().equals("pageNum")) {
		        continue;
		    }
		    
		    field.setAccessible(true);
		    try {
		        Object value = field.get(planOrder);
		        if (value == null) {
		            field.set(planOrder, "");
		        }
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    }
		}
		
		System.out.println("planOrder--> " + planOrder);
		// 수주수량
		int planOrderCnt = pd.planOrderCnt(planOrder); // 제품 상세별 총 개수를 구해준다.
		System.out.println("shipCnt -> "+planOrderCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(planOrderCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		planOrder.setStart(page.getStartRow());
		planOrder.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("planOrder() -> " + planOrder);
		
		// 거래처 리스트(조건 포함)
		List<Plan_order> planOrderList = pd.planOrderList(planOrder);
		
		// 제품 리스트
		List<Product> productList = pd.productList();
		
		// 거래처 리스트(조건 포함)
		List<Customer> customerList = pd.customerList();
		
		
		model.addAttribute("planOrderCnt", planOrderCnt);
		model.addAttribute("planOrderList", planOrderList);
		model.addAttribute("planOrder", planOrder);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("productList", productList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("currentPage", currentPage);
		
		return "/planOrderList";
	}
	
	// 모달창 수주 리스트 ajax
	@ResponseBody
	@RequestMapping("/shipListModal")
	public List<Ship> shipListModal() {
		
		System.out.println("모달 리스트 시작");
		
		// 거래처 리스트(조건 포함)
		List<Ship> result =  pd.shipList();
		
		return result;
	}
	
	//생산계획 등록
	@PostMapping("/newPlanInsert")
	public ResponseEntity<String> newPlanInsert(@RequestBody List<Plan_order> planList) {
	    // 받은 JSON 데이터 처리
		System.out.println("planList--> " + planList);
	    for (Plan_order plan : planList) {
	        // 각 제품에 대한 처리 수행
	        System.out.println("Received ship plan_code: " + plan.getPlan_code());
	        System.out.println("Received ship ship_code: " + plan.getShip_code());
	        System.out.println("Received ship p_code: " + plan.getP_code());
	        System.out.println("Received ship c_code: " + plan.getC_code());
	        System.out.println("Received ship ship_num: " + plan.getShip_num());
	        System.out.println("Received ship ship_deli: " + plan.getShip_deli());
	        
	        // 여기에 받은 제품 정보를 데이터베이스에 저장하거나 다른 비즈니스 로직을 수행할 수 있습니다.
	    }
	    // jsp로 받은 파라미터로 삭제할 데이터 확인
 		String result = String.valueOf(pd.newPlanInsert(planList));
    
	    // 처리 결과에 따라 적절한 응답 반환
	    return ResponseEntity.ok(result);
	}
	
	// 생산계획 삭제 ajax
	@ResponseBody
	@RequestMapping("/deletePlan")
	public String deletePlan(String plan_code) {
		System.out.println(" deletePlan() start...");
		System.out.println(" plan_code --> " + plan_code);
		
		// jsp로 받은 파라미터로 삭제할 데이터 확인
		String result = String.valueOf(pd.deletePlan(plan_code));
		
		System.out.println("result--> " + result);
		
		return result;
	}
}
