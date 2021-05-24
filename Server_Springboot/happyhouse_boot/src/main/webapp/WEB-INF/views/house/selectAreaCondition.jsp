<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<script>
////// house 정보 상세 검색 페이지로 이동  
	$(document).ready(function(){
		$.ajax({
			url: "http://localhost/api/sido",
            method: "GET",
            success: function (data) {
            	$.each(data.resValue, function(index, vo) {
					$("#sido").append("<option value='"+vo.sido_code+"'>"+vo.sido_name+"</option>");
				});//each
            }
		});//get
	});//ready
	$(document).ready(function(){
		$("#sido").change(function() {
			$.ajax({
				url: "http://localhost/api/gugun",
	            method: "GET",
	            data: {sido: $("#sido").val()},
	            success: function (data) {
	            	$("#gugun").empty();
					$("#gugun").append('<option value="0">선택</option>');
					$.each(data.resValue, function(index, vo) {
						$("#gugun").append("<option value='"+vo.gugun_code+"'>"+vo.gugun_name+"</option>");
					});//each
	            }
			});//get
		});//change
		$("#gugun").change(function() {
			console.log($("#gugun").val());
			$.ajax({
				url: "http://localhost/api/dong",
	            method: "GET",
	            data: {gugun: $("#gugun").val()},
	            success: function (data) {
	            	$("#dong").empty();
					$("#dong").append('<option value="0">선택</option>');
					$.each(data.resValue, function(index, vo) {
						$("#dong").append("<option value='"+vo.dong+"'>"+vo.dong+"</option>");
					});//each
	            }
			});//get
		});//change
		$("#dong").change(function() {
			alert('검색을 시작합니다.');
			$.ajax({
				url: "http://localhost/api/houseinfo",
	            method: "GET",
	            data: {dong: $("#dong").val()},
	            success: function (data) {
	            	$("#col-houseinfo").css("display","block");
					var opt = {
							// google map에 중앙으로 표시할 좌표 설정
		  	          		center : {lat:37.6522545,lng: 127.0304855},
			          		zoom : 15,  //0~ 21  큰 값일 수록  zooming
		          		};
					
					map = new google.maps.Map(document.getElementById("map"), opt);
					var infowindow = new google.maps.InfoWindow();
					var marker;
					const houseInfoArr = data.resValue;
					map = new google.maps.Map(document.getElementById("map"), {
				        center: { lat: Number(houseInfoArr[0].lat), lng: Number(houseInfoArr[0].lng) },
				        zoom: 14,
				      });
					var contents = $('#houseInfo');
					contents.empty();
					$('<h3 class="title">거래 정보</h3><div class="separator-2"></div><hr>').appendTo(contents);
					if(houseInfoArr.length == 0){
						var html = '<div class="media margin-clear"><div class="media-body"><h6>해당 지역에 존재하는 거래 정보가 없습니다.</h6></div></div><hr>'
						$(html).appendTo(contents)
					}
					
					for(var i in houseInfoArr) { 
				    	  var html='<div class="media margin-clear"><div class="media-body">'
		                  html += '<h4><a href="javascript:moveMap('+ houseInfoArr[i].lat+ ', '+houseInfoArr[i].lng+');">'+houseInfoArr[i].aptname+'</a></h4>'
							html+='<h6 class="media-heading">거래금액 :'+houseInfoArr[i].dealAmount+'</h6>'
							html+='<h6 class="media-heading">면적: '+houseInfoArr[i].area+'</h6>'
							html+='<p class="small margin-clear"><i class="fa fa-calendar pr-10"></i>'+houseInfoArr[i].dealYear+'.'+houseInfoArr[i].dealMonth+'.'+houseInfoArr[i].dealDay+'</p><hr>'
							$(html).appendTo(contents)
				      marker = new google.maps.Marker({
				      id:i,
				      title : houseInfoArr[i].aptname,
				      label : houseInfoArr[i].dealAmount,
				      icon : "./img/house.jpg",
				      position: new google.maps.LatLng(houseInfoArr[i].lat, houseInfoArr[i].lng),
				      map: map
				      });
				      
				      // marker 눌릴 때마다 그 위치에 해당하는 상세 정보 나옴
				      google.maps.event.addListener(marker, 'click', (function(marker, i) {
				      return function() {
				      infowindow.setContent("<center>["+houseInfoArr[i].aptname+"]<br>거래가 : "+houseInfoArr[i].dealAmount+"만원</center>");
				      infowindow.open(map, marker);
				      }
				      })(marker, i));
				      if(marker)
				      {
				    	  marker.addListener('click', function() {
				    		  $.ajax({
				        			url: "http://localhost/api/housedeal",
				                    method: "GET",
				                    data:{dong : houseInfoArr[i].dong, aptname:this.title},
				                    success: function (data) {
				                    	console.log(data.resValue);
				                    	processSearchAptResult(data.resValue);
				                    }
				        		});
				      map.setZoom(16);
				      map.panTo(this.getPosition());
					  });
				      }
				      
				      }
					
	            }
			});
		});//change
	});//ready
</script>

<div class="dark-bg section">
	<div class="container-fluid">
		<!-- filters start -->
		<div
			class="sorting-filters text-center mb-20 d-flex justify-content-center">
			<form class="form-inline" id="frm" method="get" action="">
				<input type="hidden" id="act" name="act" value="searchAreas" />
				<div class="form-group md">
					<select class="form-control mr-2" name="sido" id="sido">
						<option value="0">도/광역시</option>
					</select>
				</div>
				<div class="form-group mr-2 md-1">
					<select class="form-control" name="gugun" id="gugun">
						<option value="0">시/구/군</option>
					</select>
				</div>
				<div class="form-group md-1">
					<select class="form-control" name="dong" id="dong">
						<option value="0">동</option>
					</select>
				</div>

			</form>
		</div>
		<!-- filters end -->
	</div>
</div>
