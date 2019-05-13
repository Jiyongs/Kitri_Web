<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">

var ziplistView;

function zipSearch() {
	ziplistView = document.getElementById("zip_codeList");
	var doro = document.getElementById("doro").value;
	
	if(doro.length == 0) {
		alert("검색할 도로명을 입력해주세요!");
		return;
	} else {
		// 모달창이라 **ajax** 사용 필요!!
		var params = "act=zipSearch&doro=" + doro;
		sendRequest("<%= root %>/user", params, zipsearchResult, "GET"); 
		// => root에서 에러가 나지만, member.jsp에서 이 zipsearch.jsp가 실행되므로 에러 무시해도 됨!
	}
}

// 구조
// <zip>
//		<zipcode>1234</zipcode>
//		<address>서울시 구로구</address>
// </zip>
// ...

function zipsearchResult() {
	
	// 모든 데이터가 넘어왔을 경우
	if(httpRequest.readyState == 4) {
		// 그 데이터에 에러가 없을 경우
		if(httpRequest.status == 200) {
			var view = "";
			var result = httpRequest.responseXML;
			var ziplist = result.getElementsByTagName("zip"); //배열
			var len = ziplist.length;
			
			for(var i = 0; i < len; i++){
				var zipcode = ziplist[i].getElementsByTagName("zipcode")[0].firstChild.data;
				var address = ziplist[i].getElementsByTagName("address")[0].firstChild.data;
				view += "<tr>\n";
				view += "	<td>" + zipcode + "</td>\n";
				view += '	<td>';
				view += '		<a href="javascript:selectZip(\'' + zipcode + '\', \'' + address + '\');">';
				view += address;
				view += '		</a>';
				view += "	</td>\n";
				view += "</tr>";
			}
			
			ziplistView.innerHTML = view;
			
		} 
		
	} else {
		// 로딩중..
		ziplistView.innerHTML = '<img src="<%= root %>/img/loading.gif" width="80">';
	}
		
}

function selectZip(z, a) {
	document.getElementById("zipcode").value = z;
	document.getElementById("address").value = a;
	
	// 제이쿼리
	$("#zipModal").modal("hide"); // 모달창 끄기
	$("doro").val('');  // 검색창 비우기
	$("#ziop_codeList").empty();  // 검색 결과 비우기
}

</script>

<div id="zipModal" class="modal fade" role="dialog">
	<h5 class="modal-title" id="myModalLabel">우편번호검색</h5>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header text-center">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body text-center">
				<div align="center">
					<label>도로명 주소검색</label>
				</div>
				<div class="input-group" align="left">
					<input type="text" class="form-control" id="doro" name="doro"
						placeholder="검색 할 도로명 입력(예: 구로디지털로, 여수울로)"> <span
						class="input-group-btn"> <input type="button"
						class="btn btn-warning" value="검색" id="searchBtn"
						onclick="javascript:zipSearch();">
					</span>
				</div>
				<div style="width: 100%; height: 200px; overflow: auto">
					<table class="table text-center">
						<thead>
							<tr>
								<th style="width: 150px;">웁현번오</th>
								<th style="width: 600px;">줏오</th>
							</tr>
						</thead>
						<tbody id="zip_codeList"></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
