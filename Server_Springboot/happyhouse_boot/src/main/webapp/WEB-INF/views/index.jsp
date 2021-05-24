<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Happy House</title>

  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="css/clean-blog.min.css" rel="stylesheet">
   <style>
  	.btngroup {
    padding : 10px;
	}
	
	button{
	margin : 10px;
	}
  </style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=#{your_google_cloud_api_key}&callback=initMap" async defer></script>
  <script type="text/javascript">
    var locations = [
            ['도봉구'	,	37.6658609	,	127.0317674	],
          ['은평구'	,	37.6176125	,	126.9227004	],
          ['동대문구'	,	37.5838012	,	127.0507003	],
          ['동작구'	,	37.4965037	,	126.9443073	],
          ['금천구'	,	37.4600969	,	126.9001546	],
          ['구로구'	,	37.4954856	,	126.858121	],
          ['종로구'	,	37.5990998	,	126.9861493	],
          ['강북구'	,	37.6469954	,	127.0147158	],
          ['중랑구'	,	37.5953795	,	127.0939669	],
          ['강남구'	,	37.4959854	,	127.0664091	],
          ['강서구'	,	37.5657617	,	126.8226561	],
          ['중구'		,	37.5579452	,	126.9941904	],
          ['강동구'	,	37.5492077	,	127.1464824	],
          ['광진구'	,	37.5481445	,	127.0857528	],
          ['마포구'	,	37.5622906	,	126.9087803	],
          ['서초구'	,	37.4769528	,	127.0378103	],
          ['성북구'	,	37.606991	,	127.0232185	],
          ['노원구'	,	37.655264	,	127.0771201	],
          ['송파구'	,	37.5048534	,	127.1144822	],
          ['서대문구'	,	37.5820369	,	126.9356665	],
          ['양천구'	,	37.5270616	,	126.8561534	],
          ['영등포구'	,	37.520641	,	126.9139242	],
          ['관악구'	,	37.4653993	,	126.9438071	],
          ['성동구'	,	37.5506753	,	127.0409622	],
          ['용산구'	,	37.5311008	,	126.9810742	]
      ];

    var map = null;
    var latitude;
    var longitude;
	var lat = 37.606991
    var lng = 127.0232185
    var zoom = 11			
								
///////////////////////////////////////////////////맵 설정
    function initMap() {
      //getLocation();
      var opt = {
        // google map에 중앙으로 표시할 좌표 설정
        center : {lat:lat,lng: lng},
        zoom : zoom,  //0~ 21  큰 값일 수록  zooming
      };
      //google.maps.Map(map을 그릴 영역, 옵션정보 );
      map = new google.maps.Map(document.getElementById("map"), opt);
      
      var infowindow = new google.maps.InfoWindow();

  var marker, i;
  for (i = 0; i < locations.length; i++) {  
    marker = new google.maps.Marker({
      id:i,
      title : locations[i][0],
      label : locations[i][0],
      position: new google.maps.LatLng(locations[i][1], locations[i][2]),
      map: map
    });

    google.maps.event.addListener(marker, 'click', (function(marker, i) {
      return function() {
        infowindow.setContent(locations[i][0]);
        infowindow.open(map, marker);
      }
    })(marker, i));
    if(marker)
    {
      marker.addListener('click', function() {
          console.log(this.title);
        map.setZoom(15);
        map.panTo(this.getPosition());
      });
      }
  }
    }
  						
  function moveAreaOfHouse(dealno){
	  <c:if test="${userinfo eq null}">
	  alert("로그인이 필요한 서비스입니다.");
	  </c:if>
	  let registerinfo = JSON.stringify({
		  	"productno":dealno
		   });
	  <c:if test="${userinfo ne null}">
	  console.log("[getInfo] "+dealno + "... " + "${userinfo.userid}");
		$.ajax({
			url:'${root}/api/user/${userinfo.userid}/interest',  
			type:'POST',
			contentType:'application/json;charset=utf-8',
			dataType:'json',
			data: registerinfo,
			success:function(data) {
				if(data.resmsg === "관심 지역 등록 성공"){
					location.href = "${root}/user/area/addsuccess";
				}else{
					alert("관심 지역 등록 실패");
					location.href = "${root}/error/500";
				}
			},
			error:function(xhr,status,msg){
				console.log("상태값 : " + status + " Http에러메시지 : "+msg);
			}
		});
	  </c:if>
  }
  
  function processSearchAptResult(jsondata){
		var type=['','아파트 매매','아파트 전월세','다세대 매매','다세대 전월세'];
		var list = jsondata;
		var contents = $('#houseInfo');
		contents.empty();				//기존에 내용이 있으면 비우기
		 $('<h3 class="title">거래 정보</h3><div class="separator-2"></div><h5>'+list[0].aptname+'</h5><hr>').appendTo(contents);
			$.each(list, function(index, item) {
				var html='<div class="media margin-clear"><div class="media-body">'
				html+='<button type="button" class="btn btn-link" style="float: right" onclick="moveAreaOfHouse('+parseInt(item.no)+');">관심지역 추가</button>'
				html+='<h6 class="media-heading">거래금액 :'+item.dealAmount+'</h6>'
				html+='<h6 class="media-heading">전용면적: '+item.area+'</h6>'
				html+='<h6 class="media-heading">거래구분 :'+type[item.type]+'</h6>'
				html+='<p class="small margin-clear"><i class="fa fa-calendar pr-10"></i>'+item.dealYear+'.'+item.dealMonth+'.'+item.dealDay+'</p><hr>'
				$(html).appendTo(contents)
			})
	}

  /// 현재 위치를 최초위치로.
      function showLocation(position) {
          latitude = position.coords.latitude;
          longitude = position.coords.longitude;
          //alert("Latitude : " + latitude + " Longitude: " + longitude);
          //현재 위치 정보를 center로 지정하기 위해 객체 생성
          var hear = new google.maps.LatLng(latitude, longitude);
          map.panTo(hear); //위치 정보를 통해 맵에 표시 
          
      /* 	
          for (var i = 0; i < results.features.length; i++) {
      var coords = results.features[i].geometry.coordinates;
      var latLng = new google.maps.LatLng(coords[1],coords[0]);
      var marker = new google.maps.Marker({
      position: latLng,
      map: map
      });
      }
      
      /* 	//맵에서 위치 표시하기 위한 정보 
          var marker = new google.maps.Marker({
              position: hear,
              title:"I'm hear!",
              map: map
          }); */

      }

      function errorHandler(err) {
          if (err.code == 1) {
              alert("Error: Access is denied!");
          } else if (err.code == 2) {
              alert("Error: Position is unavailable!");
          }else if(err.code == 3){
              alert("Erro : Time out");
          }
      }
      function getLocation() {
          if (navigator.geolocation) {
              var options = {
                  timeout : 60000,
                  enabledHighAccuracy :true
              };
              navigator.geolocation.getCurrentPosition(showLocation,
                      errorHandler, options);
          } else {
              alert("Sorry, browser does not support geolocation!");
          }
      }

      // 지도 위치 변경
      function moveMap(Lat, Lon, Zoomlevel) {
          map.setZoom(15);
          setTimeout(function() {										
          var moveLatLon = new google.maps.LatLng(Lat,Lon);
          map.panTo(moveLatLon);
          map.setZoom(Zoomlevel);
          }, 1000);
      };
</script>
</head>

<body>

<c:if test="${msg != null}">
<script>
	alert("${msg}");
</script>
</c:if>

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="${root}">
        <img id="logo-img-mobile" src="img/logo.jpg" width="150" alt="The SSAFY">
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
            <a class="nav-link" href="${root}">Home</a>
          </li>
        </ul>
      </div>
    </div>
    </nav>

    <!-- Page Header -->
  <header class="masthead" style="background-image: url('img/mainbg1.jpg')">
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
  <div class="container">
    <%@ include file="/WEB-INF/views/house/selectAreaCondition.jsp" %>
    <div class="row mt-5" style="justify-content: center;">
            <div class="col-4" id="col-houseinfo" style="display:none;">
                <!-- 거래 정보 출력 start-->
                <div class="sidebar">
                    <div class="block clearfix" id="houseInfo">
                        <h3 class="title">거래 정보</h3>
                        <hr>
                        <div class="separator-2"></div>
                      </div>
                  </div>
                  <!-- 거래 정보 출력 end -->
            </div>
            
            <div class="col-8">
                <!-- 지도 띄우는 부분 start -->
                <div id="map" style="width: 100%; height: 600px; margin: auto; position: relative; overflow: hidden;">
                    <div style="height: 100%; width: 100%; position: absolute; top: 0px; left: 0px; background-color: rgb(229, 227, 223);">
                        <div class="gm-err-container"><div class="gm-err-content">
                            <div class="gm-err-icon">
                                <img src="https://maps.gstatic.com/mapfiles/api-3/images/icon_error.png" draggable="false" style="user-select: none;">
                            </div>
                            <div class="gm-err-title">죄송합니다. 문제가 발생했습니다.</div>
                            <div class="gm-err-message">Google 지도가 제대로 로드되지 않았습니다. 자바스크립트 콘솔에서 자세한 기술 정보를 확인하세요.</div>
                        </div>
                    </div>
                </div>
            </div>
            <script defer="" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCtrYlkBfW8GosnP1qOid3SNIn5C1UuWws&amp;callback=initMap"></script>
            <!-- 지도 띄우는 부분 end -->
            </div>
    </div>
  </div>


  <hr>

<!-- Footer -->
  <footer>
    <div class="container mt-5">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="footer-content">
            <img alt="" src="img/logo.png" width="100px">
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
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  

</body>

</html>
