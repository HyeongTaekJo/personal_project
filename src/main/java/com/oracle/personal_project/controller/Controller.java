package com.oracle.personal_project.controller;

import java.util.HashMap;
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

import com.oracle.personal_project.Service.DefectService;
import com.oracle.personal_project.Service.LoginService;
import com.oracle.personal_project.Service.MemberService;
import com.oracle.personal_project.Service.Paging;
import com.oracle.personal_project.Service.ProductService;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Defect;
import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Machine;
import com.oracle.personal_project.model.Performance;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.ProductListWrapper;
import com.oracle.personal_project.model.Ship;
import com.oracle.personal_project.model.Small_code;
import com.oracle.personal_project.model.Work_center;
import com.oracle.personal_project.model.Work_order;
import com.oracle.personal_project.model.TotalOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.time.LocalDate;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
@Slf4j
public class Controller {
	private final LoginService ls;
	private final ProductService pd;
	private final MemberService ms;
	private final DefectService ds;
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		
		System.out.println("로그인 시작");
		
		
		return "/loginForm";
	}
	
	@RequestMapping("/signInsert")
	public String signInsert(Emp emp, Model model) {
		
		System.out.println("회원등록");
		
		int result = ls.signInsert(emp);
		System.out.println("HtController signInsert result-->" + result);
		
		model.addAttribute("emp",emp);
		
		return "forward:chk";
	}
	
	@GetMapping(value = "memberLogout")
	public String memberLogout(HttpSession session, HttpServletRequest request) {
		
		System.out.println("YbController userLogout start... ");
		String dest = (String) session.getAttribute("dest");
		try {
			session = request.getSession(false); // 세션이 있으면 기존 세션을 반환한다.
			// 세션이 없으면 새로운 세션을 생성하지 않고, null을 반환
			if (session != null) {
				System.out.println("YbController logout() session null ");
				session.removeAttribute("emp");
//				session.invalidate(); // 세션 초기화
			}
		} catch (Exception e) {
			System.out.println("logout Exception -> " + e.getMessage());
		}

		session.removeAttribute("dest");  
		return "loginForm";
	}
	
	@RequestMapping("/chk")
	public String chk(Emp emp, Model model, HttpSession session, HttpServletRequest request) {
		
		System.out.println("chk");
		
		session.setAttribute("emp", emp);
		
		return "redirect:loginForm";
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
	public String login(Model model, Emp emp1,  HttpSession session, HttpServletRequest request) {
		
		System.out.println("Controller login()");
		System.out.println("Controller emp1-->" + emp1);

		System.out.println("YbController login() session -> " + session);
		
		Emp emp = ls.login(emp1);
		
		if (emp != null) {
			session.setAttribute("emp", emp);
			System.out.println("Controller login() session -> " + session.getId());
			System.out.println("Controller login() emp.getId -> " + emp.getE_id());
			
			// 인터셉터에서 이전페이지 주소 세션 받기
			String dest = (String) session.getAttribute("dest");
		    String redirectUrl = dest != null ? dest : "/";
		    System.out.println("ybController memberLogin dest => " + redirectUrl);
		    
		    return "redirect:" + redirectUrl;

		} else {
			return "loginForm";
		}
		
//		// 세션에 로그인 정보 담기
//		session.setAttribute("e_code", emp.getE_id());
//		
//		return "/loginForm";
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
	
	 // 관리자-페이지 이동
	 @RequestMapping("/")
	 public String main(Model model) {
		
	   // 총 불량수
	   int totalDefect = ds.totalDefect();
	   // 1순위 불량
	   Defect firstDefect = ds.firstDefect();
	   // 2순위 불량 
	   Defect secondDefect = ds.secondDefect();
	   // 3순위 불량
	   Defect thirdDefect = ds.thirdDefect();
	   // 4순위 불량
	   Defect forthDefect = ds.forthDefect();
	  
	   model.addAttribute("totalDefect",totalDefect);
	   model.addAttribute("firstDefect",firstDefect);
	   model.addAttribute("secondDefect",secondDefect);
	   model.addAttribute("thirdDefect",thirdDefect);
	   model.addAttribute("forthDefect",forthDefect);
	   
	   
	   model.addAttribute("nomalMember",firstDefect.getTotal_count());
	   model.addAttribute("adminMember",secondDefect.getTotal_count());
	   model.addAttribute("activeMember",thirdDefect.getTotal_count());
	   model.addAttribute("wdMember",forthDefect.getTotal_count());

	   float averFirstDefect = ((float)firstDefect.getTotal_count() / totalDefect) * 100;
	   float averSecondDefect = ((float)secondDefect.getTotal_count() / totalDefect) * 100;
	   float averThirdDefect = ((float)thirdDefect.getTotal_count() / totalDefect) * 100;
	   float averForthDefect = ((float)forthDefect.getTotal_count() / totalDefect) * 100;
		
	   model.addAttribute("averFirstDefect",averFirstDefect);
	   model.addAttribute("averSecondDefect",averSecondDefect);
	   model.addAttribute("averThirdDefect",averThirdDefect);
	   model.addAttribute("averForthDefect",averForthDefect);
		 
	   System.out.println("totalDefect--> " + totalDefect );
	   System.out.println("averFirstDefect--> " + averFirstDefect );
	   
	   // 이번달 월 구하기
	   LocalDate date = LocalDate.now();
	   String dateString = date.toString();
	   System.out.println(dateString);
	   
	   int month = date.getMonthValue();
	   System.out.println("month -> "+month);
	   
	   model.addAttribute("month", month);
	   
	   //이번달 총 발주금액 구하기
	   int monthTotalOrderPrice = pd.monthTotalOrderPrice();
	   //저번달 총 발주금액 구하기
	   int lastMonthTotalOrderPrice = pd.lastMonthTotalOrderPrice();
	   
	   //이번달 총 입고금액 구하기
	   int monthTotalIncomePrice = pd.monthTotalIncomePrice();
	   //저번달 총 입고금액 구하기
	   int lastMonthTotalIncomePrice = pd.lastMonthTotalIncomePrice();
	   
	   //이번달 총 불량금액 구하기
	   int monthTotalDefectPrice = pd.monthTotalDefectPrice();
	   //저번달 총 불량금액 구하기
	   int lastMonthTotalDefectPrice = pd.lastMonthTotalDefectPrice();
	   
	   
	   System.out.println("monthTotalIncomePrice -> "+monthTotalIncomePrice);
	   System.out.println("lastMonthTotalIncomePrice -> "+lastMonthTotalIncomePrice);
	   
	   model.addAttribute("monthTotalOrderPrice",monthTotalOrderPrice);
	   model.addAttribute("lastMonthTotalOrderPrice",lastMonthTotalOrderPrice);
	   model.addAttribute("monthTotalIncomePrice",monthTotalIncomePrice);
	   model.addAttribute("lastMonthTotalIncomePrice",lastMonthTotalIncomePrice);
	   model.addAttribute("monthTotalDefectPrice",monthTotalDefectPrice);
	   model.addAttribute("lastMonthTotalDefectPrice",lastMonthTotalDefectPrice);
	   
	   // 올해 주문 총 건수 *********
	  HashMap<String, Object> map = new HashMap<String, Object>();
	  pd.selectYearOrderCnt(map);
	  
	  List<TotalOrder> totalOrderList = (List<TotalOrder>) map.get("order_cnt_list");
	  for(TotalOrder totalorder : totalOrderList) {
		  System.out.println("totalorder.getOrder_cnt()"+totalorder.getOrder_cnt());
	  }
	  
	  // 올해 반품 총 건수 **********
	  pd.selectYearReturnCnt(map);
	  
	  List<TotalOrder> totalReturnList = (List<TotalOrder>) map.get("order_return_list");
	  for(TotalOrder totalreturn : totalReturnList) {
		  System.out.println("totalreturn.getOrder_cnt()->"+totalreturn.getOrder_cnt());
	  }
	   
	  model.addAttribute("totalOrderList", totalOrderList);
	  model.addAttribute("totalReturnList", totalReturnList);
	   
	   
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
	
	//workOrder 리스트
	@RequestMapping("/workOrderList")
	public String workOrderList(String currentPage, Model model, Work_order workOrder) {
		System.out.println("생산계획 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// ship 객체의 각 필드 값 확인하여 null이면 공백으로 처리
		Field[] fields = workOrder.getClass().getDeclaredFields();
		for (Field field : fields) {
		    // pageNum 필드는 제외
		    if (field.getName().equals("pageNum")) {
		        continue;
		    }
		    
		    field.setAccessible(true);
		    try {
		        Object value = field.get(workOrder);
		        if (value == null) {
		            field.set(workOrder, "");
		        }
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    }
		}
		
		System.out.println("workOrder--> " + workOrder);
		// 지시수량
		int workOrderCnt = pd.workOrderCnt(workOrder); // 제품 상세별 총 개수를 구해준다.
		System.out.println("workOrderCnt -> "+workOrderCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(workOrderCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		workOrder.setStart(page.getStartRow());
		workOrder.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("workOrder() -> " + workOrder);
		
		// 작업지시 리스트(조건 포함)
		List<Work_order> workOrderList = pd.workOrderList(workOrder);
		
		// 설비 리스트
		List<Machine> MachineList = pd.MachineList();
		
		// 작업장 리스트
		List<Work_center> WorkCenterList = pd.WorkCenterList();
		
		// 작업상태
		List<Small_code> workStatusList = pd.workStatusList();
		
		
		model.addAttribute("workOrderCnt", workOrderCnt);
		model.addAttribute("workOrderList", workOrderList);
		model.addAttribute("workOrder", workOrder);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("MachineList", MachineList);
		model.addAttribute("WorkCenterList", WorkCenterList);
		model.addAttribute("workStatusList", workStatusList);
		model.addAttribute("currentPage", currentPage);
		
		return "/workOrderList";
	}

	// 모달창 생산계획 리스트 ajax
	@ResponseBody
	@RequestMapping("/planOrderListModal")
	public List<Plan_order> planOrderListModal() {
		
		System.out.println("모달 리스트 시작");
		
		// 거래처 리스트(조건 포함)
		List<Plan_order> result =  pd.planOrderListModal();
		
		return result;
	}
	
	//생산계획 등록
	@PostMapping("/newWorkOrderInsert")
	public ResponseEntity<String> newWorkOrderInsert(@RequestBody List<Work_order> workOrderList) {
	    // 받은 JSON 데이터 처리
		System.out.println("workOrderList--> " + workOrderList);
	    for (Work_order work : workOrderList) {
	        // 각 제품에 대한 처리 수행
	        System.out.println("Received ship plan_code: " + work.getPlan_code());
	        System.out.println("Received ship Wc_code: " + work.getWc_code());
	        System.out.println("Received ship Mc_code: " + work.getMc_code());
	        System.out.println("Received ship Plan_qty: " + work.getPlan_qty());
	        System.out.println("Received ship Qty: " + work.getQty());
	        System.out.println("Received ship ship_deli: " + work.getShip_deli());
	        
	        // 여기에 받은 제품 정보를 데이터베이스에 저장하거나 다른 비즈니스 로직을 수행할 수 있습니다.
	    }
	    // jsp로 받은 파라미터로 삭제할 데이터 확인
 		String result = String.valueOf(pd.newWorkOrderInsert(workOrderList));
    
	    // 처리 결과에 따라 적절한 응답 반환
	    return ResponseEntity.ok(result);
	}
	
	
	// 생산계획 삭제 ajax
	@ResponseBody
	@RequestMapping("/deleteWorkOrder")
	public String deleteWorkOrder(String wo_code) {
		System.out.println(" deleteWorkOrder() start...");
		System.out.println(" wo_code --> " + wo_code);
		
		// jsp로 받은 파라미터로 삭제할 데이터 확인
		String result = String.valueOf(pd.deleteWorkOrder(wo_code));
		
		System.out.println("result--> " + result);
		
		return result;
	}
	
	
	//performance 리스트
	@RequestMapping("/performanceList")
	public String performanceList(String currentPage, Model model, Performance performance) {
		System.out.println("실적조회 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// ship 객체의 각 필드 값 확인하여 null이면 공백으로 처리
		Field[] fields = performance.getClass().getDeclaredFields();
		for (Field field : fields) {
		    // pageNum 필드는 제외
		    if (field.getName().equals("pageNum")) {
		        continue;
		    }
		    
		    field.setAccessible(true);
		    try {
		        Object value = field.get(performance);
		        if (value == null) {
		            field.set(performance, "");
		        }
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    }
		}
		
		System.out.println("performance--> " + performance);
		// 지시수량
		int performanceCnt = pd.performanceCnt(performance); // 제품 상세별 총 개수를 구해준다.
		System.out.println("performanceCnt -> "+performanceCnt);
		
		// 페이징 처리 작업
		Paging page = new Paging(performanceCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		performance.setStart(page.getStartRow());
		performance.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("performance() -> " + performance);
		
		// 실적조회 리스트(조건 포함)
		List<Performance> performanceList = pd.performanceList(performance);
		
		// 담당자 리스트
		List<Emp> empList = pd.empList();
		
		// 제품 리스트
		List<Product> productList = pd.productList();
		
		// 거래처 리스트(조건 포함)
		List<Customer> customerList = pd.customerList();
		
		
		model.addAttribute("performanceCnt", performanceCnt);
		model.addAttribute("performanceList", performanceList);
		model.addAttribute("performance", performance);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("productList", productList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("empList", empList);
		model.addAttribute("currentPage", currentPage);
		
		return "/performanceList";
	}
	
	//불량 리스트
	@RequestMapping("/defectList")
	public String defectList(String currentPage, Model model, Defect defect) {
		System.out.println("불량리스트 페이지 시작");
		if(currentPage == null) {
			currentPage = "1";
		}
		
		// ship 객체의 각 필드 값 확인하여 null이면 공백으로 처리
		Field[] fields = defect.getClass().getDeclaredFields();
		for (Field field : fields) {
		    // pageNum 필드는 제외
		    if (field.getName().equals("pageNum")) {
		        continue;
		    }
		    
		    field.setAccessible(true);
		    try {
		        Object value = field.get(defect);
		        if (value == null) {
		            field.set(defect, "");
		        }
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    }
		}
		
		System.out.println("defect--> " + defect);
		// 불량수량
		int defectCnt = pd.defectCnt(defect); // 제품 상세별 총 개수를 구해준다.
		System.out.println("defect -> "+defect);
		
		// 페이징 처리 작업
		Paging page = new Paging(defectCnt, currentPage);
		// Parameter 제품 --> Page만 추가 Setting
		defect.setStart(page.getStartRow());
		defect.setEnd(page.getEndRow());
		int startRow = page.getStartRow();
		
		System.out.println("page.getStartRow() -> " + page.getStartRow());
		System.out.println("page.getEndRow() -> " + page.getEndRow());
		System.out.println("defect() -> " + defect);
		
		// 불량 리스트(조건 포함)
		List<Defect> defectList = pd.defectList(defect);
		
		// 담당자 리스트
		List<Emp> empList = pd.empList();
		
		// 제품 리스트
		List<Product> productList = pd.productList();
		
		// 거래처 리스트(조건 포함)
		List<Customer> customerList = pd.customerList();
		
		
		model.addAttribute("defectCnt", defectCnt);
		model.addAttribute("defectList", defectList);
		model.addAttribute("defect", defect);
		model.addAttribute("startRow", startRow);
		model.addAttribute("page", page);
		model.addAttribute("productList", productList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("empList", empList);
		model.addAttribute("currentPage", currentPage);
		
		return "/defectList";
	}
}
