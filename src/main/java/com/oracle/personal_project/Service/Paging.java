package com.oracle.personal_project.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
	// 현재 페이지번호					// row 제한 개수					// page 넘버 제한 개수
	private int currentPage1 = 1;	private int rowLimit = 10;	private int pageLimit = 10;
	// row 시작 번호					// row 끝 번호
	private int startRow;			private int endRow;
	// page 시작번호					// page 끝번호
	private int startPage;			private int endPage;
	// 총 row 개수						// 총 page 개수
	private int totalRow;			private int totalPage;
	
	public Paging(int productCnt, String currentPage) {
		this.totalRow = productCnt;

		if (currentPage != null) {
			this.currentPage1 = Integer.parseInt(currentPage);
		}
		
		// 한 페이지에 보여줄 start와 end row 개수
		//if			2					10
		startRow = (currentPage1 - 1) * rowLimit + 1 ;	// 시작 row : 11
		//if		  11    +    10      
		endRow	 = startRow + rowLimit - 1; 			// 끝 row  : 20
		
		// 총 페이지 페이지 개수
		//if                                   34    /     10     = 3.4  -> ceil 하여 올림처리하면 4     
		totalPage = (int) Math.ceil((double)totalRow / pageLimit); // int / int = int 이기 떄문에 totalRow를 double로 변경해야 소수점까지 결과값이 나옴.
		
		// 한 페이지에 보여줄 start와 end page 개수
		//if			 2				2   -   1	%    10    
		startPage = currentPage1 - (currentPage1-1) % pageLimit; // 시작 page = 1  (현재페이지 : 13이면 시작페이지는 11이 나와야 함. 13-2가 11이니까 2는 현재 페이지에 1을 뺴고 10을 나눈 나머지 값이다.)
		endPage	  = startPage + pageLimit - 1;					 // 끝 page = 10
		
		// 만약 총 page 개수가 pageLimit 보다 작다면
		// 공갈 페이지를 제거하기 위함.
		if (endPage > totalPage) {
			endPage = totalPage ;
		}
		
	}

}
