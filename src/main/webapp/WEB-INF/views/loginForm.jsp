
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/headerFo.jsp"  %>
<%-- <%@ include file="aboutLogin.jsp" %> --%>


<!DOCTYPE html>
<html>
<script src="https://cdn.jsdelivr.net/npm/js-cookie@rc/dist/js.cookie.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#passwordToggler {
		border: 0px;
		background: none;
	}
</style>

<script type="text/javascript" src="../assets/js/jquery.js"></script>
<script type="text/javascript">
	
	// 아이디에 focus
	window.onload = function() {
	    document.getElementById('m_id').focus();
	};

	// 아이디 기억하기
	$(document).ready(function(){
		 
	    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
	    var key = getCookie("key");
	    $("#m_id").val(key); 
	     
	    if($("#m_id").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
	        $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
	    }
	     
	    $("#idSaveCheck").change(function(){ // 체크박스에 변화가 있다면,
	        if($("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
	            setCookie("key", $("#m_id").val(), 1); // 1 일 동안 쿠키 보관
	        }else{ // ID 저장하기 체크 해제 시,
	            deleteCookie("key");
	        }
	    });
	     
	    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
	    $("#m_id").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
	        if($("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
	            setCookie("key", $("#m_id").val(), 1); // 1일 동안 쿠키 보관
	        }
	    });
	});
	//쿠키값 set
	function setCookie(cookieName, value, exdays){
	    var exdate = new Date();
	    exdate.setDate(exdate.getDate() + exdays);
	    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	    document.cookie = cookieName + "=" + cookieValue;
	}
	//쿠키값 delete
	function deleteCookie(cookieName){
	    var expireDate = new Date();
	    expireDate.setDate(expireDate.getDate() - 1);
	    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	}
	//쿠키값 get
	function getCookie(cookieName) {
	    cookieName = cookieName + '=';
	    var cookieData = document.cookie;
	    var start = cookieData.indexOf(cookieName);
	    var cookieValue = '';
	    if(start != -1){
	        start += cookieName.length;
	        var end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cookieValue = cookieData.substring(start, end);
	    }
	    return unescape(cookieValue);
	}
		

	
	function loginChk(m_id, m_pw) {		
	
		//alert("loginChk");
		$.ajax({
					url:"<%=request.getContextPath()%>/empChk",
					data : {chk_Id : m_id, 
							chk_Pw : m_pw},
					dataType : 'text',
					success : function(strResult) {
						//alert('strResult -> '+strResult);
						if(strResult == "1"){
// 							alert("로그인 성공! 환영합니다.");
							location.href = "mainBo";		
							return true;
						} else if(strResult == "2") {
							alert("탈퇴한 회원입니다.");
							$('#msg').html("탈퇴한 회원입니다.");
							$('#msg').css("color", "red");
							$('#m_id').val('');
							$('#m_pw').val('');
							return false;
						} else if(strResult == "0"){
							alert("아이디 혹은 비밀번호를 확인해주세요.");
							$('#msg').html("아이디 혹은 비밀번호를 확인해주세요.");
							$('#msg').css("color", "red");
// 							$('#m_id').val('');
							$('#m_pw').val('');
							return false;
						}	
					}
		});
		
		
	} 
	


</script>
</head>

<body>
<!-- section -->
  <section class="my-lg-14 my-8" style="width: 150%; ">
    <div class="container">
      <!-- row -->
      <div class="row justify-content-center align-items-center">
        <!-- col -->
        
        <div class="col-12 col-md-6 offset-lg-1 col-lg-4 order-lg-2 order-1" style="align-items: center;">
          <div class="mb-lg-9 mb-5">
            <h1 class="mb-1 h2 fw-bold" style="text-align: center;">LOGIN</h1>
            <p style="text-align: center;"><br> 로그인하여 이용해주시길 바랍니다.</p>
          </div>
		  <div id="msg" style="text-align: center;"></div><p>
<!-- 		<form action="memberLogin" method="get" name="frm" onsubmit="loginChk(m_id.value, m_pw.value)"> -->
            <div class="row g-3">
              <!-- row -->
			
              <div class="col-12">
                <!-- input -->
                <input type="text" class="form-control" id="m_id" name="m_id" placeholder="아이디를 입력하세요" required>
              </div>
              <div class="col-12">
                <!-- input -->
              <div class="password-field position-relative">
      			<input type="password" id="m_pw"  name="m_pw" placeholder="비밀번호를 입력하세요" class="form-control" required >
      			<span><button id="passwordToggler" class="bi bi-eye-slash"></button></span>
    		  </div>

              </div>
              <div class="d-flex justify-content-between">	
                <!-- form check -->
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" id="idSaveCheck" name="idSaveCheck" value="remember" >
                  <!-- label --> <label class="form-check-label" for="idSaveCheck">
                    	아이디 기억하기
                  </label>
                  
                </div>
                <div> 계정을 잊어버리셨나요? <a href="memberFindAccount">계정 찾기</a></div>
              </div>

             
<!--               <div class="col-12 d-grid"><input type="submit" class="btn btn-primary"  value="로그인"></div> -->
              <!-- btn -->
             <div class="col-12 d-grid"> <button id="btnSearch" class="btn btn-primary" onclick="loginChk(m_id.value, m_pw.value)">로그인</button>
             	 <!-- link -->
              <div>회원이 아니신가요? <a href="signUp">회원 가입하기</a></div>
           	 </div>
            
              
<!-- 		</form> -->
            
        
        </div>
       </div>
      </div>
   </div>
</section>
	

</body>
<script type="text/javascript">
	// 비밀번호 type 바꾸기
	const passwordToggler = document.getElementById("passwordToggler");
	const pwInput = document.getElementById("m_pw");
	
	passwordToggler.addEventListener("click", function() {

	  const currentType = pwInput.type;

	  if (currentType === "text") {
		  pwInput.type = "password";
	  } else if (currentType === "password") {
		  pwInput.type = "text";
	  }
	});
	
	// 버튼 요소와 input 요소를 가져옵니다.
	const btnSearch = document.getElementById("btnSearch");
	const m_pw = document.getElementById("m_pw");

	// input 요소에서 엔터 키를 누를 때 버튼을 클릭하도록 설정합니다.
	m_pw.addEventListener("keyup", function(event) {
	  if (event.key === "Enter") {
		  btnSearch.click();
	  }
	});
</script>
</html>