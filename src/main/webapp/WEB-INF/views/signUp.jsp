<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/headerFo.jsp"  %>
<!DOCTYPE html>
<html>
<script src="https://cdn.jsdelivr.net/npm/js-cookie@rc/dist/js.cookie.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    
</style>
<script type="text/javascript" src="../assets/js/jquery.js"></script>
<script type="text/javascript">
	var isDuplicateChecked = false; //중복확인 변수

	function checkDuplicate() {
	    var id = document.getElementById('id').value;
	    
	    // Ajax 요청 보내기
	    // jQuery를 사용한 예시
	    $.ajax({
	    	url:"<%=request.getContextPath()%>/checkDuplicate", // 서버 측 코드가 위치한 경로 또는 URL
	        data: { chk_id: id }, // 아이디를 서버로 전송
	        dataType : 'text',
	        success: function(response) {
	            if (response === 'exists') {
	                alert('이미 존재하는 아이디입니다.');
	                $('#id').val('');
	                return true;
	            } else {
	                alert('가입 가능한 아이디입니다.');
	                isDuplicateChecked = true; // 중복 확인이 되었음을 표시
	                return false;
	            }
	        }
	    });
	}
	
	function checkPasswordMatch() {
        var password = document.getElementById("pw").value;
        var confirmPassword = document.getElementById("pw2").value;

        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            $('#pw2').val('');
            // 팝업 창 대신 다른 방법을 원하시면 해당 부분을 수정하세요.
        }
    }
	
	function onSubmitForm(event) {
        if (!isDuplicateChecked) {
            event.preventDefault(); // 폼 제출을 막음
            alert("중복 확인을 먼저 진행해주세요.");
        }
    }

</script>
</head>
<body>
	<div class="row my-10">
 <!-- col -->
 <div class="offset-lg-1 col-lg-10 col-12">
    <div class="mb-8">
       <!-- heading -->
       <h1 class="h3">회원가입</h1>
       
    </div>
    <!-- form -->
    <form class="row" onsubmit="onSubmitForm(event);" action="mainBo">
       <!-- input -->
       <div class="col-md-7 mb-3">
          <label class="form-label" for="name"> 이름<span class="text-danger">*</span></label>
          <input type="text" id="name" class="form-control" name="e_name" placeholder="이름을 입력하시오" required>
       </div>
       <div class="col-md-7 mb-3">
        <!-- input -->
        <label class="form-label" for="id">아이디<span class="text-danger">*</span></label>
        <div class="input-group">
            <input type="text" id="id" class="form-control" name="e_id" placeholder="아이디를 입력하시오"  required>
            <button type="button" class="btn btn-primary" onclick="checkDuplicate(id.value)">중복확인</button>
        </div>
       </div>
       <div class="col-md-7 mb-3">
          <!-- input -->
          <label class="form-label" for="pw"> 비밀번호<span class="text-danger">*</span></label>
          <input type="text" id="pw" class="form-control" name="e_pw" placeholder="비밀번호를 입력하시오" required>
       </div>
       <div class="col-md-7 mb-3">
          <!-- input -->
          <label class="form-label" for="pw2"> 비밀번호 확인<span class="text-danger">*</span></label>
          <input type="text" id="pw2" name="e_pw2" class="form-control" placeholder="비밀번호를 재입력하시오" onblur="checkPasswordMatch();" required>
       </div>
  
        <div class="col-md-7 mb-3">
          <!-- input -->
          <label class="form-label" for="tel"> 연락처<span class="text-danger">*</span></label>
          <div class="input-phone mb-2">
              <input type="text" maxlength="13" id="tel" name="e_ph" class="form-control" placeholder="Ex : 010-1234-1234" pattern="(?:\d{2,3}-)?\d{3,4}-\d{4}" required >
           </div>
       </div> 
      
       <div class="col-md-7 mb-3">
          <label class="form-label" for="dept">소속부서<span class="text-danger">*</span></label>
          <%-- <div class="input-group" >
          	  <select id="dept" name="d_code" class="form-select" aria-label="Default select example">
          	  	<c:forEach var="list" items="${deptList}">
          	  		<option value="${dept.d_code}">${dept.d_name}</option>
          	  	</c:forEach>
          	  </select >
	      </div> --%>
	      <select id="dept" name="d_code" class="form-select" aria-label="Default select example" required>
	      	<option value="" disabled selected>부서를 선택하시오</option>
	      	<option value=10>생산부</option>
	      	<option value=20>영업팀</option>
	      	<option value=30>회계팀</option>
	      	<option value=40>전략기획팀</option>
	      	<option value=50>디자인팀</option>
	      </select>
       </div>
      <p><p>
       <div class="col-md-12">
          <!-- btn -->
          <button type="submit" class="btn btn-primary">가입하기</button>
       </div>
    </form>
  </div>
</div>
</body>
</html>