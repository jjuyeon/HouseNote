<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>공지사항 글목록</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <!-- Bootstrap core CSS -->
  <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="../css/clean-blog.min.css" rel="stylesheet">

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
		$(document).ready(function() {
			
			$('#mvWriteBtn').focusin(function() {
				$(location).attr("href", "${root}/article/write");
			});
			
			$("#searchBtn").click(function() {
				
				if($("#sword").val() == "") {
					alert("모든 목록 조회!!");
				} 
				$("#searchform").attr("action", "${root}/article/list").submit();
			});
			
			$(".page-item").click(function() {
				$("#pg").val(($(this).attr("data-pg")));
				$("#pageform").attr("action", "${root}/article/list").submit();
			});	
		});
</script>
</head>
<body>
<!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="${root}">
        <img id="logo-img-mobile" src="../img/logo.jpg" width="150" alt="The SSAFY">
      </a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>

      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          
 		<c:if test="${userinfo == null}">
          <li class="nav-item" id="login">
            <a class="nav-link" id="login" href="${root}/user?act=mvlogin">로그인</a>
          </li>
          <li class="nav-item" id="signup">
            <a class="nav-link" href="${root}/user?act=mvjoin">회원가입</a>
          </li>
          </c:if>
          
          <c:if test="${userinfo != null}">
          <li class="nav-item" id="logout">
            <a class="nav-link" href="${root}/user?act=logout">로그아웃</a>
          </li>
          <li class="nav-item" id="interest">
            <a class="nav-link" href="${root}/user?act=list&userid=${userinfo.userid}">관심지역</a>
          </li>
          <li class="nav-item" id="mypage">
            <a class="nav-link" href="${root}/user?act=mvinfo">마이 페이지</a>
          </li>
          <li class="nav-item" id="notice">
            <a class="nav-link" href="${root}/user?act=list&userid=${userinfo.userid}">공지사항</a>
          </li>
          </c:if>

          <li class="nav-item" id="home">
            <a class="nav-link" href="${root}">Home</a>
          </li>
        </ul>
      </div>
    </div>
    </nav>

    <!-- Page Header -->
  <header class="masthead" style="background-image: url('../img/mainbg1.jpg')">
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
  
    <form name="pageform" id="pageform" method="GET" action="">
		<input type="hidden" name="pg" id="pg" value="">
		<input type="hidden" name="key" id="key" value="${key}">
		<input type="hidden" name="word" id="word" value="${word}">
	</form>

<div class="container" align="center">
	<div class="col-lg-8" align="center">
	  <h2>글목록</h2>
	  <p>좋은글 많이 올려 주세요!!!!!</p>  
	  <table class="table table-borderless">
	  	<tr>
	  		<td align="right"><button type="button" id="mvWriteBtn" class="btn btn-link">글쓰기</button></td>
	  	</tr>
	  </table>
	  <form id="searchform" method="get" class="form-inline" action="">
	  <input type="hidden" name="pg" id="pg" value="1">
	  <table class="table table-borderless">
	  	<tr>
	  		<td align="right">
		  	  <select class="form-control" name="key" id="skey">
			    <option value="userid" selected="selected">아이디</option>
			    <option value="articleno">글번호</option>
			    <option value="subject">제목</option>
			  </select>
			  <input type="text" class="form-control" placeholder="검색어 입력." name="word" id="sword">
			  <button type="button" id="searchBtn" class="btn btn-primary">검색</button>
			</td>
	  	</tr>
	  </table>
	  </form>
	  <c:if test="${articles.size() != 0}">
	  	<c:forEach var="article" items="${articles}">
	  <table class="table table-active">
	    <tbody>
	      <tr class="table-info">
	        <td>작성자 : ${article.userid}</td>
	        <td align="right">작성일 : ${article.regtime}</td>
	      </tr>
	      <tr>
	        <td colspan="2" class="table-danger"><strong>${article.articleno}. ${article.subject}</strong></td>
	      </tr>
	      <tr>
	        <td colspan="2">${article.content}</td>
	      </tr>
	      <c:if test="${userinfo.userid == article.userid}">
	      <tr>
	        <td colspan="2">
			<a href="${root}/article/modify?articleno=${article.articleno}">수정</a>
			<a href="${root}/article/delete?articleno=${article.articleno}">삭제</a>
			</td>
	      </tr>
	      </c:if>
	    </tbody>
	  </table>
	  	</c:forEach>
	  	<table>
	  	<tr>
	  	<td>
	  	${navigation.navigator}
	  	</td>
	  	</tr>
	  	</table>
	  </c:if>
	  <c:if test="${articles.size() == 0}">
	  <table class="table table-active">
	    <tbody>
	      <tr class="table-info" align="center">
	        <td>작성된 글이 없습니다.</td>
	      </tr>
	    </tbody>
	  </table>
	  </c:if>
	  </div>
</div>
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
  <script src="../vendor/jquery/jquery.min.js"></script>
  <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="../js/clean-blog.min.js"></script>
</body>
</html>