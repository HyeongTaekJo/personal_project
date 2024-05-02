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
	function deletePlanOrder(wo_code, pIndex){
		if(confirm("작업지시를 삭제하시겠습니까?")){
			$.ajax({
					url : "deleteWorkOrder",
					data : {wo_code : wo_code},
					dataType : 'Text',
					success : function(data){
						if(data == '1'){
							alert("삭제되었습니다.");
							 // table element 찾기
							  const table = document.getElementById('list');
							  
							  searchWorkOrder();
						}else if(data == '2'){
							// table element 찾기
							  alert("실적이 등록된 작업지시는 삭제할 수 없습니다.");
							 
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
	
	// 전역 변수로 select2 선언
	var select2;
	// 전역 변수로 선택한 행의 인덱스를 저장할 변수 추가
	var selectedRowIndex;
	
	// 콤보박스 클릭 시 모달 열기 함수
	function openModal(rowIndex) {
		// 선택한 행의 인덱스를 전역 변수에 저장
	    selectedRowIndex = rowIndex;
		
	    $.ajax({
	        url: 'planOrderListModal', // shipList를 반환하는 컨트롤러 주소
	        method: 'GET',
	        success: function(data) {
	            // AJAX 요청이 성공하면 테이블에 shipList 데이터 추가
	            var tableBody = $('#shipListTable tbody');
	            tableBody.empty(); // 테이블 내용 초기화

	            // shipList 데이터를 순회하며 테이블에 추가
	            data.forEach(function(item, index) {
	                var row = '<tr>' +
	                    '<td>' + (index + 1) + '</td>' +
	                    '<td>' + item.ship_code + '</td>' +
	                    '<td>' + item.plan_code + '</td>' +
	                    '<td>' + item.p_name + '</td>' +
	                    '<td>' + item.c_name + '</td>' +
	                    '<td style="display: none;">' + item.p_code + '</td>' + // p_code 추가, 숨김 처리
	                    '<td style="display: none;">' + item.c_code + '</td>' + // c_code 추가, 숨김 처리
	                    '<td>' + item.adjusted_plan_num + '</td>' +
	                    '<td>' + item.plan_date + '</td>' +
	                    '<td>' + item.ship_deli + '</td>' +
	                    '<td><button type="button" class="btn btn-info mb-2">선택</button></td>' +
	                    '</tr>';
	                tableBody.append(row); // 행 추가
	            });
	        },
	        error: function(xhr, status, error) {
	            // AJAX 요청이 실패할 경우 처리
	            console.error('AJAX 오류:', error);
	        }
	    });

	    // 선택 버튼에 이벤트 핸들러 연결
	    $(document).on('click', '.btn-info', function() {
	        // 선택 버튼을 클릭했을 때 해당 행의 데이터를 가져와서 각 셀에 있는 입력 요소의 값으로 설정하는 함수 호출
	        var ship_code = $(this).closest('tr').find('td:eq(1)').text();
	        var plan_code = $(this).closest('tr').find('td:eq(2)').text();
	        var p_name = $(this).closest('tr').find('td:eq(3)').text();
	        var c_name = $(this).closest('tr').find('td:eq(4)').text();
	        var p_code = $(this).closest('tr').find('td:eq(5)').text(); // p_code 가져오기
	        var c_code = $(this).closest('tr').find('td:eq(6)').text(); // c_code 가져오기
	        var qty = $(this).closest('tr').find('td:eq(7)').text();
	        var plan_date = $(this).closest('tr').find('td:eq(8)').text();
	        var ship_deli = $(this).closest('tr').find('td:eq(9)').text();
	        selectRow(ship_code,plan_code, p_name, c_name, p_code, c_code, qty, plan_date, ship_deli); // 함수 호출 시 p_code와 c_code 전달
	    
	    });

	    // 모달 창 열기
	    $('#exampleModalScrollable').modal('show');
	}
	
	// 선택 버튼 클릭 시 해당 행의 데이터를 가져와서 각 셀에 있는 입력 요소의 값으로 설정하는 함수
	function selectRow(ship_code,plan_code, p_name, c_name, p_code, c_code, qty, plan_date, ship_deli) {
		
		// 현재 선택한 행의 인덱스를 가져오지 않고, 전역 변수로 저장된 인덱스를 사용
	    var rowIndex = selectedRowIndex;
	    var row = $('#list tbody tr').eq(rowIndex);
	    
	 	// 수주번호 설정
	    row.find('td:eq(2)').html('<select class="form-select" id="select_custom_yqsaeidzk" name="plan_code" style="width: 150px; padding: 5px; text-align: center;" required><option value="' + plan_code + '" selected>' + plan_code + '</option></select>');
	    row.find('td:eq(5)').html('<input type="number" class="form-control" style="width: 100px; readonly="readonly" name="qty" value="' + qty + '">'); // 계획수량 설정
	    row.find('td:eq(7)').html('<select class="form-select" name="wo_status"  readonly="readonly" style="width: 100px; padding: 5px; text-align: center;" required><option value=114 selected>작업전</option></select>'); // 작업상태 설정
	    
	    // 모달 창 닫기
	    $('#exampleModalScrollable').modal('hide');
	    
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
	    const newCell8 = newRow.insertCell(7);
	    const newCell9 = newRow.insertCell(8);
	    const newCell10 = newRow.insertCell(9);

	    // Cell에 텍스트 추가
	    newCell1.innerText = ''; // NO
	    newCell2.innerText = ''; // 작업지시번호

	    // newCell3에 콤보박스 추가 (거래처 구분)
	    select2 = document.createElement("select");
	    var selectId = "select_custom_" + uniqueId(); // 고유한 ID 생성
	    select2.setAttribute("class", "form-select");
	    select2.setAttribute("id", selectId); // 콤보박스에 고유한 ID 설정
	    select2.setAttribute("name", "plan_code");
	    select2.setAttribute("style", "width: 160px; padding: 5px; text-align: center;");
	    select2.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정

	    // 플레이스홀더 추가
	    var placeholderOption = document.createElement("option");
	    placeholderOption.setAttribute("disabled", "true");
	    placeholderOption.setAttribute("selected", "true");
	    placeholderOption.setAttribute("hidden", "true");
	    placeholderOption.textContent = "계획번호 선택";
	    select2.appendChild(placeholderOption);

	    // 셀에 콤보박스 추가
	    var div2 = document.createElement("div");
	    div2.setAttribute("class", "col-md-2");

	    div2.appendChild(select2);
	    newCell3.appendChild(div2);

	    // 고유한 ID를 생성하는 함수
	    function uniqueId() {
	        return Math.random().toString(36).substr(2, 9);
	    }
	    
	 	// 콤보박스 클릭 이벤트 핸들러 수정
	    select2.addEventListener('click', function() {
	        // 현재 클릭된 콤보박스가 속한 행의 인덱스를 가져옴
	        var rowIndex = $('#list tbody tr').index($(this).closest('tr'));
	        // openModal 함수 호출 시 선택한 행의 인덱스 전달
	        openModal(rowIndex);
	    });
	    
 	    // newCell4에 콤보박스 추가 (작업장)
	    var select3 = document.createElement("select");
	    select3.setAttribute("class", "form-select");
	    select3.setAttribute("id", "validationCustom06_e");
	    select3.setAttribute("name", "wc_code");
	    select3.setAttribute("style", "width: 200px; padding: 5px; text-align: center;");
	    select3.setAttribute("required", ""); // 거래처 담당자 선택 필수로 설정
	
	    // 거래처 담당자 목록 추가
	    <c:forEach var="WorkCenterList" items="${WorkCenterList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${WorkCenterList.wc_code}");
	      option.textContent = "${WorkCenterList.wc_name}";
	      select3.appendChild(option);
	    </c:forEach>
	
	    // 셀에 콤보박스 추가
	    var div3 = document.createElement("div");
	    div3.setAttribute("class", "col-md-2");
	
	    div3.appendChild(select3);
	    newCell4.appendChild(div3);
		  
		  
	    // newCell5에 콤보박스 추가 (설비)
	    var select4 = document.createElement("select");
	    select4.setAttribute("class", "form-select");
	    select4.setAttribute("id", "validationCustom07_s");
	    select4.setAttribute("name", "mc_code");
	    select4.setAttribute("style", "width: 160px; padding: 5px; text-align: center;");
	    select4.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정
	
	    // 거래처 구분 목록 추가
	    <c:forEach var="MachineList" items="${MachineList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${MachineList.mc_code}");
	      option.textContent = "${MachineList.mc_name}";
	      select4.appendChild(option);
	    </c:forEach>
	
	    // 셀에 콤보박스 추가
	    var div4 = document.createElement("div");
	    div4.setAttribute("class", "col-md-2");
	
	    div4.appendChild(select4);
	    newCell5.appendChild(div4);

	    //newCell6 생산계획수량
	    newCell6.innerHTML = '<input type="number" class="form-control" name="plan_qty" placeholder="계획수량" style="width: 100px; padding: 5px; text-align: center;" required readonly="readonly">';

	    //newCell7 지시수량
	    newCell7.innerHTML = '<input type="number" class="form-control" name="qty" placeholder="지시수량" style="width: 100px; padding: 5px; text-align: center;" required>';
	    
        //newCell8에 콤보박스 추가 (작업상태)
	    var select5 = document.createElement("select");
	    select5.setAttribute("class", "form-select");
	    select5.setAttribute("id", "validationCustom07_s");
	    select5.setAttribute("name", "wo_status");
	    select5.setAttribute("style", "width: 160px; padding: 5px; text-align: center;");
	    select5.setAttribute("required", ""); // 거래처 구분 선택 필수로 설정
	
	    // 거래처 구분 목록 추가
	    <c:forEach var="workStatusList" items="${workStatusList}">
	      var option = document.createElement("option");
	      option.setAttribute("value", "${workStatusList.wo_status}");
	      option.textContent = "${workStatusList.s_name}";
	      select5.appendChild(option);
	    </c:forEach>
	
	    // 셀에 콤보박스 추가
	    var div5 = document.createElement("div");
	    div5.setAttribute("class", "col-md-2");
	
	    div5.appendChild(select5);
	    newCell8.appendChild(div5);
	    
	    //newCell9 계획일자 (date 형식)
	    newCell9.innerHTML = '<input type="date" class="form-control" name="wo_date" style="width: 150px; padding: 5px; text-align: center;" required>';

	 	// 삭제 버튼 클릭 이벤트 핸들러 등록
	    var deleteButton = document.createElement("button");
	    deleteButton.setAttribute("type", "button");
	    deleteButton.setAttribute("class", "btn btn-danger deleteButton");
	    deleteButton.textContent = "삭제";
	      
	    deleteButton.onclick = function() {
										    deleteRow(newRow.rowIndex); // 해당 버튼의 행의 인덱스를 넘겨주어 행을 삭제
										    };
	      
	    newCell10.appendChild(deleteButton);
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
	        searchWorkOrder();
	    });
	}) 

    // 조건에 따른 검색을 수행하는 함수
    function searchWorkOrder() {
		
		
        var sendData = $('#form1').serialize();
        //alert(sendData);
        location.href = "workOrderList?" + sendData;
    }
	
	$(function() {
	    $('#saveButton').click(function() {
	        var planList = []; // 제품 정보를 담을 배열 선언
	        var isValid = true; // 유효성 검사를 위한 변수 추가
	        var totalQtyMap = {}; // 각 수주별 계획수량의 합을 저장할 객체 선언
	        
	        // 테이블의 각 행을 순회하며 값을 가져와서 배열에 추가
	        $('#list tbody tr').each(function() {
	            var wo_code = $(this).find('td:eq(1)').text(); // 생산계획 코드 가져오기
	            if (!wo_code.trim()) { // plan_code가 비어있는 경우만 추가
	                var plan = {
	                	plan_code: $(this).find('td:eq(2) select').val(), // 생산계획 가져오기
	                	wc_code: $(this).find('td:eq(3) select').val(), // 작업장 가져오기
	                	mc_code: $(this).find('td:eq(4) select').val(), // 설비 가져오기
	                	plan_qty: $(this).find('td:eq(5) input').val(), // 생산계획수량 가져오기
	                    qty: $(this).find('td:eq(6) input').val(), // 지시수량 가져오기
	                    wo_status: $(this).find('td:eq(7) select').val(), // 작업상태 가져오기
	                    wo_date: $(this).find('td:eq(8) input').val(), // 지시일자 가져오기
	                };
	                
	                // 유효성 검사 추가
	                if (!plan.plan_code) {
	                    isValid = false;
	                    $(this).find('td:eq(2) select').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.wc_code) {
	                    isValid = false;
	                    $(this).find('td:eq(3) select').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.mc_code) {
	                    isValid = false;
	                    $(this).find('td:eq(4) select').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.plan_qty) {
	                    isValid = false;
	                    $(this).find('td:eq(5) input').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.qty) {
	                    isValid = false;
	                    $(this).find('td:eq(6) input').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.wo_status) {
	                    isValid = false;
	                    $(this).find('td:eq(7) select').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                } else if (!plan.wo_date) {
	                    isValid = false;
	                    $(this).find('td:eq(8) input').focus(); // 해당 입력란으로 포커스 이동
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                }
	                
	                // 계획수량이 수주수량보다 많은지 확인
	                if (parseInt(plan.qty) > parseInt(plan.plan_qty)) {
	                    isValid = false;
	                    $(this).find('td:eq(6) input').focus(); // 해당 입력란으로 포커스 이동
	                    alert("지시수량은 계획수량보다 많을 수 없습니다.");
	                    return false; // 유효성 검사 실패 시 반복문 종료
	                }
	                
	                // 각 수주별 계획수량의 합 계산
	                if (!totalQtyMap[plan.plan_code]) {
	                    // 수주번호가 totalQtyMap에 없는 경우
	                    totalQtyMap[plan.plan_code] = parseInt(plan.qty);
	                   
	                } else {
	                    // 수주번호가 totalQtyMap에 있는 경우ㅋ
	                    totalQtyMap[plan.plan_code] += parseInt(plan.qty);
	                    totalQtyMap[plan.plan_code]
	                }
	                
	                planList.push(plan); //수주정보를 배열에 추가
	            }
	        });
	        
	        // 모든 요소가 유효한지 확인
	        if (!isValid) {
	            // 유효성 검사 실패 시 처리
	            alert("양식을 모두 입력하세요.");
	            return; // 함수 종료
	        }
	        
	        // 각 수주별 지시수량 합과 생산계획 수량 비교
	        for (var plan_code in totalQtyMap) {
	        	
	        	var row = $('#list tbody tr').filter(function() {
	                return $(this).find('td:eq(2) select').val() === plan_code;
	            });
	        	
	            // 해당 행의 생산계획을 가져옴
	            var plan_qty = parseInt(row.find('td:eq(5) input').val());
	            if (totalQtyMap[plan_code] > plan_qty) {
	                isValid = false;
	                alert("생산계획번호 " + plan_code + "에 대한 지시수량의 합산이 생산계획수량보다 많습니다.");
	                break; // 유효성 검사 실패 시 반복문 종료
	            }
	        }
	        
	        // 모든 요소가 유효한지 확인
	        if (!isValid) {
	            // 유효성 검사 실패 시 처리
	            return; // 함수 종료
	        }
	        
	        // 배열을 JSON 형식으로 직렬화
	        var jsonData = JSON.stringify(planList);
	        
	        // Ajax 요청 보내기
	        $.ajax({
	            type: "POST", // POST 방식으로 요청
	            url: "newWorkOrderInsert", // 요청을 보낼 URL
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
        searchWorkOrder(); // 검색 실행
    }
</script>

<!-- Modal -->
<div class="modal fade" id="exampleModalScrollable" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable modal-xl" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalScrollableTitle">생산계획 목록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <!-- 테이블과 form -->
        <form id="shipListForm">
          <div class="table-responsive" style="text-align: center;">
            <table id="shipListTable" class="table text-nowrap">
              <thead class="table-light">
                <tr>
                  <th>No.</th>
                  <th>수주번호</th>
                  <th>생산계획번호</th>
                  <th>제품명</th>
                  <th>거래처명</th>
                  <th>계획수량</th>
                  <th>계획일자</th>
                  <th>납기일자</th>
                  <th>선택</th>
                </tr>
              </thead>
              <tbody>
                <!-- 여기에 shipList 데이터를 동적으로 추가할 예정 -->
              </tbody>
            </table>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


<table>
<tr>
	
</tr>
</table>

	<div class="row">
		<div class="col-lg-12">
			<!-- 조건 입력창 시작 -->
		    <div class="row mt-8">
		    
		    	<!-- 검색 -->
		    	<form id="form1" action="productList">
			        <!-- 상품목록 -->
				    <div class="mb-8">
				       <!-- heading -->
				       <h1 class="mb-1">작업지시 등록</h1>
				       <p>총  ${workOrderCnt} 건</p>
				    </div>
				    <!-- 제품조회 -->
				     <div class="row" id="k" style="margin-bottom: 10px;">
					    <div class="col-md-2">
					        <label for="validationCustom01" class="form-label">작업지시번호</label>
					        <input type="text" class="form-control" id="validationCustom01" name="wo_code" style="max-width: 200px;" placeholder="작업지시번호 입력" oninput="checkNumber(this);" value="${workOrder.wo_code}">
					    </div>
					    
					    <!-- 수주 시작일자 -->
					     <div class="col-md-2">
					        <label for="startDate" class="form-label">작업지시 시작일자</label>
					        <input type="date" class="form-control" id="startDate" name="date_from" value="${workOrder.date_from}">
					    </div> 
					    <!-- 수주 종료일자 -->
					    <div class="col-md-2">
					        <label for="endDate" class="form-label">작업지시 종료일자</label>
					        <input type="date" class="form-control" id="endDate" name="date_to" value="${workOrder.date_to}">
					    </div>
					    
					</div>
				
					<!-- 분류 -->
					<div class="row" id="k2" style="margin-bottom: 20px;">
					    <div class="col-md-2">
					        <label for="validationCustom04" class="form-label">작업장</label>
					        <select class="form-select" id="validationCustom04" name="wc_code" style="max-width: 200px;" >
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="WorkCenterList" items="${WorkCenterList}">
					        		 <option value="${WorkCenterList.wc_code}" ${WorkCenterList.wc_code eq workOrder.wc_code ? 'selected' : ''}>${WorkCenterList.wc_name}</option>
					        	</c:forEach>
					        </select>
					    </div>
					    <div class="col-md-2">
					        <label for="validationCustom05" class="form-label">설비</label>
					        <select class="form-select" id="validationCustom04" name="mc_code" style="max-width: 200px;">
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="MachineList" items="${MachineList}">
					        		<option value="${MachineList.mc_code}" ${MachineList.mc_code eq workOrder.mc_code ? 'selected' : ''}>${MachineList.mc_name}</option>
					        	</c:forEach>
					        </select>
					    </div>
					    
					    <div class="col-md-2">
					        <label for="validationCustom05" class="form-label">작업상태</label>
					        <select class="form-select" id="validationCustom04" name="wo_status" style="max-width: 200px;">
					        	<option value="" selected="selected">전체</option>
					        	<c:forEach var="workStatusList" items="${workStatusList}">
					        		<option value="${workStatusList.wo_status}" ${workStatusList.wo_status eq workOrder.wo_status ? 'selected' : ''}>${workStatusList.s_name}</option>
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
		                   <th>작업지시번호</th>
		                   <th>생산계획번호</th>
		                   <th>작업장명</th>
		                   <th>설비명</th>
		                   <th>계획수량</th>
		                   <th>지시수량</th>
		                   <th>작업상태</th>
		                   <th>지시일자</th>
		                   <th>삭제</th>
		                </tr>
		             </thead>
		             <tbody>
		             	<c:forEach var="workOrderList" items="${workOrderList }" varStatus="status">
			                <tr id="workOrder${status.index }">
			                   <td class="align-middle">${startRow}</td>
			                   <td class="align-middle">${workOrderList.wo_code}  </td>
			                   <td class="align-middle" >${workOrderList.plan_code}  </td>
			                   <td class="align-middle" >${workOrderList.wc_name}  </td>
			                   <td class="align-middle">${workOrderList.mc_name}  </td>
			                   <td class="align-middle">${workOrderList.plan_qty}  </td>
			                   <td class="align-middle">${workOrderList.qty}  </td>
			                   <td class="align-middle">${workOrderList.s_name}  </td>
			                   <td class="align-middle">${workOrderList.wo_date}  </td>
					           <td class="align-middle">
						            <button type="button" class="btn btn-danger deleteButton" onclick="deletePlanOrder('${workOrderList.wo_code}', '${status.index+1}')">삭제</button>
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
		          <a class="page-link  mx-1 " href="workOrderList?currentPage=${page.startPage-page.pageLimit}&wo_code=${workOrder.wo_code}&wc_name=${workOrder.wc_name}&mc_name=${workOrder.mc_name}&wo_status=${workOrder.wo_status}&date_from=${workOrder.date_from}&date_to=${workOrder.date_to}" aria-label="Previous">
		            	이전
		          </a>
		        </li>
	        </c:if>
	        
	        <!-- 페이지 넘버 -->
	        <c:forEach var="i" begin="${page.startPage }" end="${page.endPage }" varStatus="status">
	        	<li id="page${status.index}" class="page-item"><a class="page-link mx-1 text-body" href="workOrderList?currentPage=${i}&wo_code=${workOrder.wo_code}&wc_name=${workOrder.wc_name}&mc_name=${workOrder.mc_name}&wo_status=${workOrder.wo_status}&date_from=${workOrder.date_from}&date_to=${workOrder.date_to}">${i }</a></li>
	        </c:forEach>
	         
	        <!-- 다음 버튼 -->
	        
	        <c:if test="${page.endPage < page.totalPage}">
		        <li class="page-item">
		          <a class="page-link mx-1 text-body" href="workOrderList?currentPage=${page.startPage+page.pageLimit}&wo_code=${workOrder.wo_code}&wc_name=${workOrder.wc_name}&mc_name=${workOrder.mc_name}&wo_status=${workOrder.wo_status}&date_from=${workOrder.date_from}&date_to=${workOrder.date_to}" aria-label="Next">
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