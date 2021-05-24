<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Happy House | 회원정보수정</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <!-- Bootstrap core CSS -->
  <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="/css/clean-blog.min.css" rel="stylesheet">

<style type="text/css">
fieldset {
	text-align: center;
}

fieldset label {
	padding: 10px;
}

fieldset lnput {
	padding: 10px;
}

#join {
	margin-top: 30px;
	color: white;
}

.span {
	color : red;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	//회원 목록
	console.log('${userinfo.userid}');
	$.ajax({
		url:'${root}/api/user/${userinfo.userid}',  
		type:'GET',
		dataType:'json',
		success:function(data) {
			console.log(data);
			if(data.resmsg === "조회 성공"){
				makeList(data.resValue);
			}
		},
		error:function(xhr,status,msg){
			console.log("상태값 : " + status + " Http에러메시지 : "+msg);
		}
	});
}); // document

function makeList(user){
	console.log("list: "+user);
	$("#userlist").empty();
	let str = `
		<form id="modifyform" method="post" action="">
		<div class="form-group" align:"left">
			<label for="name">아이디</label>
			<input type="text" class="form-control" name="userid" id="userid" readonly="readonly" value=${'${user.userid}'}>
		</div>
		<div class="form-group" align="left">
			<label for="username">이름</label>
			<input type="text" class="form-control" id="username" name="username" readonly="readonly" value=${'${user.username}'}>
		</div>
		<div class="form-group" align="left">
			<label for="userpwd">비밀번호</label>
			<input type="password" class="form-control" id="userpwd" name="userpwd" placeholder="">
		</div>
		<div class="form-group" align="left">
			<label for="">비밀번호재입력</label>
			<input type="password" class="form-control" id="pwdcheck" name="pwdcheck" placeholder="">
		</div>
		<div class="form-group" align="left">
			<label for="email">이메일</label><br>
			<div class="custom-control-inline">
			<input type="text" class="form-control" id="email" name="email" placeholder="" size="25"> @
			<select class="form-control" id="emaildomain" name="emaildomain">
				<option value="naver.com">naver.com</option>
				<option value="google.com">google.com</option>
				<option value="daum.net">daum.net</option>
				<option value="nate.com">nate.com</option>
				<option value="hanmail.net">hanmail.net</option>
			</select>
			</div>
		</div>
		<div class="form-group" align="left">
			<label for="">주소</label><br>
			<div id="addressdiv" class="custom-control-inline">
				<button type="button" style="background-color: rgb(228, 222, 222);" class="btn" id="zipcode" name="zipcode">주소 찾기</button>
			</div>
			<input type="text" class="form-control" name="address" id="address" placeholder="Click!" readonly="readonly">
			<input type="text" class="form-control" id="address_detail" name="address_detail" placeholder="">
		</div>
		<div class="form-group" align="center">
		<button type="button" class="btn btn-primary" id="btn-modi">회원 정보 수정</button>
		<button type="button" class="btn btn-warning" id="btn-cancel">취소</button>
		</div>
	</form>
	`;
	$("#userlist").append(str);
}
  
$(document).on("click", '#zipcode', function() {
	new daum.Postcode({
	      oncomplete : function(data) {
	        var fullRoadAddr = data.roadAddress;
	        var extraRoadAddr = '';

	        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
	          extraRoadAddr += data.bname;
	        }

	        if (data.buildingName !== '' && data.apartment === 'Y') {
	          extraRoadAddr += (extraRoadAddr !== '' ? ', '
	          + data.buildingName : data.buildingName);
	        }

	        if (extraRoadAddr !== '') {
	          extraRoadAddr = ' (' + extraRoadAddr + ')';
	        }

	        if (fullRoadAddr !== '') {
	          fullRoadAddr += extraRoadAddr;
	        }

	        $("[name=address]").val(fullRoadAddr);
	      }
	    }).open();
});

//회원 수정
$(document).on("click", "#btn-modi", function() {
	if($("#username").val() == "") {
		alert("이름 입력!!!");
		return;
	} else if($("#userpwd").val() == "") {
		alert("비밀번호 입력!!!");
		return;
	} else if($("#userpwd").val() != $("#pwdcheck").val()) {
		alert("비밀번호 확인!!!");
		return;
	} else if($("#email").val() == ""){
		alert("이메일 입력!!!")
		return;
	}
	else {
		// $("#userform").attr("action", "${root}/user").submit();
		let modifyinfo = JSON.stringify({
			"userid" : $("#userid").val(), 
			"userpwd" : $("#userpwd").val(), 
			"email" : $("#email").val() + "@" + $("#emaildomain").val(), 
			"address" : $("#address").val() + " " + $("#address_detail").val()
		   });
		console.log(modifyinfo);
		$.ajax({
			url:'${root}/api/user',  
			type:'PUT',
			contentType:'application/json;charset=utf-8',
			dataType:'json',
			data: modifyinfo,
			success:function(data) {
				if(data.resmsg === "수정 성공"){
					location.href = "${root}/user/modifysuccess";
				}else{
					alert("수정 실패");
					location.href = "${root}/error/500";
				}
			},
			error:function(xhr,status,msg){
				console.log("상태값 : " + status + " Http에러메시지 : "+msg);
			}
		});
	}
});

$(document).on("click", "#btn-cancel", function() {
	 history.back();
});
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>

<!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="${root}/">
        <img id="logo-img-mobile" src="/img/logo.jpg" width="150" alt="The SSAFY">
      </a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>

      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          
		 <c:if test="${userinfo == null}">
          <li class="nav-item" id="login">
            <a class="nav-link" id="login" href="${root}/user/login">로그인</a>
          </li>
          <li class="nav-item" id="signup">
            <a class="nav-link" href="${root}/user/join">회원가입</a>
          </li>
          </c:if>
          
          <c:if test="${userinfo != null}">
          <li class="nav-item" id="logout">
            <a class="nav-link" href="${root}/user/logout">로그아웃</a>
          </li>
          <li class="nav-item" id="interest">
            <a class="nav-link" href="${root}/user/interest">관심지역</a>
          </li>
          <li class="nav-item" id="mypage">
            <a class="nav-link" href="${root}/user/myinfo">마이 페이지</a>
          </li>
          <li class="nav-item" id="notice">
            <a class="nav-link" href="${root}/admin/notice">공지사항</a>
          </li>
          </c:if>

          <li class="nav-item" id="home">
            <a class="nav-link" href="${root}/">Home</a>
          </li>
        </ul>
      </div>
    </div>
    </nav>

    <!-- Page Header -->
  <header class="masthead" style="background-image: url('/img/mainbg1.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>HAPPY HOUSE</h1>
            <span class="subheading">행복한 우리집</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
 <div class="container" align="center">
	<div class="col-lg-6" align="center">
		  <h2>회원 정보 수정</h2>
		  <div id="userlist"></div>	
	</div>
</div>

 <!-- Footer -->
  <footer>
    <div class="container mt-5">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="footer-content">
            <img alt="" src="../img/logo.png" width="100px">
            <h2 class="title" style="display: inline-block;">Find Us</h2>
          </div>
          <hr>
          <ul class="list-icons mt-3">
            <li><i class="fa fa-map-marker pr-2 text-default"></i>(SSAFY) 서울시 강남구  테헤란로 멀티스퀘어</li>
            <li><i class="fa fa-phone pr-2 text-default"></i> 1544-9001</li>
            <li><a href="#"><i class="fa fa-envelope-o pr-2"></i>admin@ssafy.com</a></li>
          </ul>

          <p class="copyright text-muted">Copyright &copy; SUYEON Website 2021</p>
        </div>
      </div>
    </div>
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="/vendor/jquery/jquery.min.js"></script>
  <script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="/js/clean-blog.min.js"></script>

</body>
</html>