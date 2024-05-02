package com.oracle.personal_project.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterCeptor implements HandlerInterceptor {
	private static final String LOGIN = "login";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterCeptor postHandle Start...");
		
		HttpSession session = request.getSession();

		ModelMap modelMap = modelAndView.getModelMap();
		Object emp = modelMap.get("emp");
		Object dest = null;
		if(emp != null) {			
			System.out.println("Login success!");
			session.setAttribute(LOGIN, emp);
			
			// 이전 페이지 불러오기
			dest = session.getAttribute("dest");
			
//			response.sendRedirect(dest != null ? dest.toString() : "/");
			String redirectUrl = dest != null ? dest.toString() : "/";
			System.out.println("LoginInterCeptor postHandle redirectUrl => " + redirectUrl);
			
			session.setAttribute("redirectUrl", redirectUrl);	
			
			session.removeAttribute("dest");  
			  
		}
	
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("LoginInterCeptor preHandle() Start...");
		// session 객체를 가져옴
        HttpSession session = request.getSession();
        
        if ( session.getAttribute("emp") == null ){
            // 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
        	System.out.println("LoginInterCeptor Start... You do not have permission. Please log in first");
        	
            response.sendRedirect("/loginForm");
            
            return false; // 더 이상 컨트롤러 요청으로 가지 않도록 false로 반환함
//             
        }

        return true;
	}
}
