package com.oracle.personal_project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.personal_project.Service.LoginService;
import com.oracle.personal_project.Service.Paging;
import com.oracle.personal_project.Service.ProductService;
import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Dept;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Small_code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		//DB에 없는 값
		if(p_code == "") {
			return "2";
		}
		
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
	
}
