<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/headerBo.jsp" %>

<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
       .pop_wrap {
        display: flex;
        align-items: center;
        justify-content: center;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        z-index: 9999;
    }

    .pop_inner {
        text-align: center;
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

.btn_open{font-weight:bold; margin:5px; padding:4px 6px; background:#000; color:#fff;}
.pop_wrap{position:fixed; top:0; left:0; right:0; bottom:0; background:rgba(0,0,0,.5); font-size:0; text-align:center;}
.pop_wrap:after{display:inline-block; height:100%; vertical-align:middle; content:'';}
.pop_wrap .pop_inner{display:inline-block; padding:20px 30px; background:#fff; width:200px; vertical-align:middle; font-size:15px;}

</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	


	// 페이징 색상변경
	$(document).ready(function() {
		 var currentPage = ${currentPage}  // 현재 페이지 값
	    // 현재 페이지와 일치하는 페이지 번호 요소의 배경색 변경
	    $('[id^="page"]').each(function() {
	        var index = this.id.replace('page', ''); // id에서 숫자 부분만 추출
	
	        // 현재 페이지와 일치하면 배경색을 파란색으로 설정
	        if (index == currentPage) {
	        	$(this).css('background-color', 'lightgreen');
	        }
	    });
	});
	
	//삭제
	function deleteCustomer(ship_code, pIndex){
		if(confirm("수주를 삭제하시겠습니까?")){
			$.ajax({
					url : "deleteShip",
					data : {ship_code : ship_code},
					dataType : 'Text',
					success : function(data){
						if(data == '1'){
							alert("삭제되었습니다.");
							 // table element 찾기
							  const table = document.getElementById('list');
							  
							  // 행(Row) 삭제
							  //const newRow = table.deleteRow(pIndex);
							  searchShip();
						}else if(data == '2'){
							// table element 찾기
							  alert("생산계획이 등록된 수주는 삭제할 수 없습니다.");
							 
						}else {
							alert("삭제에 실패하였습니다.");
						}
					}
			});
		}
	}
	
	//제품코드는 숫자만 입력가능
	function checkNumber(input) {
	    // 입력된 값이 숫자가 아닌 경우
	    if (isNaN(input.value)) {
	        // 입력값을 지우거나 유효한 숫자를 입력하도록 요구하는 메시지를 출력
	        input.setCustomValidity('숫자만 입력하세요.');
	    } else {
	        // 입력값이 유효한 경우, 에러 메시지를 초기화
	        input.setCustomValidity('');
	    }
	}
	
	
	//행추가
	function addRow() {
	  // table element 찾기
	  const table = document.getElementById('list');
	  
	  // 새 행(Row) 추가
	  const newRow = table.insertRow(1);
	  
	  // 새 행(Row)에 Cell 추가
	  const newCell1 = newRow.insertCell(0);
	  const newCell2 = newRow.insertCell(1);
	  const newCell3 = newRow.insertCell(2);
	  const newCell4 = newRow.insertCell(3);
	  const newCell5 = newRow.insertCell(4);
	  const newCell6 = newRow.insertCell(5);
	  const newCell7 = newRow.insertCell(6);
	  const newCell8 = newRow.insertCell(7); // 새로운 셀 추가
	  const newCell9 = newRow.insertCell(8); // 새로운 셀 추가
	  
	  // Cell에 텍스트 추가
	  newCell1.innerText = ''; // NO
	  newCell2.innerText = ''; // 수주번호 코드
	  // newCell3에 콤보박스 추가 (제품명 담당자)
	  var select3 = document.createElement("select");
	  select3.setAttribute("class", "form-select");
	  select3.setAttribute("id", "validationCustom06_e");
	  select3.setAttribute("name", "p_code");
	  select3.setAttribute("style", "width: 200px; padding: 5px; text-align: center;");
	  select3.setAttribute("required", ""); // 거래처 담당자 선택 필수로 설정
	
	  // 거래처 담당자 목록 추가
	  <c:forEach var="productList" items="${productList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${productList.p_code}");
	      option.textContent = "${productList.p_name}";
	      select3.appendChild(option);
	  </c:forEach>
	
	  // 셀에 콤보박스 추가
	  var div3 = document.createElement("div");
	  div3.setAttribute("class", "col-md-2");
	
	  div3.appendChild(select3);
	  newCell3.appendChild(div3);
	  //newCell4
	  // newCell4에 콤보박스 추가 (거래처 구분)
	  var select4 = document.createElement("select");
	  select4.setAttribute("class", "form-select");
	  select4.setAttribute("id", "validationCustom07_s");
	  select4.setAttribute("name", "c_code");
	  select4.setAttribute("style", "width: 160px; padding: 5px; text-align: center;");
	  select4.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정
	
	  // 거래처 구분 목록 추가
	  <c:forEach var="customerList" items="${customerList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${customerList.c_code}");
	      option.textContent = "${customerList.c_name}";
	      select4.appendChild(option);
	  </c:forEach>
	
	  // 셀에 콤보박스 추가
	  var div4 = document.createElement("div");
	  div4.setAttribute("class", "col-md-2");
	
	  div4.appendChild(select4);
	  newCell4.appendChild(div4);
	
	  //newCell5 수주수량
	  newCell5.innerHTML = '<input type="number" class="form-control" name="ship_num" placeholder="수주수량" style="width: 100px; padding: 5px; text-align: center;" required>';
	  
	  //newCell6 수주일자 (date 형식)
	  newCell6.innerHTML = '<input type="date" class="form-control" name="ship_date" style="width: 150px; padding: 5px; text-align: center; " required>';
	  
	  //newCell7 납품일자 (date 형식)
	  newCell7.innerHTML = '<input type="date" class="form-control" name="ship_deli" style="width: 150px; padding: 5px; text-align: center;" required>';
	  
	  // newCell8에 콤보박스 추가 (등록자)
	  var select5 = document.createElement("select");
	  select5.setAttribute("class", "form-select");
	  select5.setAttribute("id", "validationCustom07_s");
	  select5.setAttribute("name", "e_code");
	  select5.setAttribute("style", "width: 130px; padding: 5px; text-align: center;");
	  select5.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정
	
	  // 거래처 구분 목록 추가
	  <c:forEach var="empList" items="${empList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${empList.e_code}");
	      option.textContent = "${empList.e_name}";
	      select5.appendChild(option);
	  </c:forEach>
	
	  // 셀에 콤보박스 추가
	  var div5 = document.createElement("div");
	  div5.setAttribute("class", "col-md-2");
	
	  div5.appendChild(select5);
	  newCell8.appendChild(div5);
	  
	// 삭제 버튼 클릭 이벤트 핸들러 등록
      var deleteButton = document.createElement("button");
      deleteButton.setAttribute("type", "button");
      deleteButton.setAttribute("class", "btn btn-danger deleteButton");
      deleteButton.textContent = "삭제";
      
      deleteButton.onclick = function() {
									      deleteRow(newRow.rowIndex); // 해당 버튼의 행의 인덱스를 넘겨주어 행을 삭제
									      };
      
      newCell9.appendChild(deleteButton);
	}
	
	
	// 행 삭제
	function deleteRow(rowIndex) {
	    // table element 찾기
	    const table = document.getElementById('list');

	    // 행(Row) 삭제
	    table.deleteRow(rowIndex);
	}
	
	// 검색 버튼 클릭 시 실행되는 함수
	$(function() {
	    $('#searchButton').click(function() {
	        // 조건에 따른 검색 실행
	        searchShip();
	    });
	}) 

    // 조건에 따른 검색을 수행하는 함수
    function searchShip() {
		
		
        var sendData = $('#form1').serialize();
        //alert(sendData);
        location.href = "shipList?" + sendData;
    }
	
	$(function() {
	    $('#saveButton').click(function() {
	        var shipList = []; // 제품 정보를 담을 배열 선언
	        
	        // 테이블의 각 행을 순회하며 값을 가져와서 배열에 추가
	        $('#list tbody tr').each(function() {
	            var ship_code = $(this).find('td:eq(1)').text(); // 수주 코드 가져오기
	            if (!ship_code.trim()) { // ship_code가 비어있는 경우만 추가
	                var ship = {
	                    p_code: $(this).find('td:eq(2) select').val(), // 제품코드 가져오기
	                    c_code: $(this).find('td:eq(3) select').val(), // 거래처코드 가져오기
	                    ship_num: $(this).find('td:eq(4) input').val(), // 수주수량 가져오기
	                    ship_date: $(this).find('td:eq(5) input').val(), // 수주일자 가져오기
	                    ship_deli: $(this).find('td:eq(6) input').val(), // 납기일자 가져오기
	                    e_code: $(this).find('td:eq(7) select').val(), // 담당자 가져오기
	                };
	                shipList.push(ship); //수주정보를 배열에 추가
	            }
	        });
	        
	        // 배열을 JSON 형식으로 직렬화
	        var jsonData = JSON.stringify(shipList);
	        
	        // Ajax 요청 보내기
	        $.ajax({
	            type: "POST", // POST 방식으로 요청
	            url: "newShipInsert", // 요청을 보낼 URL
	            contentType: "application/json", // 전송할 데이터의 타입을 JSON으로 설정
	            data: jsonData, // JSON 데이터를 전송
	            success: function(response) {
	            	if (response == 'fail') {
	                    // 요청이 성공했을 때 실행되는 콜백 함수
	                    console.log("응답 데이터:", response);
	                    // 성공 메시지 등을 표시할 수 있음
	                    openPopup2(); // 팝업 창 열기
	                    // 조건에 따른 검색 실행
	                } else {
	                    // 요청이 성공했을 때 실행되는 콜백 함수
	                    console.log("응답 데이터:", response);
	                    // 성공 메시지 등을 표시할 수 있음
	                    openPopup(); // 팝업 창 열기
	                    // 조건에 따른 검색 실행

	                }
	            },
	            error: function(xhr, status, error) {
	                // 요청이 실패했을 때 실행되는 콜백 함수
	                console.error("에러:", error);
	                // 실패 메시지 등을 표시할 수 있음
	                alert("저장에 실패했습니다.");
	            }
	        });
	    });
	});
	
	
</script>
</head>
<body>
<!-- 팝업 창 HTML -->
<div id="popup" class="pop_wrap" style="display: none;">
    <div class="pop_inner" style="max-width: 300px;">
        <p style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; padding: 10px;">완료되었습니다.</p>
        <button onclick="confirmAndSearch()" class="btn btn-primary">확인</button>
    </div>
</div>

<!-- 팝업 창 HTML -->
<div id="popupFail" class="pop_wrap" style="display: none;">
    <div class="pop_inner" style="max-width: 400px;"> <!-- 너비 조정 -->
        <p style="padding: 10px;">중복된 거래처명이 있습니다.</p>
        <button onclick="confirmAndSearch2()" class="btn btn-primary">확인</button>
    </div>
</div>

<!-- JavaScript 함수 -->
<script>
	function openPopup2() {
	    document.getElementById('popupFail').style.display = 'block';
	}
	
	function closePopup2() {
	    document.getElementById('popupFail').style.display = 'none';
	}
	function confirmAndSearch2() {
        closePopup2(); // 팝업 창 닫기
    }
	
    
    function openPopup() {
        document.getElementById('popup').style.display = 'block';
    }

    function closePopup() {
        document.getElementById('popup').style.display = 'none';
    }
    function confirmAndSearch() {
        closePopup(); // 팝업 창 닫기
        searchShip(); // 검색 실행
    }
</script>

	<div class="row">
		<div class="col-lg-12">
			<!-- 조건 입력창 시작 -->
		    <div class="row mt-8">
		    
		    	<!-- 검색 -->
		    	<form id="form1" action="productList">
			        <!-- 상품목록 -->
				    <div class="mb-8">
				       <!-- heading -->
				       <h1 class="mb-1">수주 등록</h1>
				       <p>총  ${shipCnt} 건</p>
				    </div>
				    <!-- 제품조회 -->
				     <div class="row" id="k" style="margin-bottom: 10px;">
					    <div class="col-md-2">
					        <label for="validationCustom01" class="form-label">수주번호</label>
					        <input type="text" class="form-control" id="validationCustom01" name="ship_code" placeholder="수주번호 입력" style="max-width: 200px;" oninput="checkNumber(this);" value="${ship.ship_code}">
					    </div>
					    
					    <!-- 수주 시작일자 -->
					     <div class="col-md-2">
					        <label for="startDate" class="form-label">수주 시작일자</label>
					        <input type="date" class="form-control" id="startDate" name="date_from" value="${ship.date_from}">
					    </div> 
					    <!-- 수주 종료일자 -->
					    <div class="col-md-2">
					        <label for="endDate" class="form-label">수주 종료일자</label>
					        <input type="date" class="form-control" id="endDate" name="date_to" value="${ship.date_to}">
					    </div>
					    
					</div>
				
					<!-- 분류 -->
					<div class="row" id="k2" style="margin-bottom: 20px;">
					    <div class="col-md-2">
					        <label for="validationCustom04" class="form-label">제품</label>
					        <select class="form-select" id="validationCustom04" name="p_code" style="max-width: 200px;" >
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="productList" items="${productList}">
					        		 <option value="${productList.p_code}" ${productList.p_code eq ship.p_code ? 'selected' : ''}>${productList.p_name}</option>
					        		<%-- <option value="${bigList.b_code}">${bigList.b_name}</option> --%>
					        	</c:forEach>
					        </select>
					    </div>
					    <div class="col-md-2">
					        <label for="validationCustom05" class="form-label">거래처</label>
					        <select class="form-select" id="validationCustom04" name="c_code" style="max-width: 200px;">
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="customerList" items="${customerList}">
					        		<option value="${customerList.c_code}" ${customerList.c_code eq ship.c_code ? 'selected' : ''}>${customerList.c_name}</option>
					        		<%-- <option value="${smallList.s_code}">${smallList.s_name}</option> --%>
					        	</c:forEach>
					        </select>
					    </div>
					    
					   
					    
					    <!-- 검색 버튼 -->
			            <div class="col-md-2 col-xxl-2 d-lg-block mt-auto" style="margin-left: 5px;">
					        <button type="button" id="searchButton" class="btn  btn-outline-gray-400 text-muted" data-bs-toggle="modal" data-bs-target="#locationModal">검색</button>
					    </div>
					</div>
		        </form>
			</div>
	        <!-- 조건 입력창 끝 --> 
	        
	        <!-- 버튼 -->
	        <div>
			  <!-- Secondary Button -->
			  <button type="button" class="btn btn-secondary mb-2" style="width: 100px;"  onclick='addRow()'> 추가 </button>
			
			  <!-- Success Button -->
			  <button type="button"   id="saveButton"  class="btn btn-success mb-2" style="width: 100px;"> 저장 </button>
	        </div>
	        
        	<!-- 리스트 출력 -->   
	       <!-- table -->
	       <form id="form2" action="newProductInsert">
		       <div class="table-responsive" style="text-align: center;">
		          <table id="list" class="table text-nowrap">
		             <thead class="table-light">
		                <tr>
		                   <th>No.</th>
		                   <th>수주번호</th>
		                   <th>제품명</th>
		                   <th>거래처명</th>
		                   <th>수주수량</th>
		                   <th>수주일자</th>
		                   <th>납품일자</th>
		                   <th>등록자</th>
		                   <th>삭제</th>
		                </tr>
		             </thead>
		             <tbody>
		             	<c:forEach var="shipList" items="${shipList }" varStatus="status">
			                <tr id="ship${status.index }">
			                   <td class="align-middle">${startRow}</td>
			                   <td class="align-middle">${shipList.ship_code}  </td>
			                   <td class="align-middle">${shipList.p_name}  </td>
			                   <td class="align-middle">${shipList.c_name}  </td>
			                   <td class="align-middle">${shipList.ship_num}  </td>
			                   <td class="align-middle">${shipList.ship_date}  </td>
			                   <td class="align-middle">${shipList.ship_deli}  </td>
			                   <td class="align-middle">${shipList.e_name}  </td>
			                   
					           <td class="align-middle">
						            <button type="button" class="btn btn-danger deleteButton" onclick="deleteCustomer('${shipList.ship_code}', '${status.index+1}')">삭제</button>
						        </td>
			                </tr>
			                <c:set var="startRow" value="${startRow + 1}"/>
		                </c:forEach>
			    	 </tbody>
			   	  </table>
			    </div>
		    </form>

		</div>
	</div>
	<!-- 페이징 처리 -->
	<div class="row mt-8">
	  <div class="d-flex justify-content-center">
	    <!-- nav -->
	    <nav>
    	   <ul class="pagination">
	        <!-- 이전버튼 -->
	        <c:if test="${page.startPage > page.pageLimit}">
		        <li class="page-item">
		          <a class="page-link  mx-1 " href="shipList?currentPage=${page.startPage-page.pageLimit}&ship_code=${ship.ship_code}&p_code=${ship.p_code}&c_code=${ship.c_code}&date_from=${ship.date_from}&date_to=${ship.date_to}" aria-label="Previous">
		            	이전
		          </a>
		        </li>
	        </c:if>
	        
	        <!-- 페이지 넘버 -->
	        <c:forEach var="i" begin="${page.startPage }" end="${page.endPage }" varStatus="status">
	        	<li id="page${status.index}" class="page-item"><a class="page-link mx-1 text-body" href="shipList?currentPage=${i}&ship_code=${ship.ship_code}&p_code=${ship.p_code}&c_code=${ship.c_code}&date_from=${ship.date_from}&date_to=${ship.date_to}">${i }</a></li>
	        </c:forEach>
	         
	        <!-- 다음 버튼 -->
	        
	        <c:if test="${page.endPage < page.totalPage}">
		        <li class="page-item">
		          <a class="page-link mx-1 text-body" href="shipList?currentPage=${page.startPage+page.pageLimit}&ship_code=${ship.ship_code}&p_code=${ship.p_code}&c_code=${ship.c_code}&date_from=${ship.date_from}&date_to=${ship.date_to}" aria-label="Next">
		            	다음
		          </a>
		        </li>
	        </c:if>
	       </ul>
	    </nav>
	  </div>
	</div>
</body>
</html>