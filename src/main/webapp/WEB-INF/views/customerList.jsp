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
	function deleteCustomer(c_code, pIndex){
		if(confirm("상품을 삭제하시겠습니까?")){
			$.ajax({
					url : "deleteCustomer",
					data : {c_code : c_code},
					dataType : 'Text',
					success : function(data){
						if(data == '1'){
							alert("삭제되었습니다.");
							 // table element 찾기
							  const table = document.getElementById('list');
							  
							  // 행(Row) 삭제
							  //const newRow = table.deleteRow(pIndex);
							  searchCustomer();
						}else if(data == '2'){
							// table element 찾기
							  alert("수주가 등록된 거래처는 삭제할 수 없습니다.");
							 
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
	  
	  // Cell에 텍스트 추가
	  newCell1.innerText = ''; // NO
	  newCell2.innerText = ''; // 거래처 코드
	  newCell3.innerHTML = '<input type="text" class="form-control" name="c_name" placeholder="거래처명" style="width: 150px; padding: 5px; text-align: center; margin-left: 80px;" required>';
	  newCell4.innerHTML = '<input type="tel" class="form-control" name="b_tel" placeholder="연락처" style="width: 180px; padding: 5px; text-align: center;" required>';
	  newCell5.innerHTML = '<input type="email" class="form-control" name="b_address" placeholder="이메일" style="width: 230px; padding: 5px; text-align: center;" required>';
	  // newCell6에 콤보박스 추가 (거래처 담당자)
	  var select3 = document.createElement("select");
	  select3.setAttribute("class", "form-select");
	  select3.setAttribute("id", "validationCustom06_e");
	  select3.setAttribute("name", "e_code");
	  select3.setAttribute("style", "width: 120px; padding: 5px; text-align: center;");
	  select3.setAttribute("required", ""); // 거래처 담당자 선택 필수로 설정
	
	  // 거래처 담당자 목록 추가
	  <c:forEach var="empList" items="${empList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${empList.e_code}");
	      option.textContent = "${empList.e_name}";
	      select3.appendChild(option);
	  </c:forEach>
	
	  // 셀에 콤보박스 추가
	  var div3 = document.createElement("div");
	  div3.setAttribute("class", "col-md-2");
	
	  div3.appendChild(select3);
	  newCell6.appendChild(div3);
	  // newCell7에 콤보박스 추가 (거래처 구분)
	  var select4 = document.createElement("select");
	  select4.setAttribute("class", "form-select");
	  select4.setAttribute("id", "validationCustom07_s");
	  select4.setAttribute("name", "s_code");
	  select4.setAttribute("style", "width: 100px; padding: 5px; text-align: center;");
	  select4.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정
	
	  // 거래처 구분 목록 추가
	  <c:forEach var="flagList" items="${flagList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${flagList.s_code}");
	      option.textContent = "${flagList.s_name}";
	      select4.appendChild(option);
	  </c:forEach>
	
	  // 셀에 콤보박스 추가
	  var div4 = document.createElement("div");
	  div4.setAttribute("class", "col-md-2");
	
	  div4.appendChild(select4);
	  newCell7.appendChild(div4);
	
	// 삭제 버튼 클릭 이벤트 핸들러 등록
      var deleteButton = document.createElement("button");
      deleteButton.setAttribute("type", "button");
      deleteButton.setAttribute("class", "btn btn-danger deleteButton");
      deleteButton.textContent = "삭제";
      
      deleteButton.onclick = function() {
									      deleteRow(newRow.rowIndex); // 해당 버튼의 행의 인덱스를 넘겨주어 행을 삭제
									      };
      
      newCell8.appendChild(deleteButton);
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
	        searchCustomer();
	    });
	}) 

    // 조건에 따른 검색을 수행하는 함수
    function searchCustomer() {
		
		
        var sendData = $('#form1').serialize();
        //alert(sendData);
        location.href = "customerList?" + sendData;
    }
	
	$(function() {
	    $('#saveButton').click(function() {
	        var customerList = []; // 제품 정보를 담을 배열 선언
	        var isValid = true; // 유효성 검사를 위한 변수 추가
	        
	        // 테이블의 각 행을 순회하며 값을 가져와서 배열에 추가
	        $('#list tbody tr').each(function() {
	            var c_code = $(this).find('td:eq(1)').text(); // 제품 코드 가져오기
	            if (!c_code.trim()) { // c_code가 비어있는 경우만 추가
	                var customer = {
	                    c_name: $(this).find('td:eq(2) input').val(), // 거래처명 가져오기
	                    c_tel: $(this).find('td:eq(3) input').val(), // 거래처 연락처가져오기
	                    c_email: $(this).find('td:eq(4) input').val(), // 거래처 이메일 가져오기
	                    e_code: $(this).find('td:eq(5) select').val(), // 거래처 코드 가져오기
	                    s_code: $(this).find('td:eq(6) select').val(), // 거래처구분 코드 가져오기
	                };
	            
	            	 // 유효성 검사 추가
	                if (!customer.c_name || !customer.c_tel || !customer.c_email ) {
	                    isValid = false;
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                }
	            
	                customerList.push(customer); // 제품 정보를 배열에 추가
	            }
	        });
	        
	     	// 모든 요소가 유효한지 확인
	        if (!isValid) {
	            // 유효성 검사 실패 시 처리
	            alert("양식을 모두 입력하세요.");
	            return; // 함수 종료
	        }
	        
	        // 배열을 JSON 형식으로 직렬화
	        var jsonData = JSON.stringify(customerList);
	        
	        // Ajax 요청 보내기
	        $.ajax({
	            type: "POST", // POST 방식으로 요청
	            url: "newCustomertInsert", // 요청을 보낼 URL
	            contentType: "application/json", // 전송할 데이터의 타입을 JSON으로 설정
	            data: jsonData, // JSON 데이터를 전송
	            success: function(response) {
	            	if (response == -1) {
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
        searchCustomer(); // 검색 실행
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
				       <h1 class="mb-1">거래처 조회</h1>
				       <p>총  ${customerCnt} 건</p>
				    </div>
				    <!-- 제품조회 -->
				     <div class="row" id="k" style="margin-bottom: 10px;">
					    <div class="col-md-2">
					        <label for="validationCustom01" class="form-label">거래처코드</label>
					        <input type="text" class="form-control" id="validationCustom01" name="c_code" style="max-width: 200px;" placeholder="거래처코드 입력" oninput="checkNumber(this);" value="${customer.c_code}">
					    </div>
					    <div class="col-md-2">
					        <label for="validationCustom04" class="form-label">담당자</label>
					        <select class="form-select" id="validationCustom04" name="e_code" style="max-width: 200px;" >
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="empList" items="${empList}">
					        		 <option value="${empList.e_code}" ${empList.e_code eq customer.e_code ? 'selected' : ''}>${empList.e_name}</option>
					        		<%-- <option value="${bigList.b_code}">${bigList.b_name}</option> --%>
					        	</c:forEach>
					        </select>
					    </div>
					</div>
				
					<!-- 분류 -->
					<div class="row" id="k2" style="margin-bottom: 20px;">
					    <div class="col-md-2">
						     <label for="validationCustom02" class="form-label">거래처명</label>
						     <input type="text" class="form-control" id="validationCustom02"  name="c_name" style="max-width: 200px;" placeholder="거래처명 입력" value="${customer.c_name}">
					    </div>
					    <div class="col-md-2">
					        <label for="validationCustom05" class="form-label">거래처구분</label>
					        <select class="form-select" id="validationCustom04" name="s_code" style="max-width: 200px;">
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="flagList" items="${flagList}">
					        		<option value="${flagList.s_code}" ${flagList.s_code eq customer.s_code ? 'selected' : ''}>${flagList.s_name}</option>
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
		                   <th>거래처 코드</th>
		                   <th>거래처명</th>
		                   <th>거래처 연락처</th>
		                   <th>거래처 이메일</th>
		                   <th>거래처 담당자</th>
		                   <th>거래처 구분</th>
		                   <th>삭제</th>
		                </tr>
		             </thead>
		             <tbody>
		             	<c:forEach var="customerList" items="${customerList }" varStatus="status">
			                <tr id="product${status.index }">
			                   <td class="align-middle">${startRow}</td>
			                   <td class="align-middle">${customerList.c_code}  </td>
			                   <td class="align-middle">${customerList.c_name}  </td>
			                   <td class="align-middle">${customerList.c_tel}  </td>
			                   <td class="align-middle">${customerList.c_email}  </td>
			                   <td class="align-middle">${customerList.e_name}  </td>
			                   <td class="align-middle">${customerList.s_name}  </td>
			                   
					           <td class="align-middle">
						            <button type="button" class="btn btn-danger deleteButton" onclick="deleteCustomer('${customerList.c_code}', '${status.index+1}')">삭제</button>
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
		          <a class="page-link  mx-1 " href="customerList?currentPage=${page.startPage-page.pageLimit}&c_code=${customer.c_code}&c_name=${customer.c_name}&e_code=${customer.e_code}&s_code=${customer.s_code}" aria-label="Previous">
		            	이전
		          </a>
		        </li>
	        </c:if>
	        
	        <!-- 페이지 넘버 -->
	        <c:forEach var="i" begin="${page.startPage }" end="${page.endPage }" varStatus="status">
	        	<li id="page${status.index}" class="page-item"><a class="page-link mx-1 text-body" href="customerList?currentPage=${i}&c_code=${customer.c_code}&c_name=${customer.c_name}&e_code=${customer.e_code}&s_code=${customer.s_code}">${i }</a></li>
	        </c:forEach>
	         
	        <!-- 다음 버튼 -->
	        <c:if test="${page.endPage < page.totalPage}">
		        <li class="page-item">
		          <a class="page-link mx-1 text-body" href="customerList?currentPage=${page.startPage+page.pageLimit}&c_code=${customer.c_code}&c_name=${customer.c_name}&e_code=${customer.e_code}&s_code=${customer.s_code}" aria-label="Next">
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